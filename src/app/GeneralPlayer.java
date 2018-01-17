package app;

import java.util.Vector;

public interface GeneralPlayer {
    /**
     *
     * @param num number of disks to add to the player's score
     */
    void scoreUp(int num);

    /**
     *
     * @param num number to reduce from this player score
     */
    void scoreDown(int num);

    /**
     *
     * @return the sign of the player
     */
    char getSign();

    /**
     *
     * @return the score of the player
     */
    int getScore();

    /**
     * getMovesForPlayer.
     * @param gameBoard the board to check on.
     * @param sign the player sign.
     * @return the optional moves.
     */
    Vector<Cell> getMovesForPlayer(Board gameBoard, char sign);

    /**
     *
     * @return the color of the player
     */
    String getPlayerColor();

    /**
     *
     * @return the theme of the player
     */
    String getPlayerTheme();
}
