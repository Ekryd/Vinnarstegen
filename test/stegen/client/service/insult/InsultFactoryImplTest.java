package stegen.client.service.insult;

import static org.junit.Assert.*;

import org.junit.*;

public class InsultFactoryImplTest {
	private InsultFactoryImpl insultFactory = new InsultFactoryImpl();

	@Test
	public void testCreateCompleteInsult() {
		String insult = insultFactory.createCompleteInsult();
		assertNotNull(insult);
		assertEquals(true, insult.length() > 8);
	}

}
