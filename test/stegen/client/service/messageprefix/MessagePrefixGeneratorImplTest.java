package stegen.client.service.messageprefix;

import static org.junit.Assert.*;

import org.junit.*;

public class MessagePrefixGeneratorImplTest {

	@Test
	public void testGetRandomizedPrefix() {
		MessagePrefixGeneratorImpl generator = new MessagePrefixGeneratorImpl();
		MessagePrefix messagePrefix = generator.getRandomizedPrefix();
		assertNotNull(messagePrefix);
		assertNotNull(messagePrefix.actionText);
		assertNotNull(messagePrefix.buttonText);
	}

}
