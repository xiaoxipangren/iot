package com.nationalchip.iot.test.chain;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/6/18 10:24 AM
 * @Modified:
 */
public class OrderedFilter extends AbstractFilter {

    private int index;
    private int end;


    public OrderedFilter(int index,int end){
        this.index=index;
        this.end=end;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void filter(StringBuilder request, StringBuilder response, IFilterChain chain){
        if(index!=7){
            filterRequest(request);
            super.filter(request,response,chain);
            filterResponse(response);
        }
        else
            super.filter(request,response,chain);


    }

    private void filterRequest(StringBuilder request){

        request.append(multi(index*(toString().length())," "));
        request.append(toString());
    }

    private void filterResponse(StringBuilder response){
        filterRequest(response);
    }

    private String multi(int count,String s){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<count;i++)
            sb.append(s);
        return sb.toString();
    }

    public String toString(){
        return "--filter "+index+"--\n";
    }
}
