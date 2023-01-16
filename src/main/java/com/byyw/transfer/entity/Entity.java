package com.byyw.transfer.entity;

import lombok.Data;

@Data
public class Entity {
    private String str;
    public Entity(){};
    public Entity(String str){
        this.str = str;
    }
}
