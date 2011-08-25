package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.gui.score.*;
import stegen.shared.*;

import com.google.gwt.cell.client.*;
import com.google.gwt.event.dom.client.*;

public class ChallengePresenter implements Presenter {
	private final Display view;
	private final LoginDataDto result;
	private final EventBus eventBus;
	private FieldUpdater<ScoreTableRow, String> openChallengeInputhandler = createOpenChallengeInputhandler();
	private ClickHandler clickSendChallengeHandler = createClickSendChallengeHandler();
	protected ChallengeMessage message;

	public interface Display {
		void addClickOpenChallengeInputHandler(FieldUpdater<ScoreTableRow, String> fieldUpdater);

		void addClickSendChallengeHandler(ClickHandler clickHandler);

		void setupChallengeInputDialog(String challengeeName, String shortInsultText, String challengeMessageSubject,
				String challengeMessage);

		void openChallengeInputDialog();
	}

	public ChallengePresenter(Display scoreView, LoginDataDto result, EventBus eventBus) {
		this.view = scoreView;
		this.result = result;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		initView();
	}

	private void initView() {
		view.addClickOpenChallengeInputHandler(openChallengeInputhandler);
		view.addClickSendChallengeHandler(clickSendChallengeHandler);
	}

	private FieldUpdater<ScoreTableRow, String> createOpenChallengeInputhandler() {
		return new FieldUpdater<ScoreTableRow, String>() {

			@Override
			public void update(int index, ScoreTableRow row, String value) {
				PlayerDto challenger = result.player;
				PlayerDto challengee = row.player;
				message = new ChallengeMessage(challenger, challengee);
				view.setupChallengeInputDialog(row.player.nickname, message.getInsult(), message.getSubject(),
						message.getMessage());
				view.openChallengeInputDialog();
			}
		};
	}

	private ClickHandler createClickSendChallengeHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.challengePlayer(message.createDto());
			}
		};
	}
}
