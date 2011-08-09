package stegen.client.gui.player;

import stegen.client.gui.dialog.*;

public class ChangeNicknameDialog2 extends InputDialogBox2 {

	public void setPlayerName(String name) {
		setLabelText("Ändra från " + name + " till");
	}

}
