package com.gestionStocks.gestionStock.exception;

import lombok.Getter;

import java.util.List;
//lorqu'on essaye d'enregistre dans la base mais que les donnes ne sont valides
public class InvalidEntityException extends RuntimeException{

    @Getter
    private ErrorCodes errorCode;
    @Getter
    private List<String> errors;

    public InvalidEntityException(String message) {
        super(message);
    }

    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEntityException(String message, Throwable cause, ErrorCodes errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public InvalidEntityException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    //in serviceImpl
    public InvalidEntityException(String message, ErrorCodes errorCode, List<String> errors) {
        super(message);
        this.errorCode = errorCode;
        this.errors = errors;
    }
}
