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
	private LoginDataDto result;
	private EventBus eventBus;
	private ChallengePresenter presenter;
	private InsultFactory insultFactory;

	@Test
	public void testShowView() {
		setupPresenter();

		setupInitializationExpects();

		presenter.go();
	}

	@Test
	public void testOpenInputDialog() {
		setupPresenter();
		presenter.go();

		setupOpenDialogExpects();

		simulateOpenDialogClick();
	}

	@Test
	public void testSendOkMessage() {
		setupPresenter();
		presenter.go();
		setupOpenDialogExpects();

		simulateOpenDialogClick();

		setupSendOkMessageExpects();

		simulateSendMessage();
	}

	private void setupPresenter() {
		result = LoginDataDtoFactory.createLoginData();
		view = createStrictMock(Display.class);
		eventBus = createStrictMock(EventBus.class);
		insultFactory = createStrictMock(InsultFactory.class);
		presenter = new ChallengePresenter(view, result, eventBus, insultFactory);
	}

	private void setupInitializationExpects() {
		view.addClickOpenChallengeInputHandler(presenter.openChallengeInputhandler);
		view.addClickSendChallengeHandler(presenter.clickSendChallengeHandler);
		replay(view, eventBus, insultFactory);
	}

	private void setupOpenDialogExpects() {
		reset(view, eventBus, insultFactory);
		expect(insultFactory.createCompleteInsult()).andReturn("insult1");
		expect(insultFactory.createCompleteInsult()).andReturn("insult2");
		expect(insultFactory.getChallengeDateDefaultOneDayFromNow()).andReturn("nextDay");
		String message = "Jag, nickname (address),  tycker att du, challengeeName, är insult1!\n"
				+ "Försvara din ära! Möt mig i pingis nextDay.\n"
				+ "Annars kommer hela världen att veta att du är insult2\n" + "\n" + "Med vänliga hälsningar\n"
				+ "nickname";
		view.setupChallengeInputDialog("challengeeName", "insult1", "Utmaning från Vinnarstegen", message);
		view.openChallengeInputDialog();
		replay(view, eventBus, insultFactory);
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
