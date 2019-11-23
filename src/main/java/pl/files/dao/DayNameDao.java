package pl.files.dao;

import pl.files.exception.NotFoundException;
import pl.files.model.DayName;
import pl.files.utils.DbUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DayNameDao {

    // ZAPYTANIA SQL
    private static final String CREATE_DAY_NAME_QUERY = "INSERT INTO day_name(name, display_order) VALUES (?, ?);";
    private static final String DELETE_DAY_NAME_QUERY = "DELETE FROM day_name where id = ?;";
    private static final String FIND_ALL_DAY_NAME_QUERY = "SELECT * FROM day_name;";
    private static final String READ_DAY_NAME_QUERY = "SELECT * from day_name where id = ?;";
    private static final String UPDATE_DAY_NAME_QUERY = "UPDATE day_name SET name = ?, display_order = ? WHERE id = ?;";


    public List<DayName> findAll() {
        List<DayName> dayNameList = new ArrayList<>();
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(DayNameDao.FIND_ALL_DAY_NAME_QUERY,true);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                DayName dayNameToAdd = new DayName();
                dayNameToAdd.setId(resultSet.getInt("id"));
                dayNameToAdd.setName(resultSet.getString("name"));
                dayNameToAdd.setDisplayOrder(resultSet.getInt("display_order"));
                dayNameList.add(dayNameToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dayNameList;
    }


    public DayName read(Integer dayNameId) {
        DayName dayName = new DayName();
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(DayNameDao.READ_DAY_NAME_QUERY, true)
        ) {
            preparedStatement.setInt(1, dayNameId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    dayName.setId(resultSet.getInt("id"));
                    dayName.setName(resultSet.getString("name"));
                    dayName.setDisplayOrder(resultSet.getInt("display_order"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayName;
    }

    public DayName create(DayName dayName) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(DayNameDao.CREATE_DAY_NAME_QUERY, true)) {
            preparedStatement.setString(1, dayName.getName());
            preparedStatement.setInt(2, dayName.getDisplayOrder());
            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    dayName.setId(generatedKeys.getInt(1));
                    return dayName;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Integer dayNameID) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(DayNameDao.DELETE_DAY_NAME_QUERY,true)) {
            preparedStatement.setInt(1, dayNameID);
            int deleted = preparedStatement.executeUpdate();
            if (deleted != 1) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(DayName dayName) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(DayNameDao.UPDATE_DAY_NAME_QUERY,true)) {
            preparedStatement.setInt(3, dayName.getId());
            preparedStatement.setString(1, dayName.getName());
            preparedStatement.setInt(2, dayName.getDisplayOrder());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
