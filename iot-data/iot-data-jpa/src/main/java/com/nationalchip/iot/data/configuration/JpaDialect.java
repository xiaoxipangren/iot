package com.nationalchip.iot.data.configuration;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;

import java.sql.SQLException;

public class JpaDialect extends EclipseLinkJpaDialect {


    private static final SQLStateSQLExceptionTranslator SQLSTATE_EXCEPTION_TRANSLATOR = new SQLStateSQLExceptionTranslator();

    @Override
    public DataAccessException translateExceptionIfPossible(final RuntimeException ex) {
        final DataAccessException dataAccessException = super.translateExceptionIfPossible(ex);

        if (dataAccessException == null) {
            return searchAndTranslateSqlException(ex);
        }

        return translateJpaSystemExceptionIfPossible(dataAccessException);
    }

    private static DataAccessException translateJpaSystemExceptionIfPossible(
            final DataAccessException accessException) {
        if (!(accessException instanceof JpaSystemException)) {
            return accessException;
        }

        final DataAccessException sql = searchAndTranslateSqlException(accessException);
        if (sql == null) {
            return accessException;
        }

        return sql;
    }

    private static DataAccessException searchAndTranslateSqlException(final RuntimeException ex) {
        final SQLException sqlException = findSqlException(ex);

        if (sqlException == null) {
            return null;
        }

        return SQLSTATE_EXCEPTION_TRANSLATOR.translate(null, null, sqlException);
    }

    private static SQLException findSqlException(final RuntimeException jpaSystemException) {
        Throwable exception = jpaSystemException;
        do {
            final Throwable cause = exception.getCause();
            if (cause instanceof SQLException) {
                return (SQLException) cause;
            }
            exception = cause;
        } while (exception != null);

        return null;
    }
}
