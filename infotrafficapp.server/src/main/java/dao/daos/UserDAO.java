package dao.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;

import dao.connection.ConnectionFactory;
import common.model.User;

public class UserDAO
{

    protected static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO user (username, mail, password)"
            + " VALUES (?, ?, ?)";
    private final static String findByIDStatementString = "SELECT * FROM user WHERE id_user = ?";
    private final static String findByMailStatementString = "SELECT * FROM user WHERE mail = ?";
    private final static String findByUsernameStatementString = "SELECT * FROM user WHERE username = ?";
    private final static String deleteStatementString = "DELETE FROM user WHERE mail = ?";
    private final static String editStatementString = "UPDATE user SET (pass, pass_nohash, is_admin) = (?, ?, ?, ?) WHERE mail = ?";
    private final static String findAllStatementString = "SELECT * FROM user";

    public static User findById(int id)
    {
        User toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try
        {
            findStatement = dbConnection.prepareStatement(findByIDStatementString);
            findStatement.setInt(1, id);
            rs = findStatement.executeQuery();

            if(rs.next())
            {
                String username = rs.getString("username");
                String mail = rs.getString("mail");
                String password = rs.getString("password");
                toReturn = new User(id, username, mail, password);
            }
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING,"UserDAO:findByUserMail " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static User findByMail(String userMail)
    {
        User toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try
        {
            findStatement = dbConnection.prepareStatement(findByMailStatementString);
            findStatement.setString(1, userMail);
            rs = findStatement.executeQuery();

            if(rs.next())
            {
                int id = rs.getInt("id_user");
                String username = rs.getString("username");
                String password = rs.getString("password");
                toReturn = new User(id, username, userMail, password);
            }
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING,"UserDAO:findByUserMail " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static User findByUsername(String username)
    {
        User toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try
        {
            findStatement = dbConnection.prepareStatement(findByUsernameStatementString);
            findStatement.setString(1, username);
            rs = findStatement.executeQuery();

            if(rs.next())
            {
                int id = rs.getInt("id_user");
                String mail = rs.getString("mail");
                String password = rs.getString("password");
                toReturn = new User(id, username, mail, password);
            }
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING,"UserDAO:findByUserMail " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static String insert(User user)
    {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        String insertedMail = "NULL";
        try
        {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, user.getUsername());
            insertStatement.setString(2, user.getMail());
            insertStatement.setString(3, user.getPassword());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            //something might be fucky (was designed to get id of next row)
            if (rs.next())
            {
                insertedMail = rs.getString(1);
            }
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, "UserDAO:insert " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedMail;
    }
    public static String edit(User user)
    {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement editStatement = null;
        String editedMail = "NULL";
        try
        {
            editStatement = dbConnection.prepareStatement(editStatementString, Statement.RETURN_GENERATED_KEYS);
            editStatement.setString(1, user.getUsername());
            editStatement.setString(2, user.getMail());
            editStatement.setString(3, user.getPassword());
            editStatement.executeUpdate();

            ResultSet rs = editStatement.getGeneratedKeys();
            //something might be fucky (was designed to get id of next row)
            if (rs.next())
            {
                editedMail = rs.getString(1);
            }
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, "UserDAO:edit " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(editStatement);
            ConnectionFactory.close(dbConnection);
        }
        return editedMail;
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        java.sql.ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }

    //builds a swing tablemodel
    public static DefaultTableModel findAll()
    {
        int toReturn = 0;
        DefaultTableModel returning = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findAllStatement = null;
        ResultSet rs = null;
        try
        {
            findAllStatement = dbConnection.prepareStatement(findAllStatementString);
            rs = findAllStatement.executeQuery();
            returning = buildTableModel(rs);
            while(rs.next())
            {
                int id = rs.getInt("id_user");
                String username = rs.getString("username");
                String mail = rs.getString("mail");
                String password = rs.getString("password");
                System.out.println("id: " + id + " username: " + username + " mail: " + mail + " password: " + password);
                toReturn++;
            }
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING,"UserDAO:findAll " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findAllStatement);
            ConnectionFactory.close(dbConnection);
        }
        return returning;
    }
}