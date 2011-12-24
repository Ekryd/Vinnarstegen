package stegen.client.presenter;

import static org.easymock.EasyMock.*;

import org.junit.*;

import stegen.client.event.*;
import stegen.client.presenter.ApplicationVersionPresenter.Display;

public class ApplicationVersionPresenterTest {

	private Display view;
	private EventBus eventBus;
	private ApplicationVersionPresenter presenter;

	@Test
	public void testShowView() {
		setupPresenter();

		setupInitializationExpects();

		presenter.go();

		verify(view, eventBus);
	}

	@Test
	public void testSetVersion() {
		setupPresenter();
		presenter.go();

		setupSetVersionExpects();

		presenter.eventUpdateApplicationVersion.onSuccess("42");

		verify(view, eventBus);
	}

	private void setupPresenter() {
		view = createStrictMock(Display.class);
		eventBus = createStrictMock(EventBus.class);
		presenter = new ApplicationVersionPresenter(view, eventBus);
	}

	private void setupInitializationExpects() {
		eventBus.addHandler(presenter.eventUpdateApplicationVersion);
		eventBus.getApplicationVersion();
		replay(view, eventBus);
	}

	private void setupSetVersionExpects() {
		reset(view, eventBus);
		view.setApplicationVersion("v. 42");
		replay(view, eventBus);
	}

}
