package app;

import fxml.BoardController;
import fxml.GameController;

import java.util.Vector;

public class GameFlow {
    private ReversiRules rules;
    private GameController gameController;

    private BoardController board;
    private GeneralPlayer player1;
    private GeneralPlayer player2;
    private BoardController boardController;
   // private BoardController boardControl;



    public GameFlow(ReversiRules logic, GameController control, BoardController boardController) {
        rules = logic;
        this.gameController = control;
        this.boardController = boardController;
    }
    public void run(int x, int y) {
        Vector<Cell> moves = rules.getMovesForCurrentPlayer();
        //if this player has possible moves
        if (moves.size() != 0) {
            //check if the clicked point is valid for this player
            if (isAValidMove(x, y)) {
                rules.updateBoard(x, y);
                rules.switchPlayers();
                this.boardController.draw();
                if (rules.gameOver()) {
                    gameController.gameOver(rules.whoWon());
                    return;
                }
                if (this.rules.getMovesForCurrentPlayer().size() == 0) {
                    this.gameController.noMovesForCurrentPlayer();
                    rules.switchPlayers();
                }
                boardController.draw();
            }
        } else {
            this.boardController.draw();
            this.gameController.noMovesForCurrentPlayer();
        }

            /*.nextTurn(x,y);
            if (rules.gameOver()) {
                controller.gameOver(rules.whoWon());
            }*/
        }


    public String getCurrentPlayerColor() { return this.rules.getCurrentPlayer().getPlayerColor(); }

    public ReversiRules getLogic() { return this.rules; }

    public boolean isAValidMove(int x, int y) {
        boolean flag = false;
        Vector<Cell> moves = rules.getMovesForCurrentPlayer();
        for (int i = 0; i < moves.size(); i++) {
            int xMoveInput = moves.get(i).x;
            int yMoveInput = moves.get(i).y;
            if ((xMoveInput == x) && (yMoveInput == y)) {
                flag = true;
            }
        }
        return flag;
    }


}
