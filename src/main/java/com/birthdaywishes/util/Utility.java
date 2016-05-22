package com.birthdaywishes.util;

import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.birthdaywishes.constants.Constants;

/**
 * @author Akash
 * This class many utility methods.
 *
 */
public class Utility implements UtilityInterface {

	/* (non-Javadoc)
	 * @see com.vishalstationers.util.UtilityInterface#getSessionProperties()
	 */
	public Properties getSessionProperties() {
		Properties properties = new Properties();
		properties.put(Constants.MAIL_SMTP_HOST_KEY, Constants.MAIL_SMTP_HOST_VALUE);
		properties.put(Constants.MAIL_SMTP_PORT_KEY, Constants.MAIL_SMTP_PORT_VALUE);
		properties.put(Constants.MAIL_SMTP_STARTTLS_ENABLE_KEY, Constants.TRUE);
		properties.put(Constants.MAIL_SMTP_AUTH_KEY, Constants.TRUE);
		return properties;
	}

	/* (non-Javadoc)
	 * @see com.vishalstationers.util.UtilityInterface#createMimeMessage(javax.mail.Session, java.util.List, java.lang.String, java.lang.String)
	 */
	public Message createMimeMessage(Session session, List<String> listOfToEmailId, String subject, String text) {
		Address[] arrayOfToAddress = new Address[listOfToEmailId.size()];
		int i = 0;
		Message message = new MimeMessage(session);
		try {
			// Prepare array of To address.
			for (String emailId : listOfToEmailId) {
				arrayOfToAddress[i] = new InternetAddress(emailId);
				i++;
			}
			message.setFrom(new InternetAddress(Constants.FROM_EMAIL_ID));
			message.setRecipients(RecipientType.TO, arrayOfToAddress);
			message.setSubject(subject);
			message.setText(text);
		} catch (AddressException addressException) {
			addressException.printStackTrace();
			System.out.println(addressException.getMessage());
		} catch (MessagingException messagingException) {
			messagingException.printStackTrace();
			System.out.println(messagingException.getMessage());
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(exception.getMessage());
		}
		return message;
	}

}
