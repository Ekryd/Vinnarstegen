package stegen.client.messages;

import java.util.*;

import stegen.client.dto.*;

public interface MessageListener {
	void onMessageListUpdate(List<PlayerCommandDto> messages);

}
