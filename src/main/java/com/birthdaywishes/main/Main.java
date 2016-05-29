package com.birthdaywishes.main;

import java.util.List;

import com.birthdaywishes.mail.Mail;
import com.birthdaywishes.util.DbUtility;

public class Main {
   public static void main(String[] args) {
	   
	   //Get listOfToEmailAddress.
	   DbUtility dbUtility=new DbUtility();
	   System.out.println("Fetching Records....");
	   List<String> listOfToEmailAddress=dbUtility.getListOfToEmailAddress();
	   
	   //Send B'day Mail to all listOfToEmailAddress.
	   Mail mail=new Mail();
	   if(listOfToEmailAddress!=null && !listOfToEmailAddress.isEmpty()){
		   System.out.println("Sending mail....");
		   mail.sendMail(listOfToEmailAddress);
	   }
   }
}