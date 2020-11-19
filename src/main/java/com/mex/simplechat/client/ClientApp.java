package com.mex.simplechat.client;

import com.mex.simplechat.exception.ParameterCountError;
import com.mex.simplechat.exception.ParameterStartWithError;

import java.util.logging.Logger;

public class ClientApp {

    private Logger logger = Logger.getLogger(String.valueOf(ClientApp.class));

    private final String[] parameters;
    private final ParameterChecker parameterChecker;

    public ClientApp(String[] parameters) {
        this.parameters = parameters;
        parameterChecker = new ParameterChecker(parameters);
    }

    public void run() {
        String user = null;
        try {
            user = parameterChecker.getUser();
        } catch (ParameterCountError | ParameterStartWithError parameterCountError) {
            logger.severe(parameterCountError.getMessage());
            return;
        }



    }
}
