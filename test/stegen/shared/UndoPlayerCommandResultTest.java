package stegen.shared;

import static org.junit.Assert.*;

import org.junit.*;

public class UndoPlayerCommandResultTest {

	@Test
	public void defaultEnumMenthodsTest() {
		UndoPlayerCommandResult enumInstance = UndoPlayerCommandResult.values()[0];
		assertEquals(enumInstance, UndoPlayerCommandResult.valueOf(enumInstance.name()));
	}

}
