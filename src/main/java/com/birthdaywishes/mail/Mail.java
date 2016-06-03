package com.birthdaywishes.mail;

import java.util.List;
import java.util.Properties;

import javax.mail.Session;

import com.birthdaywishes.constants.Constants;
import com.birthdaywishes.dto.User;
import com.birthdaywishes.util.GmailAuthenticator;
import com.birthdaywishes.util.Utility;

/**
 * @author Akash
 * This class does send mail activity.
 *
 */
public class Mail {	
	/**
	 * @param toEmailList
	 * This method sends mail to all list of recipients emailIds.
	 */
	public void sendMail(List<User> toEmailList) {
		String subject = Constants.HAPPY_BIRTHDAY;
		String text = Constants.HAPPY_BIRTHDAY_TEXT;
		final String username = "test007110390@gmail.com";
		final String password = "akashanand";

		// 1.Set host properties;
		Utility utility = new Utility();
		Properties props = utility.getSessionProperties();

		// 2.Get the Session object.
		Session session = Session.getDefaultInstance(props, new GmailAuthenticator(username, password));

		// 3.Create message.
		utility.createMimeMessageAndSend(session, toEmailList, subject, text);

	}
}
