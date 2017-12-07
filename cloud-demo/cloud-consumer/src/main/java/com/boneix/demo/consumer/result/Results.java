package com.boneix.demo.consumer.result;



import com.boneix.demo.consumer.result.base.ResultEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回结果集静态工厂方法类
 *
 * @Authro qygu.
 * @Email qiyao.gu@qq.com.
 * @Date 2017/4/7.
 */
public final class Results {

    private Results() {
    }

    private static <T> ListResult<T> newListResult(List<T> entities) {
        return new ListResult<>(entities);
    }

    private static <T> SingleResult<T> newSingleResult(T entity) {
        return new SingleResult<>(entity);
    }

    private static <T> PageResult<T> newPageResult(Long total, List<T> entities) {
        return new PageResult<>(total, entities);
    }

    public static <T> ResultEntity newListResultEntity(List<T> entities) {
        return newListResult(entities).toResultEntity();
    }

    public static <T> ResultEntity newSingleResultEntity(T entity) {
        return newSingleResult(entity).toResultEntity();
    }

    public static <T> ResultEntity newPageResultEntity(Long total, List<T> entities) {
        return newPageResult(total, entities).toResultEntity();
    }

    public static ResultEntity newNormalResultEntity(String key, Object value) {
        Map<String, Object> param = new HashMap<>();
        param.put(key, value);

        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setData(param);

        return resultEntity;
    }

    public static ResultEntity newEmptyResultEntity() {
        return new ResultEntity();
    }

    public static ResultEntity newErrorResultEntity(long code, String message) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setCode(code);
        resultEntity.setMessage(message);

        return resultEntity;
    }

    public static ResultEntity newErrorResultEntity(long code, String message, Object data) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setCode(code);
        resultEntity.setMessage(message);
        resultEntity.setData(data);
        return resultEntity;
    }
}