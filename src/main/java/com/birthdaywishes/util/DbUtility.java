package com.birthdaywishes.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.birthdaywishes.constants.Constants;

/**
 * @author Akash This interface contains data base related utility methods.
 *
 */
public class DbUtility implements DbUtilityInterface{

	/* (non-Javadoc)
	 * @see com.vishalstationers.util.DbUtilityInterface#getListOfToEmailAddress()
	 */
	public List<String> getListOfToEmailAddress(){
		List<String> listOfToEmailAddress=new ArrayList<String>();
		Connection con = getConnection();
		GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String selectQuery = "";		
		if (day == Constants.TWENTY_EIGHT && month == Constants.FEB && !cal.isLeapYear(year)) {
			selectQuery = "SELECT FIRST_NAME,LAST_NAME,EMAIL_ID,DOB" + " FROM REGISTERED_USER" + " WHERE EXTRACT(DAY FROM DOB)= " + day
					+ " OR    EXTRACT(DAY FROM DOB)= 29" + " AND   EXTRACT(MONTH FROM DOB)=" + month;
			listOfToEmailAddress=execute(con, selectQuery);
		} else {
			selectQuery = "SELECT FIRST_NAME,LAST_NAME,EMAIL_ID,DOB" + " FROM REGISTERED_USER" + " WHERE EXTRACT(DAY FROM DOB)= " + day
					+ " AND   EXTRACT(MONTH FROM DOB)= " + month;
			listOfToEmailAddress=execute(con, selectQuery);
		}
		return listOfToEmailAddress;
	}
	
	/**
	 * @return This method is for getting DB connection.
	 */
	private Connection getConnection() {
		Connection con = null;
		try {
			// 1.Load the driver.
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2.Get Connection.
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sample_project",
					"sample_project");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return con;
	}
	/**
	 * @param con
	 * @param selectQuery
	 * @return This method execute query and return result,(i.e)list of To email
	 *         Ids.
	 */
	private List<String> execute(Connection con, String selectQuery) {
		List<String> listOfToEmailAddress=new ArrayList<String>();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(selectQuery, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next() == false) {
				System.out.println("No record found");
			} else {
				resultSet.beforeFirst();
				while (resultSet.next() != false) {					
					listOfToEmailAddress.add(resultSet.getString("EMAIL_ID"));
				}
			}
		} catch (SQLException Ex) {
			Ex.printStackTrace();
			System.out.println(Ex.getMessage());
		} finally {
			try {
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

		}
		return listOfToEmailAddress;
	}

}
