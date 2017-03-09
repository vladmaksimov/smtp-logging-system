package com.maksimov.service.impl;

import com.maksimov.models.entity.LogDetail;
import com.maksimov.models.entity.LogKey;
import com.maksimov.processors.MessageProcessor;
import com.maksimov.service.FileWatchService;
import com.maksimov.service.LogDetailsService;
import com.maksimov.service.LogService;
import com.maksimov.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created on 02.03.17.
 */
@Service
public class FileWatchRowServiceImpl implements FileWatchService {

    private static final Logger logger = Logger.getLogger(FileWatchRowServiceImpl.class);

    private static final String LOG_PART_IP = " ip-";
    private static final String LOG_PART_POSTFIX = "ip-";
    private static final String LOG_PART_OPENDKIM = "opendkim";
    private static final String LOG_PART_CONNECT = "connect";
    private static final String LOG_PART_WARNING = "warning";
    private static final String LOG_KEY_SEPARATOR = ": ";
    private static final String LOG_EVENT_TYPE_SEPARATOR = "[";
    private static final Integer LOG_SEPARATE_LIMIT = 3;
    private static final Integer LOG_WITH_KEY_MIN_LENGTH = 2;
    private static final Integer LOG_KEY_VALUE = 1;
    private static final Integer LOG_MESSAGE_VALUE = 2;

    private LogService logService;

    private LogDetailsService detailsService;

    private List<MessageProcessor> messageProcessors;


    @Override
    public void processFile(List<String> lines) {
        for (String log : lines) {
            processLogRow(log);
        }
    }

    @Override
    public long getFirstRow(String file) {
        return 0;
    }

    @Transactional
    private void processLogRow(String log) {
        if (log.contains(LOG_PART_IP) || log.contains(LOG_PART_OPENDKIM) || log.contains(LOG_PART_POSTFIX)) {
            String[] splits = log.split(LOG_KEY_SEPARATOR, LOG_SEPARATE_LIMIT);
            if (splits.length > LOG_WITH_KEY_MIN_LENGTH
                    && !splits[LOG_KEY_VALUE].startsWith(LOG_PART_CONNECT)
                    && !splits[LOG_KEY_VALUE].startsWith(LOG_PART_WARNING)) {

                String key = splits[LOG_KEY_VALUE];
                Date date = Utils.processDate(log.substring(0, log.indexOf(LOG_PART_IP)));
                String message = splits[LOG_MESSAGE_VALUE];

                LogKey logKey = checkKey(key);
                if (logKey == null) {
                    logKey = createLogKey(key, date, log);
                }

                createLogDetails(splits[LOG_MESSAGE_VALUE], date, log, logKey);
                updateLogStatus(logKey, message);

            }
        } else {
            logger.info("Row without smtp information: " + log);
        }
    }

    private LogKey createLogKey(String key, Date date, String log) {
        LogKey logKey = new LogKey();

        String ip = log.substring(log.indexOf(LOG_PART_IP) + 1, 32);

        logKey.setFirstEventDate(date);
        logKey.setLogKey(key);
        logKey.setIp(ip);

        logService.save(logKey);

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

        detailsService.save(detail);

        return detail;
    }


    private void updateLogStatus(LogKey logKey, String message) {
        for (MessageProcessor processor : messageProcessors) {
            processor.process(logKey, message);
        }

        logService.save(logKey);
    }

    private String getEventType(String log, String ip) {
        Integer start = log.indexOf(ip) + ip.length();
        Integer end = log.indexOf(LOG_EVENT_TYPE_SEPARATOR);
        return log.substring(start, end).trim();
    }

    private LogKey checkKey(String key) {
        return logService.getByKey(key);
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
}
