package com.maksimov.queryBuilder;

import com.maksimov.models.entity.filter.Condition;
import com.maksimov.models.entity.filter.Type;
import com.maksimov.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created  on 13.04.17.
 */
public class QueryBuilderImpl<T> implements Specification<T> {

    private List<Condition> conditions;

    public QueryBuilderImpl(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        List<Predicate> predicates = buildPredicates(root, query, cb);
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }


    private List<Predicate> buildPredicates(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        conditions.forEach(condition -> predicates.add(buildPredicate(condition, root, criteriaQuery, criteriaBuilder)));
        return predicates;
    }

    @SuppressWarnings("unchecked")
    private Predicate buildPredicate(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        switch (condition.getComparison()) {
            case eq:
                return buildEquals(condition, root, criteriaBuilder);
            case gt:
                return buildGreatThen(condition, root, criteriaBuilder);
            case lt:
                break;
            case lk:
                return buildLike(condition, root, criteriaBuilder);
            default:
                return buildEquals(condition, root, criteriaBuilder);
        }
        throw new RuntimeException();
    }

    private Predicate buildEquals(Condition condition, Root root, CriteriaBuilder criteriaBuilder) {
        //todo separate date and other
        Object o = getValue(condition);
        if (Type.date.equals(condition.getType())) {
            Expression e = root.get(condition.getField());
//            Date o = (Date) getValue(condition);
//            Date lt = LocalDate
            Predicate lt = criteriaBuilder.lessThanOrEqualTo(e, (Date) o);
            Predicate gt = criteriaBuilder.greaterThanOrEqualTo(e, (Date) o);
            return criteriaBuilder.and(lt, gt);
        }
        return criteriaBuilder.equal(root.get(condition.getField()), o);
    }

    @SuppressWarnings("unchecked")
    private Predicate buildGreatThen(Condition condition, Root root, CriteriaBuilder criteriaBuilder) {
        if (Type.date.equals(condition.getType())) {
            Date date = (Date) getValue(condition);
            return criteriaBuilder.greaterThanOrEqualTo(root.get(condition.getField()), date);
        }
        return criteriaBuilder.equal(root.get(condition.getField()), condition.getValue().toString());
    }

    @SuppressWarnings("unchecked")
    private Predicate buildLike(Condition condition, Root root, CriteriaBuilder criteriaBuilder) {
        if (Type.string.equals(condition.getType())) {
            String value = (String) getValue(condition);
            return criteriaBuilder.like(root.get(condition.getField()), Utils.createSearchString(value));
        }
        throw new RuntimeException();
    }

    private Object getValue(Condition condition) {
        Object o = condition.getValue();

        switch (condition.getType()) {
            case string:
                return Utils.getStringFromObject(o);
            case date:
                return Utils.processDate(Utils.getStringFromObject(o));
            default:
                return null;
        }
    }


}