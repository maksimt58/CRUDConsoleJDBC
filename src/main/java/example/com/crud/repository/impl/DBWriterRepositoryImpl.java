package example.com.crud.repository.impl;

import example.com.crud.model.Post;
import example.com.crud.model.PostStatus;
import example.com.crud.model.Writer;
import example.com.crud.repository.WriterRepository;
import example.com.crud.utils.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBWriterRepositoryImpl implements WriterRepository {
    private final static String SQL_QUERY_UPDATE = "UPDATE writers SET first_name = ?, last_name = ? WHERE id = ?";
    private final static String SQL_QUERY_DELETE = "DELETE FROM writers WHERE id = ?";
    private final static String SQL_QUERY_INSERT = "INSERT INTO writers (first_name, last_name) VALUES (?, ?)";
    private final static String SQL_QUERY_SELECT_ALL = "SELECT * FROM writers ORDER BY id";
    private final static String SQL_QUERY_SELECT_BY_ID = "SELECT * FROM writers WHERE id = ?";

    @Override
    public Writer getById(Long id) {
        Writer writer = null;
        List<Post> postsList;

        try (PreparedStatement preparedStatement = ConnectionUtils.preparedStatement(SQL_QUERY_SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long writerId = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);

                postsList = getAllPostsByWriter(id);

                writer = new Writer(writerId, firstName, lastName, postsList);
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error getting all instances");
            e.printStackTrace();
        }

        return writer;
    }

    @Override
    public boolean delete(Long id) {
        int countRowDeleted = 0;

        try (PreparedStatement preparedStatement = ConnectionUtils.preparedStatement(SQL_QUERY_DELETE)) {
            preparedStatement.setLong(1, id);
            countRowDeleted = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error deleting instance");
            e.printStackTrace();
        }

        return countRowDeleted > 0;
    }

    @Override
    public Writer update(Writer writer) {
        Writer writerUpdated = null;

        int countRowUpdated;

        try (PreparedStatement preparedStatement = ConnectionUtils.preparedStatement(SQL_QUERY_UPDATE)) {
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            preparedStatement.setLong(3, writer.getId());
            countRowUpdated = preparedStatement.executeUpdate();

            if (countRowUpdated == 1) {
                writerUpdated = getById(writer.getId());
            } else {
                throw new SQLException("0 or more than 1 rows have been updated");
            }

        } catch (SQLException e) {
            System.out.println("Error updating instance" + e.getMessage());
            e.printStackTrace();
        }

        return writerUpdated;
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writersList = new ArrayList<>();
        List<Post> postsList;

        try (Statement statement = ConnectionUtils.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_QUERY_SELECT_ALL)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);

                postsList = getAllPostsByWriter(id);

                Writer writer = new Writer(id, firstName, lastName, postsList);
                writersList.add(writer);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all instances");
            e.printStackTrace();
        }

        return writersList;
    }

    @Override
    public Writer save(Writer writer) {
        int countRowInserted;

        try (PreparedStatement preparedStatement = ConnectionUtils.preparedStatementWithGeneratedKeys(SQL_QUERY_INSERT)) {
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            countRowInserted = preparedStatement.executeUpdate();

            if (countRowInserted != 1) {
                throw new SQLException("0 rows have been inserted");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    writer.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating label failed.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error inserting instance" + e.getMessage());
            e.printStackTrace();
        }

        return writer;
    }

    private List<Post> getAllPostsByWriter(Long id) {
        List<Post> resultListPost = new ArrayList<>();

        String SQL = "SELECT p.id, p.content, p.status " +
                "FROM writers w JOIN posts p " +
                "ON p.writer_id = w.id " +
                "WHERE w.id = ?";

        try (PreparedStatement preparedStatement = ConnectionUtils.preparedStatement(SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long postId = resultSet.getLong(1);
                String postName = resultSet.getString(2);
                String postStatus = resultSet.getString(3);
                resultListPost.add(new Post(postId, postName, PostStatus.valueOf(postStatus)));
            }

            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultListPost;
    }
}
