package guru.ysy.jdbc.dao;

import guru.ysy.jdbc.domain.Book;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by zhenrui on 2021/11/27 13:59
 */
@Component
public class BookDaoImpl implements BookDao {

    private final DataSource source;
    private final AuthorDao authorDao;

    public BookDaoImpl(DataSource source, AuthorDao authorDao) {
        this.source = source;
        this.authorDao = authorDao;
    }

    @Override
    public Book getById(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = source.getConnection();
            ps = conn.prepareStatement("SELECT * FROM book WHERE id = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return getBookFromRS(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(conn, ps, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public Book findByBookByTitle(String title) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = source.getConnection();
            ps = conn.prepareStatement("SELECT * FROM book WHERE title = ?");
            ps.setString(1, title);
            rs = ps.executeQuery();

            if (rs.next()) {
                return getBookFromRS(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(conn, ps, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Book findByIsbn(String isbn) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = source.getConnection();
            ps = conn.prepareStatement("SELECT * FROM book WHERE isbn = ?");
            ps.setString(1, isbn);
            rs = ps.executeQuery();

            if (rs.next()) {
                return getBookFromRS(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(conn, ps, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Book saveNewBook(Book book) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = source.getConnection();
            ps = conn.prepareStatement("INSERT INTO book " +
                                       "(isbn, publisher, title, author_id) " +
                                       "VALUES (?,?,?,?)");
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getPublisher());
            ps.setString(3, book.getTitle());

            if (book.getAuthor() != null) {
                ps.setLong(4, book.getAuthor().getId());
            } else {
                ps.setNull(4, -5);
            }
            ps.execute();

            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

            if (rs.next()) {
                Long savedId = rs.getLong(1);
                return this.getById(savedId);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(conn, ps, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void updateBook(Book book) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = source.getConnection();
            ps = conn.prepareStatement("UPDATE book " +
                                       "SET isbn = ?, publisher = ?, title = ?, author_id = ? " +
                                       "WHERE id = ?");
            ps.setString(1,book.getIsbn());
            ps.setString(2, book.getPublisher());
            ps.setString(3, book.getTitle());
            if (book.getAuthor() != null) {
                ps.setLong(4, book.getAuthor().getId());
            }
            ps.setLong(5, book.getId());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(conn,ps,rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteBookById(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = source.getConnection();
            ps = conn.prepareStatement("DELETE FROM book WHERE id = ?");
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(conn, ps, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeAll(Connection connection,
                          PreparedStatement preparedStatement,
                          ResultSet resultSet) throws SQLException {
        if (connection != null) {
            connection.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (resultSet != null) {
            resultSet.close();
        }
    }

    private Book getBookFromRS(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setPublisher(resultSet.getString("publisher"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setAuthor(authorDao.getById(resultSet.getLong("author_id")));

        return book;
    }
}
