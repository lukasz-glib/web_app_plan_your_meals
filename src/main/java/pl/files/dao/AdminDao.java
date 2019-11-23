package pl.files.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.files.exception.NotFoundException;
import pl.files.model.Admin;
import pl.files.utils.DbUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {

    private static final String CREATE_ADMIN_QUERY = "INSERT INTO admins(first_name, last_name, email, password, superadmin, enable, salt) VALUES (?,?,?,?,?,?,?);";
    private static final String DELETE_ADMIN_QUERY = "DELETE FROM admins where id = ?;";
    private static final String FIND_ALL_ADMIN_QUERY = "SELECT * FROM admins;";
    private static final String READ_ADMIN_QUERY = "SELECT * from admins where id = ?;";
    private static final String UPDATE_ADMIN_QUERY = "UPDATE admins SET first_name = ?, last_name = ?, email = ?, password = ?, superadmin = ?," +
            "enable = ?, salt = ? WHERE	id = ?;";
    private static final String FIND_ADMIN = "SELECT * FROM admins WHERE email = ?;";

    /**
     * Get admin by id
     *
     * @param adminId
     * @return
     */
    public Admin read(Integer adminId) {
        Admin admin = new Admin();
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(AdminDao.READ_ADMIN_QUERY, true)
        ) {
            preparedStatement.setInt(1, adminId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    admin.setId(resultSet.getInt("id"));
                    admin.setFirstName(resultSet.getString("first_name"));
                    admin.setLastName(resultSet.getString("last_name"));
                    admin.setEmail(resultSet.getString("email"));
                    admin.setSuperadmin(resultSet.getInt("superadmin"));
                    admin.setEnable(resultSet.getInt("enable"));
                    admin.setSalt(resultSet.getString("salt"));
                    admin.setHashedPassword(resultSet.getString("password"));


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;

    }

    /**
     * Return all admins
     *
     * @return
     */
    public List<Admin> findAll() {
        List<Admin> adminList = new ArrayList<>();
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(AdminDao.FIND_ALL_ADMIN_QUERY, true);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Admin adminToAdd = new Admin();
                adminToAdd.setId(resultSet.getInt("id"));
                adminToAdd.setFirstName(resultSet.getString("first_name"));
                adminToAdd.setLastName(resultSet.getString("last_name"));
                adminToAdd.setEmail(resultSet.getString("email"));
                adminToAdd.setSuperadmin(resultSet.getInt("superadmin"));
                adminToAdd.setEnable(resultSet.getInt("enable"));
                adminToAdd.setEnable(resultSet.getInt("enable"));
                adminList.add(adminToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminList;

    }

    /**
     * Create admin
     *
     * @param admin
     * @return
     */
    public Admin create(Admin admin) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(AdminDao.CREATE_ADMIN_QUERY, true)) {
            preparedStatement.setString(1, admin.getFirstName());
            preparedStatement.setString(2, admin.getLastName());
            preparedStatement.setString(3, admin.getEmail());
            preparedStatement.setString(4, admin.getPassword());
            preparedStatement.setInt(5, admin.getSuperadmin());
            preparedStatement.setInt(6, admin.getEnable());
            preparedStatement.setString(7, admin.getSalt());

            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    admin.setId(generatedKeys.getInt(1));
                    return admin;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Remove admin by id
     *
     * @param adminId
     */
    public void delete(Integer adminId) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(AdminDao.DELETE_ADMIN_QUERY, true)) {
            preparedStatement.setInt(1, adminId);
            int deleted = preparedStatement.executeUpdate();
            if (deleted != 1) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Update admin
     *
     * @param admin
     */
    public void update(Admin admin) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(AdminDao.UPDATE_ADMIN_QUERY, true)) {
            preparedStatement.setInt(8, admin.getId());
            preparedStatement.setString(1, admin.getFirstName());
            preparedStatement.setString(2, admin.getLastName());
            preparedStatement.setString(3, admin.getEmail());
            preparedStatement.setString(4, admin.getPassword());
            preparedStatement.setInt(5, admin.getSuperadmin());
            preparedStatement.setInt(6, admin.getEnable());
            preparedStatement.setString(7, admin.getSalt());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Verify admin
     *
     * @param email
     * @param password
     */
    public int verify(String email, String password) {

        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(AdminDao.FIND_ADMIN, false)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next() && BCrypt.checkpw(password, resultSet.getString("password"))){
                return resultSet.getInt("id");
            } else {
                return -1;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
