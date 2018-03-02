package com.nationalchip.iot.exception;


import com.nationalchip.iot.log.ILoggable;

public class UncheckedException extends RuntimeException implements ILoggable {
    private boolean logged;
    private String message;

    public UncheckedException(String message){
        super(message);
    }

    public UncheckedException(){
        super();
    }

    public UncheckedException(String message, Throwable cause){
        super(message,cause);
    }

    public UncheckedException(Throwable cause){
        super(cause);
    }


    public String printTraceStack(){
        StringBuilder sb = new StringBuilder();

        Throwable cause = this;
        while(cause!=null){
            sb.append(String.format("%s:%s\n",getClass().getName(),getMessage()));
            cause=cause.getCause();
        }
        return sb.toString();

    }


    @Override
    public boolean logged() {
        return logged;
    }

    @Override
    public String log() {
        logged=true;
        return printTraceStack();
    }
}
