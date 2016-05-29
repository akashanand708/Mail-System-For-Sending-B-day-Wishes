package com.birthdaywishes.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.birthdaywishes.constants.Constants;

/**
 * @author Akash This class many utility methods.
 *
 */
public class Utility implements UtilityInterface {

	private final Logger log=Logger.getLogger(Utility.class);
	/*
	 * (non-Javadoc)
	 * 
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vishalstationers.util.UtilityInterface#createMimeMessage(javax.mail.
	 * Session, java.util.List, java.lang.String, java.lang.String)
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

			// Create Multi Part object.
			MimeMultipart multiPart = new MimeMultipart();
			
			// Body part 1.
			MimeBodyPart bodyPart = new MimeBodyPart();
			String htmlText=readHtmlFile("resources/Birthday.html");
			bodyPart.setText(htmlText, "US-ASCII", "html");
			// Add Body part 1 to Multi Part.
			multiPart.addBodyPart(bodyPart);

			// Body part 2.
			MimeBodyPart imageBodyPart = new MimeBodyPart();
			imageBodyPart.attachFile("resources/Birthday.jpg");
			imageBodyPart.setContentID("<" + "image" + ">");
			imageBodyPart.setDisposition(MimeBodyPart.INLINE);

			// Add Body part 2 to Multi Part.
			multiPart.addBodyPart(imageBodyPart);
			message.setContent(multiPart);
		} catch (AddressException addressException) {
			log.error(addressException);			
		} catch (MessagingException messagingException) {
			log.error(messagingException);
		} catch (IOException e) {
			log.error(e);
		}
		return message;
	}

	/**
	 * @param fileName
	 * @return
	 * This method reads HTML file and return string.
	 */
	private String readHtmlFile(String fileName) {
		StringBuffer stringBuffer = new StringBuffer();
		FileReader fileReader = null;
		BufferedReader bufferReader = null;

		try {
			fileReader = new FileReader(fileName);
			String line = null;
			bufferReader = new BufferedReader(fileReader);

			while ((line = bufferReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append(System.getProperty("line.separator"));
			}			
		} catch (FileNotFoundException ex) {
			log.error(ex);
		} catch (IOException ex) {
			log.error(ex);
		}finally{
			try {
				bufferReader.close();
			} catch (IOException e) {
				log.error(e);
			}
		}
		return stringBuffer.toString();
	}

}
