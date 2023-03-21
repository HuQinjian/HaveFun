package com.example.havefun;

/**
 * @Project: HaveFun
 * @Author: hqj
 * @Date: 2023/3/14 11:26
 * @Introduce:
 **/
public class TranslateResult {

    private String src;
    private String tgt;

    public TranslateResult(String src, String tgt) {
        this.src = src;
        this.tgt = tgt;
    }

    @Override
    public String toString() {
        return "TranslateResult{" +
                "src='" + src + '\'' +
                ", tgt='" + tgt + '\'' +
                '}';
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTgt() {
        return tgt;
    }

    public void setTgt(String tgt) {
        this.tgt = tgt;
    }
}
