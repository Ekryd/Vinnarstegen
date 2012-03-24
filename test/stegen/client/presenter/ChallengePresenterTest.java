package stegen.client.presenter;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.event.*;
import stegen.client.gui.score.*;
import stegen.client.presenter.ChallengePresenter.Display;
import stegen.client.service.*;
import stegen.shared.*;

@RunWith(MockitoJUnitRunner.class)
public class ChallengePresenterTest {

	@Mock
	private Display view;
	private LoginDataDto loginData = LoginDataDtoFactory.createLoginData();
	private ChallengePresenter presenter;
	@Mock
	private DateTimeFormats dateTimeFormats;
	@Mock
	private com.google.gwt.event.shared.EventBus gwtEventBus;
	@Mock
	private ScoreServiceAsync scoreService;
	@Test
	public void testShowView() {
		setupPresenter();

		presenter.go();
		
		setupInitializationExpects();
	}

	@Test
	public void testOpenInputDialog() {
		setupPresenter();
		presenter.go();

		presenter.changeNicknameEventHandler.handleEvent(new ChangeNicknameEvent("nick2"));

		when(dateTimeFormats.getChallengeDateDefaultOneDayFromNow()).thenReturn("nextDay");
		
		simulateOpenDialogClick();
		
		setupOpenDialogExpects();
	}

	@Test
	public void testSendOkMessage() {
		setupPresenter();
		presenter.go();
		

		presenter.changeNicknameEventHandler.handleEvent(new ChangeNicknameEvent("nick2"));

		when(dateTimeFormats.getChallengeDateDefaultOneDayFromNow()).thenReturn("nextDay");
		
		simulateOpenDialogClick();
		
		setupOpenDialogExpects();

		simulateSendMessage();

		setupSendOkMessageExpects();
	}

	private void setupPresenter() {
		presenter = new ChallengePresenter(view, loginData,  dateTimeFormats,scoreService,gwtEventBus);
	}

	private void setupInitializationExpects() {
		verify(view).addClickOpenChallengeInputHandler(presenter.openChallengeInputhandler);
		verify(view).addClickSendChallengeHandler(presenter.clickSendChallengeHandler);
		verify(gwtEventBus).addHandler(ChangeNicknameEvent.TYPE, presenter.changeNicknameEventHandler);
	}

	private void setupOpenDialogExpects() {
		
		String message = "Jag, nick2 (address),  vill utmana dig, challengeeName!\n"
				+ "Försvara din ära! Möt mig i pingis nextDay.\n\n"
				+ "Med vänliga hälsningar\n"
				+ "nick2";
		verify(view).setupChallengeInputDialog("challengeeName", "Utmaning från Vinnarstegen", message);
		verify(view).openChallengeInputDialog();
		
	}

	private void simulateOpenDialogClick() {
		PlayerDto challengee = new PlayerDto(new EmailAddressDto("challengeeEmail"), "challengeeName");
		ScoreTableRow row = new ScoreTableRow(challengee, null, null, null, null, false, true);
		presenter.openChallengeInputhandler.update(0, row, null);
	}

	private void setupSendOkMessageExpects() {
		when(view.getUserModifiedMessage()).thenReturn("Modified message");
		verify(scoreService).challengePlayer(any(ChallengeMessageDto.class), eq(presenter.challangePlayerCallback));
	}

	private void simulateSendMessage() {
		presenter.clickSendChallengeHandler.onClick(null);
	}

}
