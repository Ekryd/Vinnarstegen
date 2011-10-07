package stegen.server.command;

import java.io.*;

/**
 * @author Björn Ekryd
 */
public interface PlayerCommand extends Serializable {
	void execute();

	void undo();

	String getDescription();

	boolean isUndoable();
}
