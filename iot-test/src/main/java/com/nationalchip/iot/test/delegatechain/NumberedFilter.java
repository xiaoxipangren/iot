package com.nationalchip.iot.test.delegatechain;

import java.util.function.Consumer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/10/18 9:32 AM
 * @Modified:
 */
public class NumberedFilter implements IFilter {
    private int number;

    public NumberedFilter(int number){
        this.number=number;
    }

    @Override
    public Consumer<StringBuilder> configure(Consumer<StringBuilder> next) {

        return sb -> {
            sb.append(number+";");
            if(number!=7){
                next.accept(sb);
            }
        };
    }
}
