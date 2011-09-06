package stegen.shared;

import static org.junit.Assert.*;

import org.junit.*;

public class UndoPlayerCommandResultTest {

	@Test
	public void test() {
		UndoPlayerCommandResult enumInstance = UndoPlayerCommandResult.values()[0];
		assertEquals(enumInstance, UndoPlayerCommandResult.valueOf(enumInstance.name()));
	}

}
