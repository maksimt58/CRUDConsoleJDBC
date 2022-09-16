package example.com.crud.repository.impl;

import example.com.crud.model.Post;
import example.com.crud.model.PostStatus;
import example.com.crud.model.Writer;
import example.com.crud.repository.WriterRepository;
import example.com.crud.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBWriterRepositoryImpl implements WriterRepository {
    private DBConnection dbConnection = null;
    private Connection connection = null;


    public DBWriterRepositoryImpl() {
        try {
            this.dbConnection = DBConnection.getInstance();
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Writer getById(Long id) throws SQLException {
        List<Writer> writersList = getAll();

        for (Writer writer : writersList) {
            if (writer.getId().equals(id)) {
                return writer;
            }
        }

        throw new SQLException("The line with the id does not exist");
    }

    @Override
    public boolean delete(Long id) {
        int countRowDeleted = 0;

        String SQL = "DELETE FROM writers WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
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

        String SQLupdate = "UPDATE writers SET firstname = ?, lastname = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLupdate))
        {
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

        String SQL = "SELECT * FROM writers ORDER BY id";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL))
        {
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

        String SQLinsert = "INSERT INTO writers (firstname, lastname) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLinsert, Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            countRowInserted = preparedStatement.executeUpdate();

            if (countRowInserted != 1) {
                throw new SQLException("0 rows have been inserted");
            }

            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                if (generatedKeys.next()) {
                    writer.setId(generatedKeys.getLong(1));
                }
                else {
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
                "ON p.writerid = w.id " +
                "WHERE w.id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL))
        {
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
