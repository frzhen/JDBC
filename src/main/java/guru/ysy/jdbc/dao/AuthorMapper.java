package guru.ysy.jdbc.dao;

import guru.ysy.jdbc.domain.Author;
import guru.ysy.jdbc.domain.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by zhenrui on 2021/11/27 21:58
 */
public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        rs.next();

        Author author = new Author();
        author.setId(rs.getLong("id"));
        author.setFirstName(rs.getString("first_name"));
        author.setLastName(rs.getString("last_name"));

        if(rs.getString("isbn") != null) {
            author.setBooks(new ArrayList<>());
            author.getBooks().add(mapBook(rs));
        }

        while (rs.next()) {
            author.getBooks().add(mapBook(rs));
        }

        return author;
    }

    private Book mapBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setPublisher(rs.getString("publisher"));
        book.setIsbn(rs.getString("isbn"));
        book.setAuthorId(rs.getLong("author_id"));
        return book;
    }
}
