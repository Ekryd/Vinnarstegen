package stegen.client.event;

import static org.easymock.EasyMock.*;

import org.easymock.*;

import stegen.client.service.*;

public class RefreshServiceStub {

	private final PlayerCommandServiceAsync playerCommandService;

	public RefreshServiceStub(PlayerCommandServiceAsync playerCommandService) {
		this.playerCommandService = playerCommandService;
	}

	@SuppressWarnings("unchecked")
	public void alwaysReturnServerChange() {
		playerCommandService.getLatestCommandId(anyObject(EventCallback.class));
		EasyMock.expectLastCall().andAnswer(new IAnswer<Object>() {

			@Override
			public Object answer() throws Throwable {
				EventCallback<Long> object = (EventCallback<Long>) getCurrentArguments()[0];
				object.onSuccess(System.currentTimeMillis());
				return null;
			}
		});
	}

}
