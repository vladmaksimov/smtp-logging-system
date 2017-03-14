package com.maksimov.service.impl;

import com.maksimov.models.entity.LogDetail;
import com.maksimov.models.entity.LogKey;
import com.maksimov.processors.MessageProcessor;
import com.maksimov.service.FileWatchService;
import com.maksimov.service.LogDetailsService;
import com.maksimov.service.LogService;
import com.maksimov.service.WebSocketService;
import com.maksimov.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 04.03.17.
 */
@Service
public class FileWatchCollectionServiceImpl implements FileWatchService {

    private static final Logger logger = Logger.getLogger(FileWatchCollectionServiceImpl.class);

    private static final String LOG_PART_IP = " ip-";
    private static final String LOG_PART_POSTFIX = "postfix";
    private static final String LOG_PART_OPENDKIM = "opendkim";
    private static final String LOG_PART_CONNECT = "connect";
    private static final String LOG_PART_WARNING = "warning";
    private static final String LOG_KEY_SEPARATOR = ": ";
    private static final String LOG_EVENT_TYPE_SEPARATOR = "[";
    private static final Integer LOG_SEPARATE_LIMIT = 3;
    private static final Integer LOG_WITH_KEY_MIN_LENGTH = 2;
    private static final Integer LOG_KEY_VALUE = 1;
    private static final String LOG_KEY_REGEX = "^[A-Z0-9]+$";
    private static final Integer LOG_MESSAGE_VALUE = 2;
    private static final Integer LOG_MESSAGE_TO_SAVE = 10000;

    private LogService logService;
    private LogDetailsService detailsService;
    private List<MessageProcessor> messageProcessors;
    private WebSocketService webSocketService;

    @Override
    public void processFile(List<String> lines) {
        Map<String, LogKey> keyMap = new LinkedHashMap<>();
        List<LogDetail> details = new LinkedList<>();
        Integer count = 0;

        for (String log : lines) {
            if (logger.isDebugEnabled()) {
                logger.debug("Process line: " + log);
            }

            if (isSMTPLog(log)) {
                String[] splits = log.split(LOG_KEY_SEPARATOR, LOG_SEPARATE_LIMIT);
                if (isLogWithKey(splits) && isKeyRegexpMatching(splits)) {
                    String key = splits[LOG_KEY_VALUE];
                    LogKey logKey = checkLogKey(keyMap, key);

                    Date date = Utils.processDate(log.substring(0, log.indexOf(LOG_PART_IP)));
                    String message = splits[LOG_MESSAGE_VALUE];

                    if (logKey == null) {
                        logKey = createLogKey(key, date, log);
                        keyMap.put(key, logKey);
                    }

                    LogDetail detail = createLogDetails(message, date, log, logKey);
                    details.add(detail);

                    updateLogStatus(logKey, message);
                    count++;

                    if (LOG_MESSAGE_TO_SAVE.equals(count)) {
                        saveLogsAndSensWsMessage(keyMap, details);
                        clearDataToSave(keyMap, details);
                        count = 0;
                    }

                }
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("Line has incorrect format: " + log);
                }
            }
        }

        if (!details.isEmpty()) {
            saveLogsAndSensWsMessage(keyMap, details);
        }
    }

    @Override
    public long getFirstRow(String file) {
        long count = 0;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(file), StandardCharsets.UTF_8)) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            LogDetail lastDetail = detailsService.getLastRow();
            if (lastDetail != null && lines.contains(lastDetail.getFullDetails())) {
                count = (long) lines.indexOf(lastDetail.getFullDetails());
            }
        } catch (IOException e) {
            logger.error("Can't get data from file: " + file + ". Error: " + e.getMessage());
        }
        return count;
    }

    private void saveLogsAndSensWsMessage(Map<String, LogKey> keyMap, List<LogDetail> details) {
        logger.info("Keys to save/update: " + keyMap.size());
        logger.info("Details to save: " + details.size());

        logService.saveCollection(keyMap.values());
        detailsService.saveCollection(details);
        webSocketService.sendMessage(keyMap.values());
    }

    private void clearDataToSave(Map<String, LogKey> keyMap, List<LogDetail> details) {
        keyMap.clear();
        details.clear();
    }

    private LogKey createLogKey(String key, Date date, String log) {
        LogKey logKey = new LogKey();

        String ip = log.substring(log.indexOf(LOG_PART_IP) + 1, 32);

        logKey.setFirstEventDate(date);
        logKey.setLogKey(key);
        logKey.setIp(ip);

        return logKey;
    }

    private LogDetail createLogDetails(String message, Date date, String log, LogKey key) {
        LogDetail detail = new LogDetail();

        String eventType = getEventType(log, key.getIp());

        detail.setMessage(message);
        detail.setEventType(eventType);
        detail.setFullDetails(log);
        detail.setDate(date);
        detail.setLogKey(key);

        return detail;
    }

    private LogKey checkLogKey(Map<String, LogKey> keys, String key) {
        if (keys.containsKey(key)) {
            return keys.get(key);
        } else {
            LogKey dbKey = logService.getByKey(key);
            if (dbKey != null) {
                keys.put(key, dbKey);
            }
            return dbKey;
        }
    }

    private void updateLogStatus(LogKey logKey, String message) {
        for (MessageProcessor processor : messageProcessors) {
            processor.process(logKey, message);
        }
    }

    private String getEventType(String log, String ip) {
        Integer start = log.indexOf(ip) + ip.length();
        Integer end = log.indexOf(LOG_EVENT_TYPE_SEPARATOR);
        return log.substring(start, end).trim();
    }

    private boolean isSMTPLog(String log) {
        return log.contains(LOG_PART_IP) || log.contains(LOG_PART_OPENDKIM) || log.contains(LOG_PART_POSTFIX);
    }

    private boolean isLogWithKey(String[] splitLogKey) {
        return splitLogKey.length > LOG_WITH_KEY_MIN_LENGTH
                && !splitLogKey[LOG_KEY_VALUE].startsWith(LOG_PART_CONNECT)
                && !splitLogKey[LOG_KEY_VALUE].startsWith(LOG_PART_WARNING);
    }

    private boolean isKeyRegexpMatching(String[] splitLogKey) {
        return splitLogKey[LOG_KEY_VALUE].matches(LOG_KEY_REGEX);
    }

    @Autowired
    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    @Autowired
    public void setDetailsService(LogDetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @Autowired
    public void setMessageProcessors(List<MessageProcessor> messageProcessors) {
        this.messageProcessors = messageProcessors;
    }

    @Autowired
    public void setWebSocketService(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }
}
