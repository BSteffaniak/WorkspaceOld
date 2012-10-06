package net.foxycorndog.jdooglandroid.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.foxycorndog.jdooglandroid.input.KeyboardInput;

public class Connector
{
	private boolean    connected;
	
	private Connection conn = null;
	
	public Connector()
	{
		
	}
	
	public void connect(String ip, String scheme, String username, String password)
	{
		connect(ip, 0, scheme, username, password);
	}
	
	public void connect(String ip, int port, String scheme, String username, String password)
	{
		try
		{
			// The newInstance() call is a work around for some
			// broken Java implementations
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}
		catch (Exception ex)
		{
			// handle the error
		}
		
		try
		{
		    conn = DriverManager.getConnection(("jdbc:mysql://" + ip + (port > 0 ? (":" + port) : "") + "/" + scheme), username, password);
		    
		    connected = true;
		}
		catch (SQLException ex)
		{
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public void sendQuery(String query)
	{
		Statement statement = null;
		
		try
		{
			statement = conn.createStatement();
			
			statement.executeQuery(query);
		}
		catch (SQLException e)
		{
			try
			{
				statement.executeUpdate(query);
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
	}
	
	public Result getResult(String query)
	{
		try
		{
			Statement statement = conn.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			
			return new Result(rs);
			
//			while (rs.next())
//			{
//				String id = rs.getString("id");
//				String name = rs.getString("name");
//				
//				System.out.println("id: " + id + ", name: " + name);
//			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public boolean isConnected()
	{
		return connected;
	}
}