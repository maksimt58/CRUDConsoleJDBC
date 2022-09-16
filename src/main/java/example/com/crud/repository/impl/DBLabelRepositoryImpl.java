package example.com.crud.repository.impl;

import example.com.crud.model.Label;
import example.com.crud.repository.LabelRepository;
import example.com.crud.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBLabelRepositoryImpl implements LabelRepository {
    private DBConnection dbConnection = null;
    private Connection connection = null;

    public DBLabelRepositoryImpl() {
        try {
            this.dbConnection = DBConnection.getInstance();
            connection = dbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Label getById(Long id) throws SQLException {
        List<Label> labelsList = getAll();

        for (Label label : labelsList) {
            if (label.getId().equals(id)) {
                return label;
            }
        }

        throw new SQLException("The line with the id does not exist");
    }

    @Override
    public boolean delete(Long id) {
        int countRowDeleted = 0;

        String SQL = "DELETE FROM Labels WHERE id = ?";

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
    public Label update(Label label) {

        int countRowUpdated;

        String SQLupdate = "UPDATE Labels SET name = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLupdate)) {
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

        String SQL = "SELECT * FROM labels ORDER BY id";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL))
        {
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

        String SQLinsert = "INSERT INTO Labels (name) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLinsert, Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(1, newLabelName);
            countRowInserted = preparedStatement.executeUpdate();

            if (countRowInserted != 1) {
                throw new SQLException("0 rows have been inserted");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    label.setId(generatedKeys.getLong(1));
                }
                else {
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
