package stegen.server.mail;

import static org.junit.Assert.*;

import javax.mail.*;

import org.junit.*;

public class MailExceptionTest {

	@Test
	public void test() {
		assertNotNull(new MailException(new MessagingException()));
		assertNotNull(new MailException("Hepp", new MessagingException()));
	}

}
