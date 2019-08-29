package com.turing.community.message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Meng
 * @date 2019/8/16
 */
public class Message {
    private int state;

    private String message;


    private Map<String, Object> extend = new HashMap<String, Object>();

    public static Message success(){
        Message result = new Message();
        result.setCode(100);
        result.setMsg("执行成功");
        return result;
    }

    public static Message fail(){
        Message result = new Message();
        result.setCode(200);
        result.setMsg("执行失败");
        return result;
    }


    public Message add(String key,Object value){
        this.getExtend().put(key, value);
        return this;
    }

    public int getCode() {
        return state;
    }

    public void setCode(int code) {
        this.state = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}
