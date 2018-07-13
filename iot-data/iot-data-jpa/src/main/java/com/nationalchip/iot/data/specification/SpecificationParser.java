package com.nationalchip.iot.data.specification;

import com.nationalchip.iot.data.model.IEntity;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/26/18 3:51 PM
 * @Modified:
 */

@Component
public final class SpecificationParser {
    public <T extends IEntity> Specification<T> parse(String sql){
        return (root, query, cb) -> {
            Node rootNode = new RSQLParser().parse(sql);
            List<Predicate> predicates = rootNode.accept(new SqlVistor<>(cb),root);
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
