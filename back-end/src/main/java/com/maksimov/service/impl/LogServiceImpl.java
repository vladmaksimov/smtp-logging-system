package com.maksimov.service.impl;

import com.maksimov.models.entity.LogKey;
import com.maksimov.persistence.LogPersistence;
import com.maksimov.service.LogService;
import com.maksimov.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created on 01.03.17.
 */
@Service
public class LogServiceImpl implements LogService {

    private static final String TYPE_ALL = "ALL";

    private LogPersistence persistence;

    @Override
    public Page<LogKey> getLogs(Pageable page, String status) {
        if (status == null || TYPE_ALL.equals(status)) {
            return persistence.findAll(page);
        } else {
            return persistence.getLogByStatus(status, page);
        }
    }

    @Override
    public Page<LogKey> searchLogs(Pageable pageable, String status, String search) {
        search = Utils.createSearchString(search);
        if (status == null || TYPE_ALL.equals(status)) {
            return persistence.searchLogKey(search, pageable);
        } else {
            return persistence.getLogBySearchAndStatus(search, status, pageable);
        }
    }

    @Override
    public LogKey getById(Long id) {
        return persistence.findOne(id);
    }

    @Override
    public LogKey getByKey(String key) {
        return persistence.getLogByLogKey(key);
    }

    @Override
    public LogKey save(LogKey key) {
        return persistence.save(key);
    }

    @Override
    public void saveCollection(Collection<LogKey> keys) {
        persistence.save(keys);
    }

    @Autowired
    public void setPersistence(LogPersistence persistence) {
        this.persistence = persistence;
    }

}
