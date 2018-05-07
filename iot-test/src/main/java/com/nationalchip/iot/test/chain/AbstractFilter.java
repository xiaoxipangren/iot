package com.nationalchip.iot.test.chain;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/6/18 10:23 AM
 * @Modified:
 */
public abstract class AbstractFilter implements IFilter {
    @Override
    public void filter(StringBuilder request, StringBuilder response, IFilterChain chain) {
        chain.filter(request,response);
    }
}
