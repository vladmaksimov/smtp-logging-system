package com.maksimov.processors;

import com.maksimov.models.entity.LogKey;

/**
 * Created on 02.03.17.
 */
public interface MessageProcessor {

    String STATUS_PROCESSING = "PROCESSING";
    String STATUS_UNKNOWN = "UNKNOWN";

    void process(LogKey key, String message);

}
