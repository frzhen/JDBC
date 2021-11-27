package guru.ysy.jdbc.dao;

import guru.ysy.jdbc.domain.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zhenrui on 2021/11/27 23:45
 */
public class BookMapper implements RowMapper<Book> {
    @Override
    public Book  mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setPublisher(rs.getString("publisher"));
        book.setIsbn(rs.getString("isbn"));
        book.setAuthorId(rs.getLong("author_id"));
        return book;
    }
}
