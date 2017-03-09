package com.maksimov.service;

import java.util.List;

/**
 * Created on 02.03.17.
 */
public interface FileWatchService {

    void processFile(List<String> lines);

    long getFirstRow(String file);

}
