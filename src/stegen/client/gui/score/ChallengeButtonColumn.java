package stegen.client.gui.score;

public class ChallengeButtonColumn extends ButtonColumn {

	public ChallengeButtonColumn(String buttonText) {
		super(buttonText);
	}

	@Override
	protected boolean showButton(ScoreTableRow cell) {
		return cell.showChallengeButton;
	}
}
