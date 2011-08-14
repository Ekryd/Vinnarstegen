package stegen.client.service.insult;

public enum EnOrEtt {
	EN("en"),
	ETT("ett");

	private final String text;

	private EnOrEtt(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
