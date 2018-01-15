package myapp;

import java.lang.reflect.GenericArrayType;

public class GameFlow {
    private ReversiRules currentGame_;
    private GameController controller;

    private BoardController board;
    private GeneralPlayer player1;
    private GeneralPlayer player2;
   // private BoardController boardControl;



    public GameFlow(ReversiRules logic, GameController control){//, BoardController boardController) {
        currentGame_ = logic;
        this.controller = control;
    }
    public void run(int x, int y) {
            currentGame_.nextTurn(x,y);
            if (currentGame_.gameOver()) {
                controller.gameOver(currentGame_.whoWon());
            }
    }

    public String getCurrentPlayerColor() { return this.currentGame_.getCurrentPlayer().getPlayerColor(); }

    public ReversiRules getLogic() { return this.currentGame_; }
}
