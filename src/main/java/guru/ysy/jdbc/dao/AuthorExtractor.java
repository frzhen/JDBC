package guru.ysy.jdbc.dao;

import guru.ysy.jdbc.domain.Author;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zhenrui on 2021/11/28 00:49
 */
public class AuthorExtractor implements ResultSetExtractor<Author> {

    @Override
    public Author extractData(ResultSet rs) throws SQLException, DataAccessException {
        return new AuthorMapper().mapRow(rs,0);
    }
}
