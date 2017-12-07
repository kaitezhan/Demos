package com.boneix.client.result;

import java.util.List;

/**
 * @Authro qygu.
 * @Email qiyao.gu@qq.com.
 * @Date 2017/3/16.
 */
class PageResult<T> extends ListResult<T> {

    private static final long serialVersionUID = 7887542373165496853L;

    private Long total;

    public PageResult() {
    }

    PageResult(Long total, List<T> entities) {
        super(entities);
        this.total = total;
    }

    public Long getTotal() {
        return total;
    }
}