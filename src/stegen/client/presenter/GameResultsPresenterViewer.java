package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.playeraction.*;
import stegen.shared.*;

public class GameResultsPresenterViewer implements Presenter {
	private final Display view;
	private final EventBus eventBus;
	final UpdateGameResultListCallback eventUpdateGameResultListCallback = createUpdateGameResultListCallback();
	
	public interface Display {
		void changeGameResultList(List<GameResultsRow> content);
	}

	public GameResultsPresenterViewer(Display scoreView, EventBus eventBus) {
		this.view = scoreView;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		initEvents();
		loadGameResults();
	}

	private void initEvents() {
		eventBus.addHandler(eventUpdateGameResultListCallback);
	}

	private void loadGameResults() {
		eventBus.updateGameResultList();
	}

	private UpdateGameResultListCallback createUpdateGameResultListCallback() {
		return new UpdateGameResultListCallback() {

			@Override
			public void onSuccessImpl(List<PlayerCommandDto> gameResults) {
				List<GameResultsRow> content = new ArrayList<GameResultsRow>();
				for (PlayerCommandDto playerCommandDto : gameResults) {
					content.add(new GameResultsRow(playerCommandDto.player.nickname,
							playerCommandDto.performedDateTime, playerCommandDto.description));
				}
				view.changeGameResultList(content);
			}
		};
	}
}
