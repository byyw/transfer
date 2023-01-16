# 基于springboot的内存间对象发布/订阅功能实现

简单来说发布者与订阅者都是spring代理的各个对象

发布者通过注入的Transfer对象的post(Object obj)方法实现发布

订阅者通过在方法上注释@TransferPoint(Object.class)实现订阅

两者之间依靠发布与订阅的对象，也就是相同的Object对象来实现对应

