package dao.daos;

import common.model.Alert;
import common.model.Like;
import dao.connection.ConnectionFactory;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LikesDAO
{
    protected static final Logger LOGGER = Logger.getLogger(AlertDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO alert_likes (liking_user, liked_alert)"
            + " VALUES (?, ?)";
    private final static String countLikesStatementString = "SELECT * FROM alert_likes WHERE liked_alert = ?";

    public static int insert(Like like)
    {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = connection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, like.getLiking_user());
            insertStatement.setInt(2, like.getLiked_alert());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if(rs.next())
            {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "LikesDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(connection);
        }
        return insertedId;
    }

    public static int count(Alert alert)
    {
        int total = 0;

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement countLikesStatement = null;
        ResultSet rs = null;
        try {
            countLikesStatement = connection.prepareStatement(countLikesStatementString);
            countLikesStatement.setInt(1, alert.getId());

            rs = countLikesStatement.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id_like");
                int liking_user = rs.getInt("liking_user");
                int liked_alert = rs.getInt("liked_alert");

                total++;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"LikesDAO:count " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(countLikesStatement);
            ConnectionFactory.close(connection);
        }
        return total;
    }
}
