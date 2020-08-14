package com.zsy.args;

import lombok.Getter;
import lombok.Setter;

import static com.zsy.args.ErrorCode.*;

@Setter
@Getter
public class ArgsException extends Exception {
    private char errorArgumentId = '\0';
    private String errorParameter = null;
    private ErrorCode errorCode = OK;

    public ArgsException() {
    }

    public ArgsException(String message) {
        super(message);
    }

    public ArgsException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ArgsException(ErrorCode errorCode, String errorParameter) {
        this.errorCode = errorCode;this.errorParameter = errorParameter;
    }

    public ArgsException(ErrorCode errorCode,char errorArgumentId, String errorParameter) {
        this.errorCode = errorCode;this.errorParameter = errorParameter;
        this.errorArgumentId = errorArgumentId;
    }

    public String errorMessage() {
        switch (errorCode) {
            case OK:
                return "TILT:Should not get here.";
            case UNEXPECTED_ARGUMENT:
                return String.format("Argument -sc unexpected.", errorArgumentId);
            case MISSING_STRING:
                return String.format("Could not find string parameter for -sc.", errorArgumentId);
            case INVALID_INTEGER:
                return String.format("Argument -c expects an integer but was '%s'.", errorArgumentId, errorParameter);
            case MISSING_INTEGER:
                return String.format("Could not find integer parameter for -*c.", errorArgumentId);
            case INVALID_DOUBLE:
                return String.format("Argument-sc expects a double but was '%s'.", errorArgumentId, errorParameter);
            case MISSING_DOUBLE:
                return String.format("Could not find double parameter for -8c.", errorArgumentId);
            case INVALID_ARGUMENT_NAME:
                return String.format("'%c'is not a valid argument name.", errorArgumentId);
            case INVALID_ARGUMENT_FORMAT:
                return String.format("'%s' is not a valid argument format.", errorParameter);
            default:
                return "";
        }
    }
}
