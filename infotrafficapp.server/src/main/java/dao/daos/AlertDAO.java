package dao.daos;

        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Statement;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.logging.Level;
        import java.util.logging.Logger;

        import dao.connection.ConnectionFactory;
        import common.model.Alert;

public class AlertDAO
{

    protected static final Logger LOGGER = Logger.getLogger(AlertDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO alert (type, location, submission_time, status, submitting_user)"
            + " VALUES (?, ?, ?, ?, ?)";
    private final static String findByIDStatementString = "SELECT * FROM alert WHERE id_alert = ?";
    private final static String deleteStatementString = "UPDATE alert SET status = 'deleted' WHERE id_alert = ?";
    private final static String findAllActiveStatementString = "SELECT * FROM alert WHERE status = 'active'";

    public static Alert findById(int alertId)
    {
        Alert toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try
        {
            findStatement = dbConnection.prepareStatement(findByIDStatementString);
            findStatement.setInt(1, alertId);
            rs = findStatement.executeQuery();

            if(rs.next())
            {
                String type = rs.getString("type");
                String location = rs.getString("location");
                String submission_time = rs.getString("submission_time");
                String status = rs.getString("status");
                int submitting_user = rs.getInt("submitting_user");
                toReturn = new Alert(alertId, type, location, submission_time, status, submitting_user);
            }
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING,"AlertDAO:findById " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static int delete(Alert alert)
    {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement editStatement = null;
        int deletedId = -1;
        try
        {
            editStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            editStatement.setInt(1, alert.getId());
            editStatement.executeUpdate();

            ResultSet rs = editStatement.getGeneratedKeys();
            //something might be fucky (was designed to get id of next row)
            if (rs.next())
            {
                deletedId = rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, "AlertDAO:delete " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(editStatement);
            ConnectionFactory.close(dbConnection);
        }
        return deletedId;
    }

    public static int insert(Alert alert)
    {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try
        {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, alert.getType());
            insertStatement.setString(2, alert.getLocation());
            insertStatement.setString(3, alert.getSubmission_time());
            insertStatement.setString(4, alert.getStatus());
            insertStatement.setInt(5, alert.getSubmitting_user());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            //something might be fucky (was designed to get id of next row)
            if (rs.next())
            {
                insertedId = rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, "AlertDAO:insert " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    public static List<Alert> findAllActive()
    {
        int toReturn = 0;
        List<Alert> returning = new ArrayList<Alert>();

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findAllStatement = null;
        ResultSet rs = null;
        try
        {
            findAllStatement = dbConnection.prepareStatement(findAllActiveStatementString);
            rs = findAllStatement.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt("id_alert");
                String type = rs.getString("type");
                String location = rs.getString("location");
                String submission_time = rs.getString("submission_time");
                String status = rs.getString("status");
                int submitting_user = rs.getInt("submitting_user");
                Alert temp = new Alert(id, type, location, submission_time, status, submitting_user);
                returning.add(temp);
                System.out.println("id: " + id + " type: " + type + " location: " + location + " submission_time: " + submission_time +
                                        " status: " + status + " submitting_user: " + submitting_user);
                toReturn++;
            }
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING,"AlertDAO:findAllActive " + e.getMessage());
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