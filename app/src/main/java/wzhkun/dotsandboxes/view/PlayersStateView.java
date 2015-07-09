package wzhkun.dotsandboxes.view;

import java.util.Map;

import wzhkun.dotsandboxes.model.Player;

public interface PlayersStateView {
    void setPlayerNow(Player player);
    void setPlayerOccupyingBoxesCount(Map<Player,Integer> player_occupyingBoxesCount_map);
    void playerTouched();
    void setWinner(Player[] winner);
}
