package stegen.client.messages;

import java.util.*;

import stegen.client.dto.*;
import stegen.shared.*;

public interface ScoreListener {
	void onScoreChange(List<PlayerScoreDto> result);

}
