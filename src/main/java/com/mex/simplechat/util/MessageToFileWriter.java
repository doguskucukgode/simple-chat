package com.mex.simplechat.util;

import com.mex.simplechat.config.AppConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class MessageToFileWriter {

    private Logger logger = Logger.getLogger(String.valueOf(MessageToFileWriter.class));
    private String user;

    public MessageToFileWriter(String user) {
        this.user = user;
        createFile();
    }

    private void createFile() {
        try {
            FileWriter fileWriter = new FileWriter(String.format(AppConfig.MESSAGE_FILE, user), false);
            fileWriter.close();
        } catch (IOException e) {
            logger.severe("File not found, " + e.getMessage());
        }
    }

    public void appendFile(String content) {
        try {
            FileWriter fileWriter = new FileWriter(String.format(AppConfig.MESSAGE_FILE, user), true);
            fileWriter.append(content + "\n");
            fileWriter.close();
        } catch (IOException e) {
            logger.severe("File not found, " + e.getMessage());
        }
    }

}
