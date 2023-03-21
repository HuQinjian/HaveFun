package com.example.havefun;

import java.util.List;

/**
 * @Project: HaveFun
 * @Author: hqj
 * @Date: 2023/3/14 11:25
 * @Introduce:
 **/
public class TranslateBean {
    private String type;
    private int errorCode;
    private int elapsedTime;
    private List<List<TranslateResult>> translateResult;

    public TranslateBean(String type, int errorCode, int elapsedTime, List<List<TranslateResult>> translateResult) {
        this.type = type;
        this.errorCode = errorCode;
        this.elapsedTime = elapsedTime;
        this.translateResult = translateResult;
    }

    @Override
    public String toString() {
        return "TranslateBean{" +
                "type='" + type + '\'' +
                ", errorCode=" + errorCode +
                ", elapsedTime=" + elapsedTime +
                ", translateResult=" + translateResult +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public List<List<TranslateResult>> getTranslateResult() {
        return translateResult;
    }

    public void setTranslateResult(List<List<TranslateResult>> translateResult) {
        this.translateResult = translateResult;
    }
}
