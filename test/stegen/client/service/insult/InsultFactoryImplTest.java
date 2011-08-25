package stegen.client.service.insult;

import org.junit.*;

import stegen.client.service.*;

public class InsultFactoryImplTest {

	@Test
	public void test() {
		InsultFactory insultFactory = new InsultFactoryImpl();
		for (int i = 0; i < 10; i++) {
			System.out.println(insultFactory.createCompleteInsult());
		}
	}

}
