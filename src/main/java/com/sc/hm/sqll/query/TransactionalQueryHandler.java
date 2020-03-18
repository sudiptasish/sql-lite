/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sc.hm.sqll.query;

import com.sc.hm.sqll.config.ConnectionCache;
import com.sc.hm.sqll.model.TableData;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Sudiptasish Chanda
 */
public class TransactionalQueryHandler extends QueryHandler {

    @Override
    public TableData query(ConnectionCache cache, Query query) throws SQLException {
        Connection conn = cache.getConnection();
        if (QueryHandler.COMMIT_QUERY == query.getType()) {
            conn.commit();
        }
        else if (QueryHandler.ROLLBACK_QUERY == query.getType()) {
            conn.rollback();
        }
        else {
            throw new SQLException(new IllegalArgumentException("Invalid txn query type: " + query.getType()));
        }
        return null;
    }
    
}
