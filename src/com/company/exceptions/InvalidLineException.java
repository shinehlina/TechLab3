package com.company.exceptions;

public class InvalidLineException extends ParsingConfigException {
    public InvalidLineException(String s) {
        super("Line " + s + " doesn't match neither header nor property format");
    }
}
