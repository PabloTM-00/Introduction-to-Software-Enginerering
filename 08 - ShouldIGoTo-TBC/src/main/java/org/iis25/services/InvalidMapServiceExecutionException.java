package org.iis25.services;

import java.lang.RuntimeException;

public class InvalidMapServiceExecutionException extends RuntimeException{
    public InvalidMapServiceExecutionException(String msg){
        super(msg);
    }
}
