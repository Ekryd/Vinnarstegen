package stegen.client.gui.score;

import stegen.client.dto.*;
import stegen.client.messages.*;
import stegen.shared.*;

import com.google.gwt.user.client.ui.*;

public class ListScorePanel extends Composite {

	private final MessageCentral messageCentral;
	private final LoginDataDto loginData;
	private final VerticalPanel panel = new VerticalPanel();

	public ListScorePanel(MessageCentral messageCentral, LoginDataDto loginData) {
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		initWidget(panel);
		initLayout();
	}

	private void initLayout() {
		panel.add(new ScoreCellTable(messageCentral, loginData));
		panel.add(new CleanScoresButton(messageCentral, loginData));
	}

}
