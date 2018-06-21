package com.ws.config;


public class JsonResponse<R> {

    JsonResponse() {
    }

    public JsonResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 业务是否处理成功
     */
    private Boolean success = false;

    /**
     * 消息代码
     */
    private String code = "";

    /**
     * 提示信息内容
     */
    private String msg = "";

    /**
     * 返回结果对象
     */
    private R resultObject;

    private Object detailErrors;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setIntCode(Integer code) {
        this.code = code + "";
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public R getResultObject() {
        return resultObject;
    }

    public void setResultObject(R resultObject) {
        this.resultObject = resultObject;
    }

    public Object getDetailErrors() {
        return detailErrors;
    }

    public void setDetailErrors(Object detailErrors) {
        this.detailErrors = detailErrors;
    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", resultObject=" + resultObject +
                ", detailErrors=" + detailErrors +
                '}';
    }

}
