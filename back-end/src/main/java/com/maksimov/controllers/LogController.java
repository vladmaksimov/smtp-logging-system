package com.maksimov.controllers;

import com.maksimov.models.entity.LogKey;
import com.maksimov.models.entity.filter.Condition;
import com.maksimov.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 01.03.17.
 */

@RestController
@RequestMapping("/api/key")
public class LogController {

    private LogService service;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Page<LogKey> getLogs(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable page,
                                String status, String search,
                                @RequestBody(required = false) List<Condition> conditions) {
        return search == null ? service.getLogs(page, status, conditions) : service.searchLogs(page, status, search);
    }

    @RequestMapping("/get/{id}")
    public LogKey getKeyById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @Autowired
    public void setService(LogService service) {
        this.service = service;
    }
}
