package myapp;

import java.util.Vector;

public class ReversiRules {
    private GeneralPlayer now_;
    private GeneralPlayer later_;
    private BoardController gui;
    private Board board_;
    private GeneralPlayer blackP_;
    private GeneralPlayer whiteP_;
   // private BoardController boardControl_;
    private Vector<Cell> movesForCurrentPlayer;

    public ReversiRules(BoardController guiB, GeneralPlayer black, GeneralPlayer white){//, BoardController boardController) {
        this.gui = guiB;
        this.board_ = this.gui.getBoard();
        this.whiteP_ = white;
        this.blackP_ = black;
        now_ = blackP_;
        later_ = whiteP_;
        //this.boardControl_ = boardController;
        movesForCurrentPlayer = now_.getMovesForPlayer(this.gui.getBoard(), now_.getSign());
    }

    public void nextTurn(int x, int y) {
        int row = 0, col = 0;
        String choice, key;
        char choiceToSend;
        //if the current player has no optional moves
        // he presses any key and the turn goes for the other player
        if (this.movesForCurrentPlayer.size() == 0) {

            System.out.println("no more moves!!!");

            //notify player he has no moves
            //this.now_.noMovesForMe(this.screen_);////////////////////////////////////////////
            this.now_.passTurn();
            //switch between players and updated movesforcurrentplayer
            this.movesForCurrentPlayer.clear();
            switchPlayers();
            this.movesForCurrentPlayer = now_.getMovesForPlayer(this.gui.getBoard(), now_.getSign());
            return;
            //if he has moves, let him choose one of them
        } else {
            boolean flag = false;
            for (int i = 0; i < movesForCurrentPlayer.size(); i++) {
                int xMoveInput = movesForCurrentPlayer.get(i).x;
                int yMoveInput = movesForCurrentPlayer.get(i).y;
                if ((xMoveInput == x) && (yMoveInput == y)) {
                    flag = true;
                }
            }
            if (!flag) {
                return;
            } else {
                System.out.println("valid move " + x + y);
                this.setPlayerDisk(x,y);
                this.flipFrom(x,y);
                this.movesForCurrentPlayer.clear();
                //switch between players
                switchPlayers();
                this.movesForCurrentPlayer = now_.getMovesForPlayer(this.gui.getBoard(), now_.getSign());
            }


        }
    }
    public void doAfterClick(int row, int col) {
        //set his choice to have his sign
        setPlayerDisk(row, col);
        //flip any disks standing in the way according to rules
        flipFrom(row, col);
        this.movesForCurrentPlayer.clear();
        //switch between players
        switchPlayers();
        this.movesForCurrentPlayer = now_.getMovesForPlayer(this.gui.getBoard(), now_.getSign());
    }
    public boolean thisIsAOption(String choice) {
        for(int i = 0; i < this.movesForCurrentPlayer.size(); i++) {
            if(choice.charAt(0) -'0' - 1 == this.movesForCurrentPlayer.get(i).x
                    && choice.charAt(2) -'0' - 1 == this.movesForCurrentPlayer.get(i).y) {
                return true;
            }
        }
        return false;
    }

    public boolean gameOver() {
        GeneralPlayer temp = now_;
        if (this.gui.getBoard().fullBoard()) {
            return true;
        }
        if (now_.getMovesForPlayer(this.gui.getBoard(), this.now_.getSign()).size() == 0) {
            if (later_.getMovesForPlayer(this.gui.getBoard(), this.later_.getSign()).size() == 0) {
                return true;
            }
        }
        return false;
    }

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

    public void flipFrom(int row, int col) {
        for (int i = 0; i < movesForCurrentPlayer.size(); i++) {
            if ((movesForCurrentPlayer.get(i).x == row) && (movesForCurrentPlayer.get(i).y == col)) {
                for (int j = 0; j < movesForCurrentPlayer.get(i).flip.size(); j++) {
                    setPlayerDisk(movesForCurrentPlayer.get(i).flip.get(j).x, movesForCurrentPlayer.get(i).flip.get(j).y);
                }
            }
            movesForCurrentPlayer.get(i).flip.clear();
        }
    }

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

    public void switchPlayers() {
        GeneralPlayer temp = now_;
        this.now_ = later_;
        later_ = temp;
    }
    public GeneralPlayer getCurrentPlayer() {
        return this.now_;
    }

    public Board getBoard() {
        return this.gui.getBoard();
    }

    public Vector<Cell> getMovesForCurrentPlayer() { return this.movesForCurrentPlayer;}

}
