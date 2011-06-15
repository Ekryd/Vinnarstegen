package stegen.client.messages;

import java.util.*;

import stegen.client.dto.*;

public interface ScoreListener {
	void onScoreChange(List<PlayerScoreDto> result);

}
