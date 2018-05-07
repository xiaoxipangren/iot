package com.nationalchip.iot.test.chain;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/6/18 10:06 AM
 * @Modified:
 */
public interface IFilter {

    int getIndex();

    void filter(StringBuilder request, StringBuilder response, IFilterChain chain);

}
