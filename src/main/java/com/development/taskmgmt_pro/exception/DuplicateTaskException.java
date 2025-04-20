package com.development.taskmgmt_pro.exception;

public class DuplicateTaskException extends RuntimeException{
    public DuplicateTaskException(String message) {
        super(message);
    }
}
