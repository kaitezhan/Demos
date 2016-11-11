package com.boneix.demo.domain;


/**
 * 此类描述的是：请求返回的公用数据
 *
 * @author xujinfei
 */

public class ResponseVo {
    // 成功标记
    private boolean success = true;

    // 错误码
    private int errorCode = CommonError.SUCCESS.getErrorCode();

    // 错误信息
    private String msg = "";

    // 返回的具体数据
    private Object data;

    /**
     * success
     *
     * @return the success
     */

    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * errorCode
     *
     * @return the errorCode
     */

    public int getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * data
     *
     * @return the data
     */

    public Object getData() {
        return data;
    }

    /**
     * @param data the data to set
     */

    public void setData(Object data) {
        this.data = data;
    }

    /*
     * http://bug.tuniu.org/issues/26368 代码里设置数组时没提供统一方法来实现这个约束，每个人自己写自己的,导致不统一
     */
    /*
     * public <T> void setData(List<T> dataList) { ResponseDataVo dataVo = new ResponseDataVo(); dataVo.setCount(dataList.size());
     * dataVo.setRows(dataList); this.data = dataVo; }
     */

    /**
     * msg
     *
     * @return the msg
     */

    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

