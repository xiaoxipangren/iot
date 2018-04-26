package com.nationalchip.iot.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/6/18 10:57 AM
 * @Modified:
 */
public class FilterChain implements IFilterChain {

    private int currentIndex=0;

    Map<Integer,IFilter> filters=new HashMap<Integer, IFilter>();

    @Override
    public void filter(StringBuilder request, StringBuilder response) {
        IFilter filter = filters.get(currentIndex++);
        if(filter!=null)
            filter.filter(request,response,this);

    }

    public void addFilter(IFilter filter){
        filters.put(filter.getIndex(),filter);
    }

}
