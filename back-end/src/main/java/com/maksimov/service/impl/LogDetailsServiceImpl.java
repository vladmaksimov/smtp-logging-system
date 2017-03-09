package com.maksimov.service.impl;

import com.maksimov.models.entity.LogDetail;
import com.maksimov.models.view.LogDetailView;
import com.maksimov.persistence.LogDetailsPersistence;
import com.maksimov.service.LogDetailsService;
import com.maksimov.transformers.ViewTransformer;
import com.maksimov.transformers.impl.LogDetailTransformer;
import com.maksimov.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 01.03.17.
 */
@Service
@Transactional
public class LogDetailsServiceImpl implements LogDetailsService {

    private LogDetailsPersistence persistence;

    private ViewTransformer<LogDetailView, LogDetail> transformer;

    @Override
    public Page<LogDetailView> getLogDetails(Pageable page, Long key) {
        Page<LogDetail> details = persistence.getLogDetails(key, page);
        return convertToView(details, page);
    }

    @Override
    public Page<LogDetailView> searchDetails(Pageable pageable, Long key, String search) {
        search = Utils.createSearchString(search);
        Page<LogDetail> details = persistence.searchDetails(key, search, pageable);
        return convertToView(details, pageable);
    }

    @Override
    public LogDetail save(LogDetail detail) {
        return persistence.save(detail);
    }

    @Override
    public void saveCollection(List<LogDetail> details) {
        persistence.save(details);
    }

    @Override
    public LogDetail getLastRow() {
        return persistence.findFirst1ByOrderByIdDesc();
    }

    private Page<LogDetailView> convertToView(Page<LogDetail> page, Pageable pageable) {
        List<LogDetailView> converted = page.getContent().stream().map(detail -> transformer.transform(detail)).collect(Collectors.toList());
        return new PageImpl<>(converted, pageable, page.getTotalElements());
    }

    @Autowired
    public void setTransformer(LogDetailTransformer transformer) {
        this.transformer = transformer;
    }

    @Autowired
    public void setPersistence(LogDetailsPersistence persistence) {
        this.persistence = persistence;
    }
}
