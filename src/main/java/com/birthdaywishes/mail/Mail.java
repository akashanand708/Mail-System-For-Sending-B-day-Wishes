package com.birthdaywishes.mail;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import org.apache.log4j.Logger;

import com.birthdaywishes.constants.Constants;
import com.birthdaywishes.util.GmailAuthenticator;
import com.birthdaywishes.util.Utility;

/**
 * @author Akash
 * This class does send mail activity.
 *
 */
public class Mail {
	private final Logger log=Logger.getLogger(Mail.class); 
	/**
	 * @param toEmailList
	 * This method sends mail to all list of recipients emailIds.
	 */
	public void sendMail(List<String> toEmailList) {
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
		Message message = utility.createMimeMessage(session, toEmailList, subject, text);

		// 4.Send mail.
		try {
			Transport.send(message);
			System.out.println("Sent mail successfully....");
		} catch (MessagingException e) {
			log.error(e);
		}
	}
}
