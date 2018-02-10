package com.nationalchip.iot.data.model;

/**
 * 权限动作的枚举
 * 使用1/2/4/8进行枚举可以进行按位与进行运算
 */
public enum Action {
    CREATE("CREATE",1),
    READ("READ",2),
    UPDATE("UPDATE",4),
    DELETE("DELETE",8);


    private final String name;
    private final int index;

    private Action(String name,int index){
        this.name=name;
        this.index=index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
