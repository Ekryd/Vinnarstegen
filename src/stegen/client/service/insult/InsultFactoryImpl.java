package stegen.client.service.insult;

import java.util.*;

import stegen.client.service.*;

import com.google.gwt.i18n.client.*;

public class InsultFactoryImpl implements InsultFactory {
	private final DateTimeFormat dateFormat = DateTimeFormat.getFormat("'kl 'HH:mm' den 'dd/MM");

	private final Random random = new Random();
	private final InsultAdjective[] adjectives = new InsultAdjective[] { new InsultAdjective("gammal", "gammlat"),
			new InsultAdjective("ynkling", "ynkligt"), new InsultAdjective("gammalmodig", "gammalmodigt"),
			new InsultAdjective("svag", "svagt"), new InsultAdjective("illaluktande", "illaluktande"),
			new InsultAdjective("svettig", "svettigt"), new InsultAdjective("mesig", "mesigt"),
			new InsultAdjective("feg", "fegt"), new InsultAdjective("patetisk", "patetiskt"),
			new InsultAdjective("skruttig", "skruttigt"), new InsultAdjective("rostig", "rostigt"),
			new InsultAdjective("trasig", "trasigt"), new InsultAdjective("tunn", "tunt"),
			new InsultAdjective("försvarslös", "försvarslöst"), new InsultAdjective("pyttig", "pyttigt"),
			new InsultAdjective("grodliknande", "grodliknande"), new InsultAdjective("långsam", "långsamt"),
			new InsultAdjective("seg", "segt"), new InsultAdjective("utrotningshotad", "utredningshotat"),
			new InsultAdjective("fumlig", "fumligt"), new InsultAdjective("överkörd", "överkört"),
			new InsultAdjective("vårtig", "vårtigt"), };
	private final InsultNoun[] nouns = new InsultNoun[] { new InsultNoun(EnOrEtt.EN, "cykelpump"),
			new InsultNoun(EnOrEtt.EN, "ost"), new InsultNoun(EnOrEtt.EN, "skottkärra"),
			new InsultNoun(EnOrEtt.EN, "trädgårdstomte"), new InsultNoun(EnOrEtt.EN, "ynkrygg"),
			new InsultNoun(EnOrEtt.EN, "bil"), new InsultNoun(EnOrEtt.EN, "asgam"),
			new InsultNoun(EnOrEtt.ETT, "groddjur"), new InsultNoun(EnOrEtt.EN, "byrålåda"),
			new InsultNoun(EnOrEtt.EN, "propp"), new InsultNoun(EnOrEtt.EN, "skrotnisse"),
			new InsultNoun(EnOrEtt.EN, "bäbis"), new InsultNoun(EnOrEtt.EN, "fjolla"),
			new InsultNoun(EnOrEtt.EN, "trasa"), new InsultNoun(EnOrEtt.ETT, "par glasögon"),
			new InsultNoun(EnOrEtt.EN, "sophög"), new InsultNoun(EnOrEtt.EN, "slaskhink"),
			new InsultNoun(EnOrEtt.EN, "diskbänk"), new InsultNoun(EnOrEtt.ETT, "fattighjon"),
			new InsultNoun(EnOrEtt.ETT, "knott"), new InsultNoun(EnOrEtt.EN, "mygga"),
			new InsultNoun(EnOrEtt.EN, "fluga"), new InsultNoun(EnOrEtt.EN, "sköldpadda"),
			new InsultNoun(EnOrEtt.EN, "blomkruka"), new InsultNoun(EnOrEtt.ETT, "dammkorn"),
			new InsultNoun(EnOrEtt.ETT, "lingon"), new InsultNoun(EnOrEtt.ETT, "blåbär"), };

	@Override
	public String createCompleteInsult() {
		StringBuilder returnValue = new StringBuilder();
		InsultNoun noun = nouns[random.nextInt(nouns.length)];
		InsultAdjective adjective = adjectives[random.nextInt(adjectives.length)];
		returnValue.append(noun.enOrEtt.getText()).append(" ");
		returnValue.append(adjective.getText(noun.enOrEtt)).append(" ");
		returnValue.append(noun.nounText);
		return returnValue.toString();
	}

	@Override
	public String getChallengeDateDefaultOneDayFromNow() {
		Date date = new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24));
		return dateFormat.format(date);
	}

}
