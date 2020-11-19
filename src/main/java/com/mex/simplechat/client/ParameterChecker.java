package com.mex.simplechat.client;

import com.mex.simplechat.exception.ParameterStartWithError;
import com.mex.simplechat.exception.ParameterCountError;

public class ParameterChecker {

    private final String[] parameters;
    private static final String USER_PARAMETER_START_WITH = "/user:";

    public ParameterChecker(String[] parameters) {
        this.parameters = parameters;
    }

    public String getUser() throws ParameterCountError, ParameterStartWithError {
        if(parameters == null || parameters.length != 1) {
            throw new ParameterCountError("Parameters should not be more than 1");
        } else if(parameters[0].indexOf(USER_PARAMETER_START_WITH) != 0) {
            throw new ParameterStartWithError(String.format("Parameters should start with %s", USER_PARAMETER_START_WITH));
        }
        return parameters[0].substring(parameters[0].indexOf(USER_PARAMETER_START_WITH));
    }
}
