package com.boneix.base.dao.constants;

/**
 * Mybatis Sql脚本的ID名称
 *
 * @author hu_guodong
 * @version [1.0, 2014年12月8日]
 */
public interface SqlId {
    String SQL_SELECT_COUNT = "selectCount";

    String SQL_SELECT = "select";

    String SQL_SELECT_BY_ID = "selectById";

    String SQL_UPDATE_BY_ID = "updateById";

    String SQL_UPDATE_BY_ID_SELECTIVE = "updateByIdSelective";

    String SQL_DELETE = "delete";

    String SQL_DELETE_BY_ID = "deleteById";

    String SQL_DELETE_BY_ID_IN_LOGICAL = "deleteByIdInLogical";

    String SQL_DELETE_IN_LOGICAL = "deleteInLogical";

    String SQL_INSERT = "insert";

    String SQL_CHECK_NAME = "checkName";
}
