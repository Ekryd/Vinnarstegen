package stegen.client.gui.challenge;

import org.junit.*;

public class InsultFactoryTest {

	@Test
	public void test() {
		InsultFactory insultFactory = new InsultFactory();
		for (int i = 0; i < 10; i++) {
			System.out.println(insultFactory.createCompleteInsult());
		}
	}

}
