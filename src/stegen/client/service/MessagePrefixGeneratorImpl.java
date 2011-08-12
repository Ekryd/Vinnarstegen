package stegen.client.service;

import java.util.*;

public class MessagePrefixGeneratorImpl implements MessagePrefixGenerator {
	private final Random random = new Random();
	private final static MessagePrefix[] availablePrefix = new MessagePrefix[] {
			new MessagePrefix("Ropa", "ropar att"), new MessagePrefix("Viska", "viskar att"),
			new MessagePrefix("Väsa", "väser att"), new MessagePrefix("Berätta", "berättar att"),
			new MessagePrefix("Fråga", "frågar om"), new MessagePrefix("Tokvråla", "tokvrålar att"),
			new MessagePrefix("Lämna en lapp", "lämnar en lapp om"), new MessagePrefix("Gissa", "gissar att"),
			new MessagePrefix("Skriva", "skriver att"), new MessagePrefix("Meddela", "meddelar att"),
			new MessagePrefix("Skrika", "skriker att"), new MessagePrefix("Påtala", "påtalar att"),
			new MessagePrefix("Ge feedback", "ger feedback på"), new MessagePrefix("Peka", "pekar på"),
			new MessagePrefix("Gorma", "gormar att"), new MessagePrefix("Rappa", "rappar om"),
			new MessagePrefix("Skria", "skriar att"), new MessagePrefix("Skräna", "skränar att"),
			new MessagePrefix("Ryta", "ryter att"), new MessagePrefix("Hojta", "hojtar att"),
			new MessagePrefix("Säga", "säger att"), new MessagePrefix("Tala", "talar om"),
			new MessagePrefix("Yttra", "yttrar"), new MessagePrefix("Framhålla", "framhåller att"),
			new MessagePrefix("Erbjuda", "erbjuder"), new MessagePrefix("Utmana", "utmanar"),
			new MessagePrefix("Håna", "hånar") };

	@Override
	public MessagePrefix getRandomizedPrefix() {
		return availablePrefix[random.nextInt(availablePrefix.length)];
	}
}
