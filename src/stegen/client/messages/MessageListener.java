package stegen.client.messages;

import java.util.*;

import stegen.client.dto.*;
import stegen.shared.*;

public interface MessageListener {
	void onMessageListUpdate(List<PlayerCommandDto> messages);

}
