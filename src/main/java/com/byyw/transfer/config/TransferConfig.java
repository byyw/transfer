package com.byyw.transfer.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.byyw.common.transfer.Transfer;
import com.byyw.transfer.TransferApplication;


@Configuration
public class TransferConfig implements ApplicationContextAware{

    private ApplicationContext ac;
    @Bean("transfer")
    public Transfer transfer() throws Exception{
        Transfer tf = new Transfer(TransferApplication.class,this.ac);
        return tf;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ac = applicationContext;
    }
}
