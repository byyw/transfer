package com.byyw.transfer.controller;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.byyw.common.annotation.TransferPoint;
import com.byyw.common.transfer.Transfer;
import com.byyw.transfer.entity.Entity;


@Component
public class DemoController implements CommandLineRunner{
    
    @Autowired
    private Transfer transfer;

    @Override
    public void run(String... args) throws Exception {
        for(int i=0;i<10;i++){
            try {
                // 推送Entity对象
                transfer.post(new Entity(""+i));
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 接收Entity对象节点
     * @param e
     */
    @TransferPoint(Entity.class)
    public void entity(Entity e){
        System.out.println(e.getStr());
    }

}
