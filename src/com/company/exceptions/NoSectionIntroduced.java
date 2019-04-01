package com.company.exceptions;

public class NoSectionIntroduced extends ParsingConfigException {
    public NoSectionIntroduced(String str) {
        super("Undefined section for line " + str);
    }
}
