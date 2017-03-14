package com.maksimov.watcher;

import com.maksimov.service.FileWatchService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 01.03.17.
 */
@Service
public class LogFileWatcher implements InitializingBean {

    private static final Logger logger = Logger.getLogger(LogFileWatcher.class);

    @Value("${watch.path.folder}")
    private String path;
    @Value("${watch.path.file}")
    private String file;

    private FileWatchService watchService;

    private class FileWatcher extends Thread {
        FileWatcher() {
            super("File watcher");
            setPriority(NORM_PRIORITY);
        }

        @Override
        public void run() {
            logger.info("Start watcher!");
            createWatcher();
        }

        private void createWatcher() {
            String pathToFile = path + file;
            Path folder = Paths.get(path);
            Long rowCount = watchService.getFirstRow(pathToFile);

            try {
                WatchService watcher = folder.getFileSystem().newWatchService();
                folder.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
                rowCount = processLines(pathToFile, rowCount);

                while (!interrupted()) {
                    WatchKey key = watcher.take();
                    List<WatchEvent<?>> events = key.pollEvents();
                    rowCount = checkLineCount(pathToFile, rowCount);

                    for (WatchEvent event : events) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY && event.context().toString().equals(file)) {
                            rowCount = processLines(pathToFile, rowCount);
                        }
                    }

                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }

            } catch (NoSuchFileException ex) {
                logger.error("Error to create watcher! Incorrect file path: " + file);
            } catch (Exception e) {
                logger.error("Error to create watcher: " + e.toString());
            }
        }

    }

    private Long processLines(String pathToFile, Long rowCount) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(pathToFile), StandardCharsets.UTF_8)) {
            List<String> line = reader.lines().skip(rowCount).collect(Collectors.toList());

            logger.info("Rows to process: " + line.size());

            watchService.processFile(line);
            rowCount += line.size();
        } catch (IOException e) {
            logger.error("Can't get data from file: " + file + ". Error: " + e.getMessage());
        }
        return rowCount;
    }

    private Long checkLineCount(String pathToFile, Long count) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(pathToFile), StandardCharsets.UTF_8)) {
            return (count < reader.lines().count()) ? count : 0;
        } catch (IOException e) {
            logger.error("Can't get row count from file: " + file + ". Error: " + e.getMessage());
        }
        return 0L;
    }

    @Autowired
    @Qualifier("fileWatchCollectionServiceImpl")
    public void setWatchService(FileWatchService watchService) {
        this.watchService = watchService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new FileWatcher().start();
    }
}
