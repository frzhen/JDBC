package guru.ysy.jdbc.dao;

import guru.ysy.jdbc.domain.Author;

/**
 * Created by zhenrui on 2021/11/26 22:51
 */
public interface AuthorDao {

    Author getById(Long id);

    Author getByName(String firstName, String lastName);
}
