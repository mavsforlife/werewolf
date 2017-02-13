package com.victor.wolves.Model;

/**
 * Created by victor on 2017/1/24.
 * 角色死亡类型
 */

public enum DieType {

    Null("", 0),Poison("女巫毒杀", 1), Exile("公投放逐", 2), Kill("狼人猎杀", 3), Hunt("猎人带走", 4), DieForLove("恋人殉情", 5);

    private int status;
    private String type;

    DieType(String type, int status) {
        this.status = status;
        this.type = type;
    }

    public int status(){
        return status;
    }

    public String type() {
        return type;
    }
}
