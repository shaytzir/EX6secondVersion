package app;

import fxml.BoardController;

import java.util.Vector;

public class ReversiRules {
    private GeneralPlayer now_;
    private GeneralPlayer later_;
    private BoardController gui;
    private GeneralPlayer blackP_;
    private GeneralPlayer whiteP_;
    private Vector<Cell> movesForCurrentPlayer;

    /**
     * constructor.
     * @param guiB the Board Controller
     * @param black first player
     * @param white second player
     */
    public ReversiRules(BoardController guiB, GeneralPlayer black, GeneralPlayer white){
        this.gui = guiB;
        this.whiteP_ = white;
        this.blackP_ = black;
        now_ = blackP_;
        later_ = whiteP_;
        movesForCurrentPlayer = now_.getMovesForPlayer(this.gui.getBoard(), now_.getSign());
    }

    /**
     * checkes if the game is over.
     * @return true if its over, else false
     */
    public boolean gameOver() {
        // if the board is full the game is over
        if (this.gui.getBoard().fullBoard()) {
            return true;
        }
        //if the board isnt full but both players has no moves, the game is over
        if (now_.getMovesForPlayer(this.gui.getBoard(), this.now_.getSign()).size() == 0) {
            if (later_.getMovesForPlayer(this.gui.getBoard(), this.later_.getSign()).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * set the disk on the board
     * @param row row of the disk
     * @param col col of the disk
     */
    public void setPlayerDisk(int row, int col) {
        //if we set it on the other player existing disk, we need to
        //reduce the other player score in 1
        if (this.gui.getBoard().getSign(row,col)== later_.getSign()) {
            later_.scoreDown(1);
        }
        //if the desired place wasnt of the current player - add 1 to his score
        if (this.gui.getBoard().getSign(row, col) != now_.getSign()) {
            now_.scoreUp(1);
        }
        //set the board to have this player disk in the desired position
        this.gui.getBoard().setSign(row, col, now_.getSign());
    }

    /**
     * flipping all disks that supposed to be flipped by rules.
     * @param row the disk row
     * @param col the disk col
     */
    public void flipFrom(int row, int col) {
        for (int i = 0; i < movesForCurrentPlayer.size(); i++) {
            if ((movesForCurrentPlayer.get(i).x == row) && (movesForCurrentPlayer.get(i).y == col)) {
                for (int j = 0; j < movesForCurrentPlayer.get(i).flip.size(); j++) {
                    setPlayerDisk(movesForCurrentPlayer.get(i).flip.get(j).x,
                            movesForCurrentPlayer.get(i).flip.get(j).y);
                }
            }
            //reset the flipping points of the disk
            movesForCurrentPlayer.get(i).flip.clear();
        }
    }

    /**
     * after the game is over, choosing a winner by the scores.
     * @return the winner's color or "tie" if its a tie.
     */
    public String whoWon() {
        int scoreBlackP = blackP_.getScore();
        int scoreWhiteP = whiteP_.getScore();
        String winner;
        if (scoreBlackP > scoreWhiteP) {
            winner = blackP_.getPlayerColor();
        } else if (scoreWhiteP > scoreBlackP) {
            winner = whiteP_.getPlayerColor();
        } else {
            winner = "Tie";
        }
        return winner;
    }

    /**
     * switching between the players.
     */
    public void switchPlayers() {

        this.movesForCurrentPlayer.clear();
        GeneralPlayer temp = now_;
        this.now_ = later_;
        later_ = temp;

        // updating the current player moves
        this.movesForCurrentPlayer = now_.getMovesForPlayer(this.gui.getBoard(), now_.getSign());
    }

    /**
     *getter.
     * @return the current player
     */
    public GeneralPlayer getCurrentPlayer() {
        return this.now_;
    }


    /**
     *getter.
     * @return the current player's moves.
     */
    public Vector<Cell> getMovesForCurrentPlayer() { return this.movesForCurrentPlayer;}

    /**
     * updating the board means setting the new disk, and flipping the right disks.
     * @param x disk row
     * @param y disk col
     */
    public void updateBoard(int x, int y) {
        setPlayerDisk(x, y);
        flipFrom(x, y);
    }
}
