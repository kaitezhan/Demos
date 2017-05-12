package com.boneix.base.resolver;

import com.alibaba.fastjson.JSONObject;

/**
 * JSONObject包装类 由于JSONObject实现了Map结果，所以Spring MVC3的默认处理器MapMethodProcessor会先起作用，这样就不能正常的映射成JSONObject对象
 * 
 * @author iteye.zjumty
 * @version [1.0, 2016年1月10日]
 */
public class JSONObjectWrapper {
    private JSONObject jsonObject;

    public JSONObjectWrapper(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJSONObject() {
        return jsonObject;
    }
}
