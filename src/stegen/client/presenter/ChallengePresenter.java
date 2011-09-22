package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.score.*;
import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.cell.client.*;
import com.google.gwt.event.dom.client.*;

public class ChallengePresenter implements Presenter {
	private final Display view;
	private final LoginDataDto loginData;
	private final EventBus eventBus;
	private final InsultFactory insultFactory;
	private final DateTimeFormats dateTimeFormats;
	private String nickname;

	final FieldUpdater<ScoreTableRow, String> openChallengeInputhandler = createOpenChallengeInputhandler();
	final ClickHandler clickSendChallengeHandler = createClickSendChallengeHandler();
	final CommandChangeNicknameCallback eventCommandChangeNicknameCallback = createCommandChangeNicknameCallback();

	ChallengeMessage message;

	public interface Display {
		void addClickOpenChallengeInputHandler(FieldUpdater<ScoreTableRow, String> fieldUpdater);

		void addClickSendChallengeHandler(ClickHandler clickHandler);

		void setupChallengeInputDialog(String challengeeName, String shortInsultText, String challengeMessageSubject,
				String challengeMessage);

		void openChallengeInputDialog();
	}

	public ChallengePresenter(Display scoreView, LoginDataDto loginData, EventBus eventBus,
			InsultFactory insultFactory, DateTimeFormats dateTimeFormats) {
		this.view = scoreView;
		this.loginData = loginData;
		this.eventBus = eventBus;
		this.insultFactory = insultFactory;
		this.dateTimeFormats = dateTimeFormats;
		this.nickname = loginData.player.nickname;
	}

	@Override
	public void go() {
		initView();
		initEvents();
	}

	private void initView() {
		view.addClickOpenChallengeInputHandler(openChallengeInputhandler);
		view.addClickSendChallengeHandler(clickSendChallengeHandler);
	}

	private void initEvents() {
		eventBus.addHandler(eventCommandChangeNicknameCallback);
	}

	private FieldUpdater<ScoreTableRow, String> createOpenChallengeInputhandler() {
		return new FieldUpdater<ScoreTableRow, String>() {

			@Override
			public void update(int index, ScoreTableRow row, String value) {
				PlayerDto challenger = loginData.player;
				PlayerDto challengee = row.player;
				message = new ChallengeMessage(challenger.email, nickname, challengee,
						insultFactory.createCompleteInsult(), insultFactory.createCompleteInsult(),
						dateTimeFormats.getChallengeDateDefaultOneDayFromNow());
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

	private CommandChangeNicknameCallback createCommandChangeNicknameCallback() {
		return new CommandChangeNicknameCallback() {

			@Override
			public void onSuccessImpl(String newNickname) {
				nickname = newNickname;
			}

		};
	}
}
