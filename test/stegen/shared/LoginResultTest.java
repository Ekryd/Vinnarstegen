package stegen.shared;

import static org.junit.Assert.*;

import org.junit.*;

public class LoginResultTest {

	@Test
	public void testEnum() {
		LoginResult enumInstance = LoginResult.values()[0];
		assertEquals(enumInstance, LoginResult.valueOf(enumInstance.name()));
	}

}
