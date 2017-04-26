package com.maksimov.queryBuilder;

import com.maksimov.models.entity.filter.Condition;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created  on 20.04.17.
 */
public interface QueryBuilder<T> {

    Specification buildQuery(List<Condition> conditions);

}
