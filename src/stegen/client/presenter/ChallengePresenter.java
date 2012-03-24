package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.gui.score.*;
import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.cell.client.*;
import com.google.gwt.event.dom.client.*;

public class ChallengePresenter implements Presenter {
	private final Display view;
	private final LoginDataDto loginData;
	private final DateTimeFormats dateTimeFormats;
	private String nickname;

	final AbstractAsyncCallback<Void> challangePlayerCallback = createChallangePlayerCallback();
	final FieldUpdater<ScoreTableRow, String> openChallengeInputhandler = createOpenChallengeInputhandler();
	final ClickHandler clickSendChallengeHandler = createClickSendChallengeHandler();
	final ChangeNicknameEventHandler changeNicknameEventHandler =  createChangeNicknameEventHandler();

	ChallengeMessage message;
	private final ScoreServiceAsync scoreService;
	private final com.google.gwt.event.shared.EventBus gwtEventBus;

	public interface Display {
		void addClickOpenChallengeInputHandler(FieldUpdater<ScoreTableRow, String> fieldUpdater);

		void addClickSendChallengeHandler(ClickHandler clickHandler);

		void setupChallengeInputDialog(String challengeeName, String challengeMessageSubject,
				String challengeMessage);

		void openChallengeInputDialog();

		String getUserModifiedMessage();
	}

	public ChallengePresenter(Display scoreView, LoginDataDto loginData, DateTimeFormats dateTimeFormats,ScoreServiceAsync scoreService,com.google.gwt.event.shared.EventBus gwtEventBus) {
		this.view = scoreView;
		this.loginData = loginData;
		this.dateTimeFormats = dateTimeFormats;
		this.scoreService = scoreService;
		this.gwtEventBus = gwtEventBus;
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
		gwtEventBus.addHandler(ChangeNicknameEvent.TYPE, changeNicknameEventHandler);
	}

	private FieldUpdater<ScoreTableRow, String> createOpenChallengeInputhandler() {
		return new FieldUpdater<ScoreTableRow, String>() {

			@Override
			public void update(int index, ScoreTableRow row, String value) {
				PlayerDto challenger = loginData.player;
				PlayerDto challengee = row.player;
				message = new ChallengeMessage(challenger.email, nickname, challengee,
						dateTimeFormats.getChallengeDateDefaultOneDayFromNow());
				view.setupChallengeInputDialog(row.player.nickname, message.getSubject(),
						message.getMessage());
				view.openChallengeInputDialog();
			}
		};
	}

	private ClickHandler createClickSendChallengeHandler() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String changedMessageBody = view.getUserModifiedMessage();
				message.setMessage(changedMessageBody);
				scoreService.challengePlayer(message.createDto(), challangePlayerCallback);
			}
		};
	}


	
	private ChangeNicknameEventHandler createChangeNicknameEventHandler() {
		return new ChangeNicknameEventHandler() {
			@Override
			public void handleEvent(ChangeNicknameEvent event) {
				nickname = event.getNewNickname();
			}
		};
	}
	
	
	private AbstractAsyncCallback<Void> createChallangePlayerCallback() {
		return new AbstractAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				gwtEventBus.fireEvent(new ChallengeEvent());
			}
		};
	}
}
