package com.zurich.qa.digitalnative.utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class EmailUtil {

	public static void sendEmail() throws EmailException
	{
		// Create the attachment
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(System.getProperty("user.dir") + "\\reports\\index.html");
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("Test Report");
		attachment.setName("Test Report");

		// Create the email message
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setTLS(true);
		email.setAuthenticator(new DefaultAuthenticator("nitin.sagroha@gmail.com", "Shaurya2012@"));
		email.setSSLOnConnect(true);
		email.addTo("Author@org.com", "Author");
		email.setFrom("Author@org.com", "Author");
		email.setSubject("Test Report");
		email.setMsg("Test Report");

		// add the attachment
		email.attach(attachment);

		// send the email
		email.send();
	}
}
