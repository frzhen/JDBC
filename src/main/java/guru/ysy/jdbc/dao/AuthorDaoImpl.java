package guru.ysy.jdbc.dao;

import guru.ysy.jdbc.domain.Author;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by zhenrui on 2021/11/26 22:52
 */
@Component
public class AuthorDaoImpl implements AuthorDao {

    private final DataSource source;

    public AuthorDaoImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public Author getById(Long id) {
        Connection connection = null;
//        Statement statement = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = source.getConnection();
//            statement = connection.createStatement();
            ps = connection.prepareStatement("SELECT * FROM author where id = ?");
            ps.setLong(1,id);
//            resultSet = statement.executeQuery("SELECT * FROM author where id = " + id);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return getAuthorFromRS(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(connection,ps,resultSet);
        }
        return null;
    }

    @Override
    public Author getByName(String firstName, String lastName) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM author where first_name = ? AND " +
                                             "last_name = ? ");
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return getAuthorFromRS(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(connection,ps,resultSet);
        }
        return null;
    }

    private void closeAll(Connection connection,
                          PreparedStatement preparedStatement,
                          ResultSet resultSet) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Author getAuthorFromRS(ResultSet resultSet) throws SQLException {
            Author author = new Author();
            author.setId(resultSet.getLong("id"));
            author.setFirstName(resultSet.getString("first_name"));
            author.setLastName(resultSet.getString("last_name"));
            return author;
    }
}
