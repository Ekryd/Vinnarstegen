package stegen.client.presenter;

import static org.easymock.EasyMock.*;

import org.junit.*;

import stegen.client.event.*;
import stegen.client.presenter.CompositeMainPresenter.Display;
import stegen.client.service.*;
import stegen.shared.*;

public class CompositeMainPresenterTest {

	private Display view;
	private LoginDataDto loginData;
	private EventBus eventBus;
	private CompositeMainPresenter presenter;
	private InsultFactory insultFactory;

	@Test
	public void testShowView() {
		setupPresenter();

		setupInitializationExpects();

		presenter.go();
	}

	private void setupPresenter() {
		loginData = LoginDataDtoFactory.createLoginData();
		view = createStrictMock(Display.class);
		eventBus = createNiceMock(EventBus.class);
		insultFactory = createStrictMock(InsultFactory.class);
		presenter = new CompositeMainPresenter(view, loginData, eventBus, insultFactory);
	}

	private void setupInitializationExpects() {
		expect(view.getScoreView()).andReturn(createStrictMock(stegen.client.presenter.ScorePresenter.Display.class));
		expect(view.getChallengeInputView()).andReturn(
				createStrictMock(stegen.client.presenter.ChallengePresenter.Display.class));
		expect(view.getWinGameInputView()).andReturn(
				createStrictMock(stegen.client.presenter.WinGameInputPresenter.Display.class));
		expect(view.getGameResultsView()).andReturn(
				createStrictMock(stegen.client.presenter.GameResultsPresenter.Display.class));
		expect(view.getUndoView()).andReturn(createStrictMock(stegen.client.presenter.UndoPresenter.Display.class));
		expect(view.getLoginStatusesView()).andReturn(
				createStrictMock(stegen.client.presenter.LoginStatusesPresenter.Display.class));
		expect(view.getPlayerMiscCommandView()).andReturn(
				createStrictMock(stegen.client.presenter.PlayerMiscCommandsPresenter.Display.class));
		replay(view, eventBus, insultFactory);
	}

}
