package com.catv.p2p.base.util;

import lombok.Getter;
import lombok.Setter;

/**
 * ajax请求的响应
 */
@Setter
@Getter
public class AjaxResult {
    private boolean success = true;
    private String msg;

    public AjaxResult() {
    }

    public AjaxResult(String msg) {

        this.msg = msg;
    }

    public AjaxResult(Boolean success) {

        this.success = success;
    }

    public AjaxResult(boolean success, String msg) {

        this.success = success;
        this.msg = msg;
    }
}
