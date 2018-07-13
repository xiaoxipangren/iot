package com.nationalchip.iot.data.specification;

import com.nationalchip.iot.data.exception.SortParameterSyntaxErrorException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/28/18 8:57 AM
 * @Modified:
 */

@Component
public class SortParser {

    public final String DELIMITER_FIELD_DIRECTION = ":";

    private final String DELIMITER_SORT_TUPLE = ",";

    private final String ID="id";

    public Sort parse(String sort){

        if(sort == null || sort.isEmpty())
            return new Sort(Direction.ASC,ID);

        List<Order> orders = toOrders(sort);
        return new Sort(orders);
    }

    private   List<Order> toOrders(final String sortString){
        final List<Order> orders = new ArrayList<>();
        if (sortString != null) {
            final StringTokenizer tokenizer = new StringTokenizer(sortString, DELIMITER_SORT_TUPLE);
            while (tokenizer.hasMoreTokens()) {
                final String sortTuple = tokenizer.nextToken().trim();
                final StringTokenizer stringTokenizer = new StringTokenizer(sortTuple,
                        DELIMITER_FIELD_DIRECTION);
                if (stringTokenizer.countTokens() == 2) {
                    final String fieldName = stringTokenizer.nextToken().trim().toLowerCase();
                    final String sortDirectionStr = stringTokenizer.nextToken().trim().toUpperCase();

                    final Direction sortDirection = Direction.valueOf(sortDirectionStr);
                    orders.add(new Order(sortDirection, fieldName));
                } else {
                    throw new SortParameterSyntaxErrorException();
                }
            }
        }
        return orders;
    }

}
