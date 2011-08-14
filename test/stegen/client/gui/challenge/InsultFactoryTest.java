package stegen.client.gui.challenge;

import org.junit.*;

import stegen.client.service.*;
import stegen.client.service.insult.*;

public class InsultFactoryTest {

	@Test
	public void test() {
		InsultFactory insultFactory = new InsultFactoryImpl();
		for (int i = 0; i < 10; i++) {
			System.out.println(insultFactory.createCompleteInsult());
		}
	}

}
