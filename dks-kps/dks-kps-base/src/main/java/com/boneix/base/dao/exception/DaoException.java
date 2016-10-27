package com.boneix.base.dao.exception;

/**
 * Dao层异常信息
 *
 * @author hu_guodong
 * @version [1.0, 2014年12月8日]
 */
public class DaoException extends RuntimeException {

    private static final long serialVersionUID = 8350049272861703406L;

    public DaoException() {
        super();
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

}
