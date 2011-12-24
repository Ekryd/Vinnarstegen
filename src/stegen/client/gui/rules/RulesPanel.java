package stegen.client.gui.rules;

import com.google.gwt.user.client.ui.*;

public class RulesPanel implements IsWidget {

	private final HTML rules = new HTML();
	private final HorizontalPanel baseWidget = new HorizontalPanel();

	public RulesPanel() {
		initLayout();

	}

	private void initLayout() {
		rules.setHTML("Vi spelar matcher om bäst av fem set (först till tre).<br/>"
				+ "Varje set ger en poäng, alltså spelar vi om fem poäng i varje match.<br/>"
				+ "<br/>"
				+ "1. Om vinnaren i en match är den högre rankade (har högst poäng av de två) får denne 5 poäng minus det antal set som fårloraren vinner.<br/>"
				+ "Förloraren får 1 poäng för varje set den vinner. <br/>"
				+ "Poängen man får läggs till den innevarande poängen. <br/>"
				+ " <br/>"
				+ "2. Om vinnaren är den lägre rankade (har lägst poäng av de två) ersätts dennes innevarande poäng av den poängen som motståndaren hade <br/>"
				+ "innan matchstart plus 5 poäng minus det antal set som förloraren vinner. <br/>"
				+ "Förloraren behåller sin innevarande poäng och för 1 poäng för varje set den vinner. <br/>"
				+ " <br/>"
				+ "Alltså: <br/>"
				+ "En 3-0 match ger 5p till vinnaren och 0p till förloraren. <br/>"
				+ "En 3-1 match ger 4p till vinnaren och 1p till förloraren. <br/>"
				+ "En 3-2 match ger 3p till vinnaren och 2p till förloraren. <br/>"
				+ " <br/>"
				+ "Exempel 1: <br/>"
				+ "Anders har 41p, Peter har 28p <br/>"
				+ "Anders slår Peter med 3-2 <br/>"
				+ "Anders för 41 + (5-2) = 44p <br/>"
				+ "Peter får 28 + 2 = 30p <br/>"
				+ " <br/>"
				+ "Exempel 2: <br/>"
				+ "Kalle har 22p, Pelle har 32p <br/>"
				+ "Kalle slår Pelle med 3-1 <br/>"
				+ "Kalle får 32 + (5-1) = 36p <br/>"
				+ "Pelle får 32 + 1 = 33p <br/>"
				+ " <br/>"
				+ "Det lönar sig alltså att spela många matcher och att vinna stort. <br/>"
				+ "Till detta gäller att man endast får utmana någon som ligger 3 steg upp. <br/>"
				+ "Man får endast tacka nej till en utmaning 1 gång. Vid ett andra nekande räknas matchen förlorad med 3-0. <br/>");

		rules.setStylePrimaryName("rulesInsets");
		baseWidget.add(rules);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

}
