package com.acme.biz.api.enums;

/**
 * 状态码
 * @author: wuhao
 * @time: 2025/3/6 14:33
 * @see org.springframework.http.HttpStatus
 */
public enum StatusCode {
    OK (0,"OK"){
        @Override
        public String getMessage() {
            return "OK";
        }
    },
    FAILED(-1,"Failed"),
    CONTINUE(1,"{status-code.continue}");


    private int code;

    private final String message; //需要进行国际化

    StatusCode(int code ,String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getLocalizedMessage(){
        // FIXME 需要支持国际化
        // 如果message是占位符 翻译message
        // 否则 直接返回
        return message;
    }
}
