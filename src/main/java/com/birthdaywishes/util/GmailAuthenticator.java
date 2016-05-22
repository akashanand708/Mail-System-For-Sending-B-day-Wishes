package com.birthdaywishes.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author Akash
 *Wrapper Authenticator class for gmail username and password authentication. 
 */
public class GmailAuthenticator extends Authenticator {
	private String username;
	private String password;
	public GmailAuthenticator(String username,String password){
		super();
		this.username=username;
		this.password=password;
	}
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
     }
}
