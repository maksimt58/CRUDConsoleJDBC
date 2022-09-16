package example.com.crud.repository.impl;

import example.com.crud.model.Label;
import example.com.crud.model.Post;
import example.com.crud.model.PostStatus;
import example.com.crud.model.Writer;
import example.com.crud.repository.PostRepository;
import example.com.crud.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBPostRepositoryImpl implements PostRepository {
    private DBConnection dbConnection = null;
    private Connection connection = null;

    public DBPostRepositoryImpl() {
        try {
            this.dbConnection = DBConnection.getInstance();
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post getById(Long id) throws SQLException {
        List<Post> postsList = getAll();

        Post getPostById = postsList.stream().filter(post -> post.getId().equals(id)).findFirst().orElse(null);

        if (getPostById != null) {
            return getPostById;
        } else {
            throw new SQLException("The line with the id does not exist");
        }
    }

    @Override
    public boolean delete(Long id) {
        int countRowDeleted = 0;

        String SQL = "DELETE FROM posts WHERE id = ?";

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
    public Post update(Post post) {
        Post postUpdated = null;
        int countRowUpdated;

        String SQLupdate = "UPDATE Posts SET content = ?, status = ?, writerId = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLupdate))
        {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setString(2, post.getPostStatus().toString());
            preparedStatement.setLong(3, post.getWriterId());
            preparedStatement.setLong(4, post.getId());
            countRowUpdated = preparedStatement.executeUpdate();

            if (countRowUpdated == 1) {
                postUpdated = getById(post.getId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return postUpdated;
    }

    @Override
    public List<Post> getAll() {
        List<Post> postsList = new ArrayList<>();
        Post post;
        Writer writer;
        String SQL = "SELECT p.id,p.content, p.status, p.createdate, p.updatedate, w.* " +
                "FROM posts p JOIN writers w " +
                "ON p.writerid = w.id ";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL))
        {
            while (resultSet.next()) {
                Long postId = resultSet.getLong(1);
                String postName = resultSet.getString(2);
                String postStatus = resultSet.getString(3);
                Timestamp createdDate = resultSet.getTimestamp(4);
                Timestamp updatedDate = resultSet.getTimestamp(5);

                Long writerId = resultSet.getLong(6);
                String writerFirstName = resultSet.getString(7);
                String writerLastName = resultSet.getString(8);

                List<Label> labelsList = getLabelsByPost(postId);

                writer = new Writer(writerId, writerFirstName, writerLastName);

                post = new Post(postId, postName, PostStatus.valueOf(postStatus));
                post.setCreated(createdDate);
                post.setUpdated(updatedDate);
                post.setLabels(labelsList);
                post.setWriter(writer);

                postsList.add(post);
            }

        } catch (SQLException e) {
            System.out.println("Error getting instance");
            e.printStackTrace();
        }

        return postsList;
    }

    @Override
    public Post save(Post post) {
        List<Label> postLabels = post.getLabels();
        int countInsertedRows;
        int countInsertedRowPostLabels = 0;

        String SQLinsert = "INSERT INTO Posts (content, WriterId, Status) VALUES (?, ?, ?)";
        String SQLinsertPostLabel = "INSERT INTO postlabel (postid, labelid) values (? , ?)";

        try (PreparedStatement preparedStatementInsertPost = connection.prepareStatement(SQLinsert, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedStatementInsertPL = connection.prepareStatement(SQLinsertPostLabel))
        {
            preparedStatementInsertPost.setString(1, post.getContent());
            preparedStatementInsertPost.setLong(2, post.getWriterId());
            preparedStatementInsertPost.setString(3, post.getPostStatus().toString());
            countInsertedRows = preparedStatementInsertPost.executeUpdate();

            if (countInsertedRows != 1) {
                throw new SQLException("0 rows have been inserted");
            }

            try (ResultSet generatedKeys = preparedStatementInsertPost.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating post failed.");
                }
            }

            preparedStatementInsertPL.setLong(1, post.getId());

            for (Label label : postLabels) {
                preparedStatementInsertPL.setLong(2, label.getId());
                countInsertedRowPostLabels += preparedStatementInsertPL.executeUpdate();
            }

            boolean checkInsertLabelsPost = countInsertedRowPostLabels == postLabels.size();

        } catch (SQLException e) {
            System.out.println("Error inserting instance");
            e.printStackTrace();
        }

        return post;
    }

    private List<Label> getLabelsByPost(Long id) {
        List<Label> resultListLabel = new ArrayList<>();

        String SQL = "SELECT l.id, l.name " +
                "FROM posts p JOIN postlabel pl " +
                "ON p.id = pl.postid " +
                "JOIN labels l " +
                "ON pl.labelid = l.id " +
                "where p.id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long labelId = resultSet.getLong(1);
                String labelName = resultSet.getString(2);
                resultListLabel.add(new Label(labelId, labelName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultListLabel;
    }}
