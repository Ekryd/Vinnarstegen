package stegen.client.service;

import stegen.client.event.*;
import stegen.shared.*;

import com.google.gwt.user.client.rpc.*;

//TODO skriv test
public class RefreshService {
	private final PlayerCommandServiceAsync playerCommandService;

	private long latestCommandId;
	public RefreshService(PlayerCommandServiceAsync playerCommandService) {
		this.playerCommandService = playerCommandService;
	}

	public void refresh(final AsyncCallback<RefreshType> callback) {
		playerCommandService.getLatestCommandId(new AbstractAsyncCallback<Long>() {
			@Override
			public void onSuccess(final Long latestCommandIdFromServer) {
				executeCallbacks(callback, latestCommandIdFromServer);
			}
		});
	}

	protected void executeCallbacks(AsyncCallback<RefreshType> callback, Long latestCommandIdFromServer) {
		if (latestCommandId == latestCommandIdFromServer) {
			callback.onSuccess(RefreshType.NO_CHANGES_ON_SERVER_SIDE);
		} else {
			latestCommandId = latestCommandIdFromServer;
			callback.onSuccess(RefreshType.CHANGES_ON_SERVER_SIDE);
		}
	}

}
