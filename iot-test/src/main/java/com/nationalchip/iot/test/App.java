package com.nationalchip.iot.test;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        FilterChain chain = new FilterChain();

        for(int i=0;i<10;i++){
            chain.addFilter(new OrderedFilter(i,6));
        }

        StringBuilder request = new StringBuilder();
        StringBuilder response = new StringBuilder();

        chain.filter(request,response);




        System.out.println( request.toString());
        System.out.println(response.toString());
    }
}
