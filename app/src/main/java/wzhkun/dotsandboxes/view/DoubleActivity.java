package wzhkun.dotsandboxes.view;

import wzhkun.dotsandboxes.model.HumanPlayer;
import wzhkun.dotsandboxes.model.Player;

public class DoubleActivity extends TwoPlayerActivity {

    @Override
    protected Player[] initPlayers() {
        return new Player[]{new HumanPlayer("Player1"), new HumanPlayer("Player2")};
    }
}
