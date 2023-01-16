package com.byyw.common.transfer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.byyw.common.annotation.TransferPoint;
import com.byyw.common.util.ClassUtils;

public class Transfer {

    private Class root;
    private ApplicationContext ac;

    private static Map<Class,List<Method>> mMap = new HashMap<>();

    private Transfer(){}
    public Transfer(Class _root,ApplicationContext _ac) throws Exception{
        setRoot(_root);
        setAc(_ac);
    }
    private void init(){
        try {
            String name = root.getName();
            name = name.substring(0,name.lastIndexOf("."));
            Set<Class<?>> cs = ClassUtils.getClassList(name);
            for(Class c:cs){
                Method[] ms = c.getMethods();
                for(Method m:ms){
                    if(m.isAnnotationPresent(TransferPoint.class)){
                        TransferPoint tp = m.getAnnotation(TransferPoint.class);
                        if(!mMap.containsKey(tp.value())){
                            mMap.put(tp.value(), new ArrayList<Method>());
                        }
                        if(m.getParameterTypes().length==1 && m.getParameterTypes()[0]==tp.value()){
                            mMap.get(tp.value()).add(m);
                        } else {
                            new Exception(m.getDeclaringClass().getName()+"."+m.getName()+"参数与"+tp.value().getName()+"不匹配.").printStackTrace();;
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
    public void post(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        List<Method> ms = mMap.get(obj.getClass());
        if(ms != null){
            for(Method m:ms){
                if(Modifier.isStatic(m.getModifiers())){
                    m.invoke(null, obj);
                } else {
                    if(this.ac != null){
                        Object o = this.ac.getBean(m.getDeclaringClass());
                        if(o != null){
                            m.invoke(o, obj);
                        }
                    }
                }
            }
        }
    }
    private void setRoot(Class _root) throws Exception{
        if(!_root.isAnnotationPresent(SpringBootApplication.class)){
            throw new Exception("root class is not annotated by @SpringBootApplication.");
        }
        this.root = _root;
        init();
    }
    private void setAc(ApplicationContext _ac){
        this.ac = _ac;
    }
    
}
