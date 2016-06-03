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

import org.apache.log4j.Logger;

import com.birthdaywishes.constants.Constants;
import com.birthdaywishes.dto.User;

/**
 * @author Akash This interface contains data base related utility methods.
 *
 */
public class DbUtility implements DbUtilityInterface{

	private final static Logger log=Logger.getLogger(DbUtility.class);
	/* (non-Javadoc)
	 * @see com.vishalstationers.util.DbUtilityInterface#getListOfToEmailAddress()
	 */
	public List<User> getListOfToEmailAddress(){
		List<User> listOfToEmailAddress=new ArrayList<User>();
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
			log.error(e);			
		} catch (SQLException e) {
			log.error(e);
		}
		return con;
	}
	/**
	 * @param con
	 * @param selectQuery
	 * @return This method execute query and return result,(i.e)list of To email
	 *         Ids.
	 */
	private List<User> execute(Connection con, String selectQuery) {
		List<User> listOfToEmailAddress=new ArrayList<User>();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(selectQuery, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next() == false) {
				System.out.println("No record found");
			} else {
				resultSet.beforeFirst();
				while (resultSet.next() != false) {	
					User user=new User();
					user.setEmail_id(resultSet.getString("EMAIL_ID"));
					user.setFirst_name(resultSet.getString("FIRST_NAME"));
					listOfToEmailAddress.add(user);
				}
			}
		} catch (SQLException Ex) {
			log.error(Ex);			
		} finally {
			try {
				stmt.close();
				con.close();
			} catch (SQLException e) {
				log.error(e);				
			}

		}
		return listOfToEmailAddress;
	}

}
