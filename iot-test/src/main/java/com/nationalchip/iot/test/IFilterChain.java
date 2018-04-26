package com.nationalchip.iot.test;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/6/18 10:22 AM
 * @Modified:
 */
public interface IFilterChain {
    void filter(StringBuilder request, StringBuilder response);
}
