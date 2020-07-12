package com.juxingtech.helmet.common.exception;

import com.juxingtech.helmet.common.result.IResultCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    public IResultCode resultCode;

    public CustomException(IResultCode errorCode) {
        super(errorCode.getMsg());
        this.resultCode = errorCode;
    }

    public CustomException(String message){
        super(message);
    }

    public CustomException(String message, Throwable cause){
        super(message, cause);
    }

    public CustomException(Throwable cause){
        super(cause);
    }
}
