package com.maksimov.service;

import com.maksimov.models.entity.LogKey;

import java.util.Collection;

/**
 * Created on 10.03.17.
 */
public interface WebSocketService {

    void sendMessage(Collection<LogKey> keys);

}
