package stegen.client.presenter;

import static org.easymock.EasyMock.*;

import org.junit.*;

import stegen.client.event.*;
import stegen.client.gui.score.*;
import stegen.client.presenter.ChallengePresenter.Display;
import stegen.client.service.*;
import stegen.shared.*;

public class ChallengePresenterTest {

	private Display view;
	private LoginDataDto loginData;
	private EventBus eventBus;
	private ChallengePresenter presenter;
	private InsultFactory insultFactory;
	private DateTimeFormats dateTimeFormats;

	@Test
	public void testShowView() {
		setupPresenter();

		setupInitializationExpects();

		presenter.go();

		verify(view, eventBus, insultFactory);
	}

	@Test
	public void testOpenInputDialog() {
		setupPresenter();
		presenter.go();

		setupOpenDialogExpects();

		simulateOpenDialogClick();

		verify(view, eventBus, insultFactory);
	}

	@Test
	public void testSendOkMessage() {
		setupPresenter();
		presenter.go();
		setupOpenDialogExpects();

		simulateOpenDialogClick();

		setupSendOkMessageExpects();

		simulateSendMessage();

		verify(view, eventBus, insultFactory);
	}

	private void setupPresenter() {
		loginData = LoginDataDtoFactory.createLoginData();
		view = createStrictMock(Display.class);
		eventBus = createStrictMock(EventBus.class);
		insultFactory = createStrictMock(InsultFactory.class);
		dateTimeFormats = createStrictMock(DateTimeFormats.class);
		presenter = new ChallengePresenter(view, loginData, eventBus, insultFactory, dateTimeFormats);
	}

	private void setupInitializationExpects() {
		view.addClickOpenChallengeInputHandler(presenter.openChallengeInputhandler);
		view.addClickSendChallengeHandler(presenter.clickSendChallengeHandler);
		eventBus.addHandler(presenter.eventCommandChangeNicknameCallback);
		replay(view, eventBus, insultFactory);
	}

	private void setupOpenDialogExpects() {
		reset(view, eventBus, insultFactory);
		expect(insultFactory.createCompleteInsult()).andReturn("insult1");
		expect(insultFactory.createCompleteInsult()).andReturn("insult2");
		expect(dateTimeFormats.getChallengeDateDefaultOneDayFromNow()).andReturn("nextDay");
		String message = "Jag, nickname (address),  tycker att du, challengeeName, är insult1!\n"
				+ "Försvara din ära! Möt mig i pingis nextDay.\n"
				+ "Annars kommer hela världen att veta att du är insult2\n" + "\n" + "Med vänliga hälsningar\n"
				+ "nickname";
		view.setupChallengeInputDialog("challengeeName", "insult1", "Utmaning från Vinnarstegen", message);
		view.openChallengeInputDialog();
		replay(view, eventBus, insultFactory, dateTimeFormats);
	}

	private void simulateOpenDialogClick() {
		PlayerDto challengee = new PlayerDto(new EmailAddressDto("challengeeEmail"), "challengeeName");
		ScoreTableRow row = new ScoreTableRow(challengee, null, null, null, null, false);
		presenter.openChallengeInputhandler.update(0, row, null);
	}

	private void setupSendOkMessageExpects() {
		reset(view, eventBus, insultFactory);
		eventBus.challengePlayer(anyObject(ChallengeMessageDto.class));
		replay(view, eventBus, insultFactory);
	}

	private void simulateSendMessage() {
		presenter.clickSendChallengeHandler.onClick(null);
	}

}
