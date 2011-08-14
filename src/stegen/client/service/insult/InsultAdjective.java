package stegen.client.service.insult;

import java.util.*;

class InsultAdjective {
	private Map<EnOrEtt, String> text = new HashMap<EnOrEtt, String>(2);

	public InsultAdjective(String enText, String ettText) {
		text.put(EnOrEtt.EN, enText);
		text.put(EnOrEtt.ETT, ettText);
	}

	public String getText(EnOrEtt enOrEtt) {
		return text.get(enOrEtt);
	}
}
