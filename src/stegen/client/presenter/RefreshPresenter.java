package stegen.client.presenter;

import stegen.client.event.*;

import com.google.gwt.event.dom.client.*;

public class RefreshPresenter implements Presenter {
	private final Display view;
	private final EventBus eventBus;

	ClickHandler clickRefreshHandler = createClickRefreshHandler();

	public interface Display {
		void addClickRefreshHandler(ClickHandler clickHandler);
	}

	public RefreshPresenter(Display scoreView, EventBus eventBus) {
		this.view = scoreView;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		initView();
	}

	private void initView() {
		view.addClickRefreshHandler(clickRefreshHandler);
	}

	private ClickHandler createClickRefreshHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.refresh();
			}
		};
	}
}
