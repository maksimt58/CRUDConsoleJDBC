package example.com.crud.repository.impl;

import example.com.crud.model.Label;
import example.com.crud.model.Post;
import example.com.crud.model.PostStatus;
import example.com.crud.model.Writer;
import example.com.crud.repository.PostRepository;
import example.com.crud.utils.ConnectionUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBPostRepositoryImpl implements PostRepository {
    private final static String SQL_QUERY_UPDATE = "UPDATE posts SET content = ?, status = ?, writer_id = ? WHERE id = ?";
    private final static String SQL_QUERY_DELETE = "DELETE FROM posts WHERE id = ?";
    private final static String SQL_QUERY_INSERT = "INSERT INTO posts (content, writer_id, status) VALUES (?, ?, ?)";
    private final static String SQL_QUERY_INSERT_POST_LABELS = "INSERT INTO post_labels (post_id, label_id) values (? , ?)";
    private final static String SQL_QUERY_SELECT_BY_ID = "SELECT p.id, p.content, p.status, p.create_date, p.update_date, w.* " +
                                                         "FROM posts p JOIN writers w ON p.writer_id = w.id WHERE p.id = ?";
    private final static String SQL_QUERY_SELECT_ALL =  "SELECT p.id,p.content, p.status, p.create_date, p.update_date, w.* " +
                                                        "FROM posts p JOIN writers w ON p.writer_id = w.id ORDER BY p.id";

    @Override
    public Post getById(Long id) {
        Post post = null;
        Writer writer;

        try(PreparedStatement preparedStatement = ConnectionUtils.preparedStatement(SQL_QUERY_SELECT_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

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
            }

            resultSet.close();
        }
        catch (SQLException e) {
            System.out.println("Error getting instance");
            e.printStackTrace();
        }

        return post;
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
    public Post update(Post post) {
        Post postUpdated = null;
        int countRowUpdated;

        try (PreparedStatement preparedStatement = ConnectionUtils.preparedStatement(SQL_QUERY_UPDATE))
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

        try (Statement statement = ConnectionUtils.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_QUERY_SELECT_ALL))
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

        try (PreparedStatement preparedStatementInsertPost = ConnectionUtils.preparedStatementWithGeneratedKeys(SQL_QUERY_INSERT);
             PreparedStatement preparedStatementInsertPL = ConnectionUtils.preparedStatement(SQL_QUERY_INSERT_POST_LABELS))
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
                "FROM posts p JOIN post_labels pl " +
                "ON p.id = pl.post_id " +
                "JOIN labels l " +
                "ON pl.label_id = l.id " +
                "WHERE p.id = ?";

        try (PreparedStatement preparedStatement = ConnectionUtils.preparedStatement(SQL)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long labelId = resultSet.getLong(1);
                String labelName = resultSet.getString(2);
                resultListLabel.add(new Label(labelId, labelName));
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultListLabel;
    }}
