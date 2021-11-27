package guru.ysy.jdbc;

import guru.ysy.jdbc.dao.AuthorDao;
import guru.ysy.jdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by zhenrui on 2021/11/27 19:35
 */
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.ysy.jdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void testGetAuthorById() {
        Author author = authorDao.getById(1L);
        assertThat(author.getId()).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        Author author = authorDao.findAuthorByName("Eric", "Evans");
        assertThat(author).isNotNull();
    }

    @Test
    void testSaveAuthor() {
        Author author = new Author();
        author.setFirstName("Fred");
        author.setLastName("Zhen");
        Author saved = authorDao.saveNewAuthor(author);

        assertThat(saved).isNotNull();
    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setFirstName("Fred");
        author.setLastName("Zhen");
        Author saved = authorDao.saveNewAuthor(author);

        saved.setLastName("R.Zhen");
        Author updated = authorDao.updateAuthor(saved);

        assertThat(updated.getLastName()).isNotNull();
    }

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("Fred");
        author.setLastName("Zhen");

        Author saved = authorDao.saveNewAuthor(author);

        authorDao.deleteAuthorById(saved.getId());

        assertThrows(EmptyResultDataAccessException.class,
                () -> authorDao.getById(saved.getId()));
    }
}
