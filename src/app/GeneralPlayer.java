package app;

import java.util.Vector;

public interface GeneralPlayer {
    /**
     *ScoreUp.
     * @param num number of disks to add to the player's score
     */
    public void scoreUp(int num);
    /**
     * scoreDown.
     * @param num a number to reduce from this player score
     */
    public void scoreDown(int num);
    /**
     * getSign.
     * @return the sign of this player
     */
    public char getSign();
    /**
     * getScore.
     * @return the score of this player
     */
    public int getScore();
    /**
     * getNextMove.
     * @param b the board to check the next move in.
     **/
    public String getNextMove(Board b);
    /**
     * passes the turn to the other player.
     */
    public void passTurn();

    /**
     * print there no moves for current player;
     */
   // public void noMovesForMe(Visualization screen);
    /**
     * getMovesForPlayer.
     * @param gameBoard the board to check on.
     * @param sign the player sign.
     * @return the optional moves.
     */
    public Vector<Cell> getMovesForPlayer(Board gameBoard, char sign);
    /**
     * prints the player options to the screen
     * @param screen screen
     * @param myoptions list of optional moves
     */
   // public void printMyOptions(Visualization screen, Vector<Cell> myoptions);

    /**
     * printItsnAOption.
     * @param screen print on sceen that the input is not valid.
     */
   // public void printItsnAOption(Visualization screen);

    /**
     * get the string (color) of the player
     */
    public String getPlayerColor();

}
