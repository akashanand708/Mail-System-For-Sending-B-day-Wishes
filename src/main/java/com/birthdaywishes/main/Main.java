package com.birthdaywishes.main;

import java.util.List;

import org.apache.log4j.PropertyConfigurator;

import com.birthdaywishes.dto.User;
import com.birthdaywishes.mail.Mail;
import com.birthdaywishes.util.DbUtility;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.init();
		// Get listOfToEmailAddress.
		DbUtility dbUtility = new DbUtility();
		System.out.println("Fetching Records....");
		List<User> listOfToEmailAddress = dbUtility.getListOfToEmailAddress();

		// Send B'day Mail to all listOfToEmailAddress.
		Mail mail = new Mail();
		if (listOfToEmailAddress != null && !listOfToEmailAddress.isEmpty()) {
			System.out.println("Sending mail....");
			mail.sendMail(listOfToEmailAddress);
		}
	}

	private void init() {
		// Load log4j.properties.
		String log4jConfPath = "resources/log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
	}
}