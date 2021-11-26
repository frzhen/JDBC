package guru.ysy.jdbc;

import guru.ysy.jdbc.dao.AuthorDao;
import guru.ysy.jdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by zhenrui on 2021/11/26 22:53
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
        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        Author author = authorDao.getByName("Eric", "Evans");
        assertThat(author).isNotNull();
    }
}
