package com.nationalchip.iot.test.delegatechain;

import java.util.function.Consumer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/10/18 9:30 AM
 * @Modified:
 */
public interface IFilter {
    Consumer<StringBuilder> configure(Consumer<StringBuilder> next);
}
