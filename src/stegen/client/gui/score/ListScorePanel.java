package stegen.client.gui.score;

import stegen.client.dto.*;
import stegen.client.messages.*;

import com.google.gwt.user.client.ui.*;

public class ListScorePanel extends VerticalPanel {

	private final MessageCentral messageCentral;
	private final LoginDataDto loginData;

	public ListScorePanel(MessageCentral messageCentral, LoginDataDto loginData) {
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		init();
	}

	private void init() {
		add(new ScoreCellTable(messageCentral, loginData));
		add(new CleanScoresButton(messageCentral, loginData));
	}

}
