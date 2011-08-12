package stegen.client.messages;

import java.util.*;

import stegen.shared.*;

public interface ScoreListener {
	void onScoreChange(List<PlayerScoreDto> result);

}
