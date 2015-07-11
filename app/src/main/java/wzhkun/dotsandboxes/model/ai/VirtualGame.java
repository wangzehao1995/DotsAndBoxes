package wzhkun.dotsandboxes.model.ai;

import wzhkun.dotsandboxes.model.Game;
import wzhkun.dotsandboxes.model.Line;
import wzhkun.dotsandboxes.model.Player;

public class VirtualGame extends Game {

    VirtualGame(Game game){
        super(game);
    }

    protected int getOccupiedBoxCount(){
        int count = 0;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (getBoxOccupier(i, j) != null)
                    count++;
            }
        }
        return count;
    }

    int getBadDegree(Line line) {
        VirtualGame sandbox=this.clone();
        Player playerBeforeMove=sandbox.playerNow();
        sandbox.addMove(line);
        Player playerAfterMove=sandbox.playerNow();

        boolean notBadMove = playerAfterMove == playerBeforeMove;
        if(notBadMove){
            return 0;
        }

        //use random ai to avoid recursive call
        RandomAIPlayer ai = new RandomAIPlayer(sandbox);

        int startCount, endCount;
        startCount = this.getOccupiedBoxCount();

        while (playerAfterMove==sandbox.playerNow()&&!sandbox.isGameFinished()) {
            sandbox.addMove(ai.move());
        }
        endCount = this.getOccupiedBoxCount();

        return (endCount - startCount);
    }

    int getGoodDegree(Line line) {
        VirtualGame sandbox=this.clone();
        Player playerBeforeMove=sandbox.playerNow();
        sandbox.addMove(line);
        Player playerAfterMove=sandbox.playerNow();

        boolean notGoodMove = playerAfterMove != playerBeforeMove;
        if(notGoodMove){
            return 0;
        }

        //use random ai to avoid recursive call
        RandomAIPlayer ai = new RandomAIPlayer(sandbox);

        int startCount, endCount;
        startCount = this.getOccupiedBoxCount();

        while (playerAfterMove==sandbox.playerNow()&&!sandbox.isGameFinished()) {
            sandbox.addMove(ai.move());
        }
        endCount = this.getOccupiedBoxCount();

        return (endCount - startCount + 1);
    }

    protected VirtualGame clone(){
        return new VirtualGame(this);
    }
}
