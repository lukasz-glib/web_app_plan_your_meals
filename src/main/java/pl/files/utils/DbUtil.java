package pl.files.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class DbUtil {
    private static DataSource dataSource;

    public static Connection getConnection() throws SQLException {

        Connection connection;

        try {
             connection = getInstance().getConnection();

        } catch (Exception e){
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/scrumlab?useSSL=false&characterEncoding=utf8&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "coderslab"
            );

        }
        return connection;
    }

    private static DataSource getInstance() {
        if (dataSource == null) {
            try {
                Context context = new InitialContext();
                dataSource = (DataSource) context.lookup("java:comp/env/jdbc/scrumlab");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return dataSource;
    }

    public static PreparedStatement prepareStatement(String QUERY, Boolean edit) throws SQLException {

        if (edit) {
            PreparedStatement preparedStatement = DbUtil.getConnection().prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
            return preparedStatement;
        } else {
            PreparedStatement preparedStatement = DbUtil.getConnection().prepareStatement(QUERY);
            return preparedStatement;
        }
    }
}