package com.maksimov.service;

import com.maksimov.models.entity.LogDetail;
import com.maksimov.models.view.LogDetailView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created on 01.03.17.
 */
public interface LogDetailsService {

    Page<LogDetailView> getLogDetails(Pageable page, Long key);

    Page<LogDetailView> searchDetails(Pageable pageable, Long key, String search);

    LogDetail save(LogDetail detail);

    void saveCollection(List<LogDetail> details);

    LogDetail getLastRow();

}
