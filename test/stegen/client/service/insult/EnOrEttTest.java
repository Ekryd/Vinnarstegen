package stegen.client.service.insult;

import static org.junit.Assert.*;

import org.junit.*;

public class EnOrEttTest {

	@Test
	public void testValuesMethod() {
		assertEquals(EnOrEtt.EN, EnOrEtt.valueOf(EnOrEtt.values()[0].name()));
	}

}
