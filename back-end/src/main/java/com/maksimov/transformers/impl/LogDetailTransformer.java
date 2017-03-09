package com.maksimov.transformers.impl;

import com.maksimov.models.entity.LogDetail;
import com.maksimov.models.view.LogDetailView;
import com.maksimov.transformers.ViewTransformer;
import org.springframework.stereotype.Component;

/**
 * Created on 03.03.17.
 */
@Component
public class LogDetailTransformer implements ViewTransformer<LogDetailView, LogDetail> {

    public LogDetailView transform(LogDetail detail) {
        LogDetailView detailView = new LogDetailView();

        detailView.setId(detail.getId());
        detailView.setDate(detail.getDate());
        detailView.setEventType(detail.getEventType());
        detailView.setFullDetails(detail.getFullDetails());
        detailView.setMessage(detail.getMessage());

        return detailView;
    }

}
