package wzhkun.dotsandboxes.view;

import java.util.Map;

import wzhkun.dotsandboxes.model.Player;

/**
 * Created by wangzehao on 2015/7/6.
 */
public interface PlayersStateView {
    public void setPlayerNow(Player player);
    public void setPlayerOccupyingBoxesCount(Map<Player,Integer> player_occupyingBoxesCount_map);
    public void playerTouched();
    public void setWinner(Player[] winner);
}
