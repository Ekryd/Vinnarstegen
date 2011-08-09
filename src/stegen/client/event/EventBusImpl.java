package stegen.client.event;

import java.util.*;

import stegen.client.messages.*;
import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.user.client.rpc.*;

public class EventBusImpl implements EventBus {

	private final PlayerCommandServiceAsync playerCommandService;
	private final ScoreServiceAsync scoreService;
	private final PlayerServiceAsync playerService;
	private final HashMap<Event, AsyncCallback<?>> eventHandlers = new HashMap<Event, AsyncCallback<?>>();

	public EventBusImpl(PlayerCommandServiceAsync playerCommandService, ScoreServiceAsync scoreService,
			PlayerServiceAsync playerService) {
		this.playerCommandService = playerCommandService;
		this.scoreService = scoreService;
		this.playerService = playerService;
	}

	@Override
	public void addHandler(Event event, DefaultCallback<?> defaultCallback) {
		eventHandlers.put(event, defaultCallback);
	}

	@Override
	public void checkUserLoginStatus(String hostPageBaseURL) {
		@SuppressWarnings("unchecked")
		AsyncCallback<LoginDataDto> callback = (AsyncCallback<LoginDataDto>) eventHandlers
				.get(Event.CHECK_USER_LOGIN_STATUS);
		playerService.userLoginStatus(hostPageBaseURL, callback);
	}

	@Override
	public void changeNickname(final PlayerDto player, final String nickname) {
		@SuppressWarnings("unchecked")
		AsyncCallback<Void> callback = (AsyncCallback<Void>) eventHandlers.get(Event.CHANGE_NICKNAME);
		playerService.changeNickname(player, nickname, callback);
	}

	@Override
	public void registerPlayer(EmailAddressDto email) {
		@SuppressWarnings("unchecked")
		AsyncCallback<Void> callback = (AsyncCallback<Void>) eventHandlers.get(Event.REGISTER_PLAYER);
		playerService.registerPlayer(email, callback);
	}
}
