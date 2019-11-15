package com.ntocc.utils;

import java.util.Map;

/**
 * @ClassName Page
 * @Description: 返回数据类型，以map分装
 * @Author 熊强
 * @Date 2019/11/7
 * @Version V1.0
 */

public class ResultPage {
    private ResultPage(){}

    private ResultPage(String msg, int code, Object data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    private String msg; // 信息
    private int code; // 响应码 0正确 1失败

    private Object data; // 存放数据

    // 对外开放的接口
    public static ResultPage success(Object data){
        return new ResultPage("成功",0,data);
    }

    public static ResultPage failed(String msg){
        return new ResultPage(msg,1,null);
    }



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
