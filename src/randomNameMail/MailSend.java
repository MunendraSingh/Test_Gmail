package randomNameMail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSend extends MailsendRandomEmail {
	
	public void mailOutlook() throws FileNotFoundException, IOException{
		// Recipient's email ID needs to be mentioned.
	/*	Properties prop = new Properties();
		prop.load(new FileInputStream(projDir + "\\src\\login.properties"));*/
		String to = "sikarwar.munendra@gmail.com";//change accordingly

	      // Sender's email ID needs to be mentioned
	      String from = "Munendra.Sikarwar007@outlook.com";//change accordingly
	      
	      final String username = "Munendra.Sikarwar007@outlook.com";//change accordingly
	      final String password = "Munendra.Sikarwar";//change accordingly

	      // Outlook's SMTP server
	      String host = "smtp-mail.outlook.com";

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "587");
	      props.put("mail.smtp.ssl.trust", host);

	      // Get the Session object.
	      Session session = Session.getInstance(props,
	      new javax.mail.Authenticator() {
	         protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	         }
	      });

	      try {
	         // Create a default MimeMessage object.
	         Message message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.setRecipients(Message.RecipientType.TO,
	         InternetAddress.parse(to));

	      // Set Subject: header field
	         message.setSubject("Outlook - Email Test");

	         // Now set the actual message
	         message.setText("Hello, this is sample email to check/send "
	            + "email using JavaMailAPI from Outlook" + "NumofMail: "+ NumofMail);

	         // Send message
	         Transport.send(message);

	         System.out.println("Sent message successfully.... from Outlook");

	      } catch (MessagingException e) {
	            throw new RuntimeException(e);
	      }
	   }
	}
