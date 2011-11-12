package stegen.server.mail;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import javax.mail.*;

import org.junit.*;

import stegen.shared.*;

import com.google.appengine.api.mail.MailServicePb.MailMessage;
import com.google.appengine.tools.development.testing.*;

public class MailBuilderTest {

	private final LocalMailServiceTestConfig config = new LocalMailServiceTestConfig();
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(config);

	private static final EmailAddressDto email = new EmailAddressDto("address");
	private static final String nickname = "nickname";
	private MailBuilder mailBuilder;

	@Before
	public void setup() {
		helper.setUp();
		mailBuilder = new MailBuilder();
	}

	@After
	public void teardown() {
		helper.tearDown();
	}

	@Test
	public void testMailBuilder() {
		assertNotNull(mailBuilder);
	}

	@Test
	public void testFrom() throws Exception {
		mailBuilder.from(email, nickname);
		Address[] from = mailBuilder.mailMessage.getFrom();
		assertEquals(1, from.length);
		assertEquals("nickname <address>", from[0].toString());
	}

	@Test
	public void testToEmailAddressDtoString() throws MessagingException {
		mailBuilder.to(email, nickname);
		Address[] to = mailBuilder.mailMessage.getAllRecipients();
		assertEquals(1, to.length);
		assertEquals("nickname <address>", to[0].toString());
	}

	@Test
	public void testToEmailAddressDto() throws MessagingException {
		mailBuilder.to(email);
		Address[] to = mailBuilder.mailMessage.getAllRecipients();
		assertEquals(1, to.length);
		assertEquals("address", to[0].toString());
	}

	@Test
	public void testSubject() throws MessagingException {
		mailBuilder.subject("subject");
		String subject = mailBuilder.mailMessage.getSubject();
		assertEquals("subject", subject);
	}

	@Test
	public void testMessageBody() throws MessagingException, IOException {
		mailBuilder.messageBody("body");
		String body = (String) mailBuilder.mailMessage.getContent();
		assertEquals("body", body);
	}

	@Test
	public void testSend() {
		mailBuilder.to(email, nickname);
		mailBuilder.messageBody("body");
		mailBuilder.send();
		List<MailMessage> sentMessages = LocalMailServiceTestConfig.getLocalMailService().getSentMessages();
		assertEquals(1, sentMessages.size());
		assertEquals("body", sentMessages.get(0).getTextBody());
	}

}
