package com.maksimov.service;

import com.maksimov.models.entity.LogKey;
import com.maksimov.models.entity.filter.Condition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * Created on 01.03.17.
 */
public interface LogService {

    Page<LogKey> getLogs(Pageable pageable, String type, List<Condition> filters);

    Page<LogKey> searchLogs(Pageable pageable, String type, String search);

    LogKey getById(Long id);

    LogKey getByKey(String key);

    LogKey save(LogKey key);

    void saveCollection(Collection<LogKey> keys);

}
