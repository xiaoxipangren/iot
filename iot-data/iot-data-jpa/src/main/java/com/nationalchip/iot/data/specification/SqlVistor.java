package com.nationalchip.iot.data.specification;

import com.nationalchip.iot.data.model.IEntity;
import cz.jirutka.rsql.parser.ast.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cz.jirutka.rsql.parser.ast.RSQLOperators.*;


/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/26/18 3:06 PM
 * @Modified:
 */


public final class SqlVistor<T extends IEntity> implements RSQLVisitor<List<Predicate>,Root<T>>{

    private static final Character LIKE_WILDCARD = '*';
    private final ConversionService conversionService = new DefaultConversionService();



    private CriteriaBuilder builder;
    
    public SqlVistor(final CriteriaBuilder builder){

        this.builder = builder;
    }


    @Override
    public List<Predicate> visit(AndNode node, Root<T> param) {
        final List<Predicate> children = acceptChilds(node,param);
        if(!children.isEmpty())
            return toSingleList(builder.and(children.toArray(new Predicate[children.size()])));

        return toSingleList(builder.conjunction());
    }

    @Override
    public List<Predicate> visit(OrNode node, Root<T> param) {
        final List<Predicate> children = acceptChilds(node,param);
        if(!children.isEmpty())
            return toSingleList(builder.or(children.toArray(new Predicate[children.size()])));

        return toSingleList(builder.conjunction());
    }

    @Override
    public List<Predicate> visit(ComparisonNode node, Root<T> root) {
        ComparisonOperator op = node.getOperator();
        Path attrPath = root.get(node.getSelector());

        String arg = node.getArguments().get(0);
        List<Object> values = convertValues(node,root);

        Object value = values.get(0);

        if (op.equals(EQUAL)) {

            if(value instanceof String){
                return toSingleList(builder.like(attrPath, (String)value));
            }
            return toSingleList(builder.equal(attrPath,value));

        }
        if (op.equals(NOT_EQUAL)) {
            if(value instanceof String){
                return toSingleList(builder.notLike(attrPath, (String)value));
            }
            return toSingleList(builder.notEqual(attrPath,value));
        }
        if (op.equals(GREATER_THAN)) {
            return toSingleList(builder.greaterThan(attrPath, arg));
        }
        if (op.equals(GREATER_THAN_OR_EQUAL)) {
            return toSingleList(builder.greaterThanOrEqualTo(attrPath, arg));
        }
        if (op.equals(LESS_THAN)) {
            return toSingleList(builder.lessThan(attrPath, arg));
        }
        if (op.equals(LESS_THAN_OR_EQUAL)) {
            return toSingleList(builder.lessThanOrEqualTo(attrPath, arg));
        }
        if(op.equals(IN)){
            return toSingleList(attrPath.in(values));
        }
        if(op.equals(NOT_IN)){
            return toSingleList(builder.not(attrPath.in(values)));
        }
        throw new IllegalArgumentException("Unknown operator: " + op);
    }


    private <X> Path<X> stringPath(Path<?> path){
        return (Path<X>) path;
    }


    private List<Object> convertValues(ComparisonNode node,Root<T> root){

        Attribute attribute = root.getModel().getAttribute(node.getSelector());
        Class type = attribute.getJavaType();
        List<Object> values = new ArrayList<>();
        node.getArguments().stream().forEach( r -> values.add(conversionService.convert(escapeValueToSQL(r),type)));
        return values;
    }


    private String escapeValueToSQL(final String transformedValue) {
        return transformedValue.replace("%", "\\%").replace(LIKE_WILDCARD, '%');
    }

    private List<Predicate> acceptChilds(final LogicalNode node,Root<T> root) {
        final List<Node> children = node.getChildren();
        final List<javax.persistence.criteria.Predicate> childs = new ArrayList<>();
        for (final Node child : children) {
            final List<Predicate> accept = child.accept(this,root);
            if (!CollectionUtils.isEmpty(accept)) {
                childs.addAll(accept);
            } else {
//                LOGGER.debug("visit logical node children but could not parse it, ignoring {}", node2);
            }
        }
        return childs;
    }

    private static List<Predicate> toSingleList(final Predicate predicate) {
        return Collections.singletonList(predicate);
    }
}
