package net.foxycorndog.jdoogl.connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class Result
{
	private ResultSet resultSet;
	
	public Result(ResultSet resultSet)
	{
		this.resultSet = resultSet;
	}
	
	public String[][] getData()
	{
		try
		{
			resultSet.beforeFirst();
			
			int columns = 0;
			int rows    = 0;
			
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			columns = rsmd.getColumnCount();
			
			resultSet.last();
			
			rows = resultSet.getRow();
			
			resultSet.beforeFirst();
			
			String[][] strings = new String[rows][];
			
			for (int row = 0; resultSet.next(); row ++)
			{
				strings[row] = new String[columns];
				
				for (int i = 1; i <= columns; i ++)
				{
					strings[row][i - 1] = resultSet.getString(i);
				}
			}
			
			return strings;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getData(String columnName, int row)
	{
		try
		{
			resultSet.beforeFirst();
			
			for (int i = 0; resultSet.next() && i < row; i ++)
			{
				
			}
			
			return resultSet.getString(columnName);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		
		return null;
	}
}