/**
 * 
 */
package com.framework.lib;
import static com.framework.lib.InitTests.getPropValue;
import static com.framework.lib.SoftAssertions.verifyNotEquals;
/**
 * @author YugandharReddyGorrep
 *
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.testng.Assert;

public class DatabaseUtils
{
	public static void executeBatchQueries(String querys[])
	{
		Connection dbCon = null;
		Statement statement = null;
		try
		{
			dbCon = getConnection(getPropValue("db_driver"), getPropValue("db_url"), getPropValue("db_username"),
					getPropValue("db_password"));
			statement = getStatement(dbCon);
			for (String query : querys)
				statement.addBatch(query);
			int[] recordsAffected = statement.executeBatch();
			for (int i : recordsAffected)
				System.out.println("no of records affected" + i);
		} catch (Exception e)
		{
			e.printStackTrace();
			// SoftAssertions.fail(e, getScreenPath(new
			// Exception().getStackTrace()[0].getMethodName()));
		} finally
		{
			if (statement != null)
			{
				try
				{
					statement.close();
					dbCon.close();
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static void executeUpdateQuery(String query)
	{
		Connection dbCon = null;
		Statement statement = null;
		try
		{
			dbCon = getConnection(getPropValue("db_driver"), getPropValue("db_url"), getPropValue("db_username"),
					getPropValue("db_password"));
			statement = getStatement(dbCon);
			int recordsAffected = statement.executeUpdate(query);
			System.out.println("no of records affected" + recordsAffected);
		} catch (Exception e)
		{
			e.printStackTrace();
			// SoftAssertions.fail(e, getScreenPath(new
			// Exception().getStackTrace()[0].getMethodName()));
		} finally
		{
			if (statement != null)
			{
				try
				{
					statement.close();
					dbCon.close();
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * @description:Returns the data base connection object
	 * 
	 * @param driver
	 * @param dburl
	 * @param userName
	 * @param password
	 * @return Connection
	 * @throws Exception
	 */
	public static Connection getConnection(String driver, String dburl, String userName, String password)
			throws ClassNotFoundException, SQLException
	{
		Connection con = null;
		Class.forName(driver);
		System.out.println("before connn" + dburl + userName + password);
		con = DriverManager.getConnection(dburl, userName, password);
		System.out.println("connected to DB");
		return con;
	}
	/**
	 * @description: Returns the database ResultSet object
	 * 
	 * @param connection
	 * @param databaseQuery
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public static ResultSet getResultSet(Connection connection, String databaseQuery) throws SQLException
	{
		PreparedStatement pst;
		ResultSet rs = null;
		pst = connection.prepareStatement(databaseQuery);
		rs = pst.executeQuery();
		return rs;
	}
	public static Statement getStatement(Connection connection)
	{
		try
		{
			return connection.createStatement();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Returns the no of records in the given table
	 * 
	 * @param connection
	 * @param databaseTableName
	 * 
	 * @return int
	 * @throws Exception
	 */
	public static int getNoOfRecordsInTable(Connection connection, String databaseTableName) throws SQLException
	{
		String query = "select count(*) from " + databaseTableName;
		PreparedStatement pst;
		ResultSet rs = null;
		pst = connection.prepareStatement(query);
		rs = pst.executeQuery();
		if (rs.next())
			return rs.getInt(1);
		else
			return 0;
	}
	/**
	 * Closes the database connection
	 * 
	 * @param connection
	 * 
	 * @throws Exception
	 */
	public static void closeConnection(Connection connection)
	{
		try
		{
			connection.close();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}