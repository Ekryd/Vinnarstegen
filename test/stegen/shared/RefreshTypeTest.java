package stegen.shared;

import static org.junit.Assert.*;

import org.junit.*;

public class RefreshTypeTest {

	@Test
	public void defaultEnumMenthodsTest() {
		RefreshType enumInstance = RefreshType.values()[0];
		assertEquals(enumInstance, RefreshType.valueOf(enumInstance.name()));
	}
}
