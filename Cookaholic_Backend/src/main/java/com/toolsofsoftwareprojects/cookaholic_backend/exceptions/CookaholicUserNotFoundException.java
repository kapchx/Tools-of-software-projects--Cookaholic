package com.toolsofsoftwareprojects.cookaholic_backend.exceptions;

    public class CookaholicUserNotFoundException extends RuntimeException {
        public CookaholicUserNotFoundException(String message) {
            super(message);
        }
    }

