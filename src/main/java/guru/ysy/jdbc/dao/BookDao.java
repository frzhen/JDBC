package guru.ysy.jdbc.dao;

import guru.ysy.jdbc.domain.Book;

/**
 * Created by zhenrui on 2021/11/27 23:40
 */
public interface BookDao {

    Book getById(Long id);

    Book findByBookByTitle(String title);

    Book findByIsbn(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);
}
