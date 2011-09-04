package stegen.client.presenter;

import static org.easymock.EasyMock.*;

import org.junit.*;

import stegen.client.event.*;
import stegen.client.presenter.RefreshPresenter.Display;

public class RefreshPresenterTest {

	private RefreshPresenter presenter;
	private EventBus eventBus;
	private Display view;

	@Before
	public void before() {
		view = createStrictMock(Display.class);
		eventBus = createStrictMock(EventBus.class);
	}

	@After
	public void after() {
		verify(view, eventBus);
	}

	@Test
	public void testShowView() {
		setupPresenter();

		setupInitializationExpects();

		presenter.go();
	}

	@Test
	public void testRefreshCallback() {
		setupPresenter();

		eventBus.refresh();
		replay(view, eventBus);

		presenter.clickRefreshHandler.onClick(null);
	}

	private void setupPresenter() {
		presenter = new RefreshPresenter(view, eventBus);
	}

	private void setupInitializationExpects() {
		view.addClickRefreshHandler(presenter.clickRefreshHandler);
		replay(view, eventBus);
	}
}
