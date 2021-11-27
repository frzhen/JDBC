package guru.ysy.jdbc;

import guru.ysy.jdbc.dao.BookDao;
import guru.ysy.jdbc.domain.Book;
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
 * Created by zhenrui on 2021/11/27 23:42
 */
@ActiveProfiles({"local"})
@DataJpaTest
@ComponentScan(basePackages = {"guru.ysy.jdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoIntegrationTest {
    @Autowired
    BookDao bookDao;

    @Test
    void testGetBookById() {
        Book book = bookDao.getById(1L);
        assertThat(book).isNotNull();
    }

    @Test
    void testFindBookByTitle() {
        Book book = bookDao.findByBookByTitle("Clean Code");
        assertThat(book).isNotNull();
    }

    @Test
    void testFindBookByIsbn() {
        Book book = bookDao.findByIsbn("978-1617294945");
        assertThat(book).isNotNull();
    }

    @Test
    void testSaveNewBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Unknown");
        book.setTitle("A Great Book");
        book.setAuthorId(3L);
        Book saved = bookDao.saveNewBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Unknown");
        book.setTitle("A Great Book");
        book.setAuthorId(3L);

        Book saved = bookDao.saveNewBook(book);

        saved.setTitle("The Greatest Book");
        bookDao.updateBook(saved);

        Book fetched = bookDao.getById(saved.getId());

        assertThat(fetched.getTitle()).isEqualTo("The Greatest Book");
    }

    @Test
    void testDeleteBookById() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Unknown");
        book.setTitle("A Great Book");
        Book saved = bookDao.saveNewBook(book);

        bookDao.deleteBookById(saved.getId());

        assertThrows(EmptyResultDataAccessException.class,
                () -> bookDao.getById(saved.getId()));
    }
}
