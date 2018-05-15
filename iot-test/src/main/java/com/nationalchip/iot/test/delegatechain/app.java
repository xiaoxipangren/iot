package com.nationalchip.iot.test.delegatechain;

import java.util.function.Consumer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/10/18 9:37 AM
 * @Modified:
 */
public class app {
    public static void main(String args[]){
        Consumer<StringBuilder> builder = sb->{};

        for(int i=0;i<10;i++)
            builder=new NumberedFilter(i).configure(builder);
        StringBuilder sb= new StringBuilder();

        builder.accept(sb);

        System.out.println(sb.toString());

    }
}
