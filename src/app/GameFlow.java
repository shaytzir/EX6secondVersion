package app;

import fxml.BoardController;
import fxml.GameController;

import java.util.Vector;

public class GameFlow {
    private ReversiRules rules;
    private GameController gameController;
    private BoardController boardController;

    /**
     * constructor
     * @param logic The logic of the game (ReversiRules)
     * @param gameController the game controller
     * @param boardController board controller
     */
    public GameFlow(ReversiRules logic, GameController gameController, BoardController boardController) {
        rules = logic;
        this.gameController = gameController;
        this.boardController = boardController;
    }

    /**
     * gets a desired cell and runs the next move in the game
     * @param x row of the cell
     * @param y col of the cell
     */
    public void run(int x, int y) {
        Vector<Cell> moves = rules.getMovesForCurrentPlayer();
        //if this player has possible moves
        if (moves.size() != 0) {
            //check if the clicked point is valid for this player
            if (isAValidMove(x, y)) {
                //update the board with this move
                rules.updateBoard(x, y);
                //change the current turn to the other player
                rules.switchPlayers();
                //draw the updated board
                this.boardController.draw();
                this.gameController.draw();
                //if after this move the game is over
                if (rules.gameOver()) {
                    //call the game controller to announce the game is over
                    gameController.gameOver(rules.whoWon());
                    return;
                }
                //if the next player (after switching has no moves)
                if (this.rules.getMovesForCurrentPlayer().size() == 0) {
                    //the game controller sends message there are no moves for him
                    this.gameController.noMovesForCurrentPlayer();
                    //switch players again
                    rules.switchPlayers();
                }

                boardController.draw();
            }
            //if there are no moves for the player
        } else {
            this.gameController.draw();
            this.boardController.draw();
            this.gameController.noMovesForCurrentPlayer();
        }
    }


    /**
     *
     * @return get the color of the current player
     */
    public String getCurrentPlayerColor() { return this.rules.getCurrentPlayer().getPlayerColor(); }

    /**
     *
     * @return the logic/rules of the game
     */
    public ReversiRules getLogic() { return this.rules; }

    /**
     * checks if the asked cell is a valid move
     * @param x row in board
     * @param y col in board
     * @return true if valid, false if not
     */
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
