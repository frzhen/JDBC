package guru.ysy.jdbc.dao;

import guru.ysy.jdbc.domain.Author;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * Created by zhenrui on 2021/11/27 19:33
 */
@Component
public class AuthorDaoImpl implements AuthorDao {
    @Override
    public Author getById(Long id) {
        return null;
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }

    private RowMapper<Author> getRowMapper() {
        return new AuthorMapper();
    }
}
