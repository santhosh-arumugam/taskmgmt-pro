package com.development.taskmgmt_pro.exception;

public class DuplicateUserException extends RuntimeException{

    public DuplicateUserException(String message){
        super(message);
    };
}
