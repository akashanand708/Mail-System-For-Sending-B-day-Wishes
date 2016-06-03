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
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.birthdaywishes.constants.Constants;
import com.birthdaywishes.dto.User;

/**
 * @author Akash This class many utility methods.
 *
 */
public class Utility implements UtilityInterface {

	private final Logger log = Logger.getLogger(Utility.class);

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
	public Message createMimeMessageAndSend(Session session, List<User> listOfToEmailId, String subject, String text) {
		Address toAddress = null;
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(Constants.FROM_EMAIL_ID));
			message.setSubject(subject);
			// Create Multi Part object.
			MimeMultipart multiPart = new MimeMultipart();
			// Body part 1.
			MimeBodyPart bodyPart = new MimeBodyPart();
			String bdayHtmlPath = Config.getProperty("resources.bday.html.path");
			String htmlText = "";
			if (bdayHtmlPath != null) {
				htmlText = readHtmlFile(bdayHtmlPath);
			}			
			// Add Body part 1 to Multi Part.
			multiPart.addBodyPart(bodyPart);
			
			// Body part 2.
			MimeBodyPart imageBodyPart = new MimeBodyPart();
			String bdayImagePath = Config.getProperty("resources.bday.image.path");
			if (bdayImagePath != null) {
				imageBodyPart.attachFile(bdayImagePath);
			}
			imageBodyPart.setContentID("<" + "image" + ">");
			imageBodyPart.setDisposition(MimeBodyPart.INLINE);

			// Add Body part 2 to Multi Part.
			multiPart.addBodyPart(imageBodyPart);
			message.setContent(multiPart);
			// Send mail to each recipient address.
			for (User user : listOfToEmailId) {
				String emailId = user.getEmail_id();
				toAddress = new InternetAddress(emailId);
				MimeMultipart mimeMulti=(MimeMultipart)message.getContent();
				MimeBodyPart bodypart=(MimeBodyPart) mimeMulti.getBodyPart(0);
				bodyPart.setText(htmlText.replace("there", user.getFirst_name()+" "),"US-ASCII", "html");
				message.setRecipient(RecipientType.TO, toAddress);
				// 4.Send mail.
				Transport.send(message);
			}
			System.out.println("Sent mail successfully....");

		} catch (AddressException addressException) {
			log.error(addressException);
		} catch (MessagingException messagingException) {
			System.out.println("Mail sending failled....");
			log.error(messagingException);
		} catch (IOException e) {
			log.error(e);
		}
		return message;
	}

	/**
	 * @param fileName
	 * @return This method reads HTML file and return string.
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
			if (bufferReader != null) {
				bufferReader.close();
			}
		} catch (FileNotFoundException ex) {
			log.error(ex);
		} catch (IOException ex) {
			log.error(ex);
		}
		return stringBuffer.toString();
	}

}
