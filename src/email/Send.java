package email;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import util.Config;

public class Send {

	private List<String> recivers;
	private List<InternetAddress> reciverAddresses;
	private String subject;

	private String content;
	private String port;
	private String host ;
	private String username;
	private String password;
	private String sender ;

	public Send() throws IOException {

		Config config = new Config();
		port = config.getProperty("port", "25");
		host = config.getProperty("host", "smtp.163.com");
		username = config.getProperty("username","jinghuashuiyuezi" );
		password = config.getProperty("password", "c++!huibian@" );
		sender = config.getProperty("sender", "jinghuashuiyuezi@163.com");

		recivers = new ArrayList<String>();
		reciverAddresses = new ArrayList<InternetAddress>();
	}

	public List<String> getRecivers() {
		return recivers;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean send() {
		Properties p = new Properties();
		p.put("mail.smtp.host", host);
		p.put("mail.smtp.port", port);
		p.put("mail.smtp.auth", "true");
		p.put("mail.transport.protocol", "smtp");

		Session session = Session.getInstance(p);
		session.setDebug(true);
		Transport transport;
		try {
			transport = session.getTransport();
			transport.connect(host, username, password);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));

			for (String r : recivers) {
				reciverAddresses.add(new InternetAddress(r));
			}

			message.setRecipients(Message.RecipientType.TO,
					reciverAddresses.toArray(new InternetAddress[recivers.size()]));

			message.setSubject(subject);
			message.setContent(content, "text/html;charset=gbk");

			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			System.err.println(new Date());
			System.err.println("send mail fail");
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
