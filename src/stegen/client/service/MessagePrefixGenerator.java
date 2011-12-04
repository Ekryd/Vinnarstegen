package stegen.client.service;

import stegen.client.service.messageprefix.*;

public interface MessagePrefixGenerator {

	MessagePrefix getRandomizedPrefix();
	
	MessagePrefix getPrefix();

}
