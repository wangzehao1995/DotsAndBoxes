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
        Player playerBeforeMove=sandbox.currentPlayer();
        sandbox.addMove(line);
        Player playerAfterMove=sandbox.currentPlayer();

        boolean notBadMove = playerAfterMove == playerBeforeMove;
        if(notBadMove){
            return 0;
        }

        return sandbox.maxBoxesCurrentPlayerCanGet();
    }

    int getGoodDegree(Line line) {
        VirtualGame sandbox=this.clone();
        Player playerBeforeMove=sandbox.currentPlayer();
        sandbox.addMove(line);
        Player playerAfterMove=sandbox.currentPlayer();

        boolean notGoodMove = playerAfterMove != playerBeforeMove;
        if(notGoodMove){
            return 0;
        }

        return sandbox.maxBoxesCurrentPlayerCanGet() + 1;
    }

    private int maxBoxesCurrentPlayerCanGet(){
        VirtualGame sandbox=this.clone();
        //use random ai to avoid recursive call
        RandomAIPlayer ai = new RandomAIPlayer(sandbox);
        Player playerBeforeMove= currentPlayer();
        int startCount = this.getOccupiedBoxCount();
        while (playerBeforeMove==sandbox.currentPlayer()&&!sandbox.isGameFinished()) {
            sandbox.addMove(ai.move());
        }
        int endCount = this.getOccupiedBoxCount();

        return (endCount - startCount);
    }

    protected VirtualGame clone(){
        return new VirtualGame(this);
    }
}
