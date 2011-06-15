package stegen.server.command;

import java.io.*;

public interface PlayerCommand extends Serializable {
	void execute();

	void undo();

	String getDescription();

	boolean isUndoable();
}
