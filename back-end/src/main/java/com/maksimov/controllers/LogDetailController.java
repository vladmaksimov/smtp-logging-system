package com.maksimov.controllers;

import com.maksimov.models.view.LogDetailView;
import com.maksimov.service.LogDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 01.03.17.
 */
@RestController
@RequestMapping("/api/details")
public class LogDetailController {

    private LogDetailsService service;

    @RequestMapping("/get/{id}")
    public Page<LogDetailView> getLogs(@PathVariable("id") Long id,
                                       @PageableDefault(sort = {"id"}) Pageable page,
                                       String search) {
        return search == null ? service.getLogDetails(page, id) : service.searchDetails(page, id, search);
    }

    @Autowired
    public void setService(LogDetailsService service) {
        this.service = service;
    }
}
