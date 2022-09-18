package example.com.crud.repository.impl;

import example.com.crud.model.Label;
import example.com.crud.repository.LabelRepository;
import example.com.crud.utils.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBLabelRepositoryImpl implements LabelRepository {
    private final static String SQL_QUERY_UPDATE = "UPDATE labels SET name = ? WHERE id = ?";
    private final static String SQL_QUERY_DELETE = "DELETE FROM labels WHERE id = ?";
    private final static String SQL_QUERY_INSERT = "INSERT INTO labels (name) VALUES (?)";
    private final static String SQL_QUERY_SELECT_ALL = "SELECT * FROM labels ORDER BY id";
    private final static String SQL_QUERY_SELECT_BY_ID = "SELECT * FROM labels WHERE id = ?";

    @Override
    public Label getById(Long id) {
        Label label = null;

        try (PreparedStatement preparedStatement = ConnectionUtils.preparedStatement(SQL_QUERY_SELECT_BY_ID))
        {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long labelId = resultSet.getLong("id");
                String labelName = resultSet.getString("name");

                label = new Label(labelId, labelName);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all instances");
            e.printStackTrace();
        }

        return label;
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
    public Label update(Label label) {

        int countRowUpdated;

        try (PreparedStatement preparedStatement = ConnectionUtils.preparedStatement(SQL_QUERY_UPDATE)) {
            preparedStatement.setString(1, label.getNameLabel());
            preparedStatement.setLong(2, label.getId());
            countRowUpdated = preparedStatement.executeUpdate();

            if (countRowUpdated != 1) {
                throw new SQLException("0 or more than 1 rows have been updated");
            }

        } catch (SQLException e) {
            System.out.println("Error updating instance" + e.getMessage());
            e.printStackTrace();
        }

        return label;
    }

    @Override
    public List<Label> getAll() {
        List<Label> labelsList = new ArrayList<>();

        try (Statement statement = ConnectionUtils.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_QUERY_SELECT_ALL)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Label label = new Label(id, name);
                labelsList.add(label);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all instances");
            e.printStackTrace();
        }

        return labelsList;
    }

    @Override
    public Label save(Label label) {
        String newLabelName = label.getNameLabel();
        int countRowInserted;

        try (PreparedStatement preparedStatement = ConnectionUtils.preparedStatementWithGeneratedKeys(SQL_QUERY_INSERT)) {
            preparedStatement.setString(1, newLabelName);
            countRowInserted = preparedStatement.executeUpdate();

            if (countRowInserted != 1) {
                throw new SQLException("0 rows have been inserted");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    label.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating label failed.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error inserting instance" + e.getMessage());
            e.printStackTrace();
        }

        return label;
    }
}
