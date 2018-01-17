package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

public class HumanP implements GeneralPlayer{
    private char sign_;
    private int disksNum_;
    private  String color_;
    private String theme_;
    public HumanP(String playerColor, String theme) {
        this.color_ = playerColor;
        sign_ = playerColor.charAt(0);
        disksNum_ = 2;
        theme_ = theme;
    }

    public void scoreUp(int num) {
        disksNum_ = disksNum_ + num;
    }

    public char getSign() {
        return sign_;
    }

    public int getScore() {
        return disksNum_;
    }

    public void scoreDown(int num) {
        disksNum_ = disksNum_ - num;
    }

    public Vector<Cell> getMovesForPlayer(Board gameBoard, char sign) {
        Vector<Cell> movesForCurrentPlayer = new Vector<>();
        //finding out all locations of the current player on the board
        Vector<Point> locations = getLocationsOfPlayerOnBoard(sign, gameBoard);
        Vector<Point> movesPoints = new Vector<>();
        Vector<Cell> movesNoDuplicates = new Vector<>();
        boolean add = true;
        //for each location of the current player -
        for (int i = 0; i < locations.size(); i++) {
            //look for optional moves.
            Vector<Cell> possibleMoves = possibleMovesForOneDisk(sign, locations.get(i), gameBoard);
            //add for the general list of the player
            for (int move = 0; move < possibleMoves.size(); move++) {
                add = true;
                Point p = new Point();
                p.x = possibleMoves.get(move).x;
                p.y = possibleMoves.get(move).y;
                for (int k = 0; k < movesPoints.size(); k++) {
                    if ((movesPoints.get(k).x == p.x) && (movesPoints.get(k).y == p.y)) {
                        add = false;
                    }
                }
                if (add == true) {
                    movesPoints.add(p);
                }
                movesForCurrentPlayer.add(possibleMoves.get(move));
            }
        }
        if (!movesForCurrentPlayer.isEmpty()) {
            movesNoDuplicates.add(movesForCurrentPlayer.get(0));
        }
        for (int i = 1; i < movesForCurrentPlayer.size();  i++) {
            int flag = 0;
            Cell c = movesForCurrentPlayer.get(i);
            int x = c.x;
            int y = c.y;
            for (int j = 0; j < movesNoDuplicates.size(); j++) {
                if (movesNoDuplicates.get(j).x == x && movesNoDuplicates.get(j).y == y) {
                    for (int n = 0; n < c.flip.size(); n++) {
                        movesNoDuplicates.get(j).flip.add(c.flip.get(n));
                    }
                    flag = 1;
                }
            }
            if (flag == 0) {
                movesNoDuplicates.add(c);
            }
        }
        /*for (int point = 0; point < movesPoints.size(); point++) {
            int pointX = movesPoints.get(point).x;
            int pointY = movesPoints.get(point).y;
            Vector<Point> sharedPoints = new Vector<>();
            for (int i = 0; i < movesForCurrentPlayer.size(); i++) {
                if ((movesForCurrentPlayer.get(i).x == pointX) && (movesForCurrentPlayer.get(i).y == pointY)) {
                    sharedPoints.add(sharedPoints.end(),
                            movesForCurrentPlayer.get(i).flip.begin(),
                            movesForCurrentPlayer.get(i).flip.end() );
                }

            Cell c = new Cell();
            c.x = pointX;
            c.y = pointY;
            c.flip = sharedPoints;
            if (!movesNoDuplicates.contains(c)) {
                movesNoDuplicates.add(c);
            }
        }*/
        // return movesForCurrentPlayer;
        return movesNoDuplicates;
    }

    @Override
    public String getPlayerColor() {
        return this.color_;
    }

    public Vector<Point> getLocationsOfPlayerOnBoard(char sign, Board gameBoard) {
        Vector<Point> locations = new Vector<>();
        //for each row and col in the board
        for (int i = 0; i < gameBoard.getWidth(); i++) {
            for (int j = 0; j < gameBoard.getHeight(); j++) {
                //if its the same sign as we're looking for, add to the vector
                if (gameBoard.getSign(i,j) == sign) {
                    Point p = new Point();
                    p.x = i;
                    p.y = j;
                    locations.add(p);
                }
            }
        }
        return locations;
    }

    public Vector<Cell> possibleMovesForOneDisk(char current, Point point, Board gameBoard) {
        Vector<Cell> possibleMoves = new Vector<>();
        Vector<Point> flippingPoints = new Vector<>();
        //first checking the upper row left to right,
        // mid row left and right, lower row left to right
        for (int vertical = -1; vertical < 2; vertical++) {
            for (int horizontal = -1; horizontal < 2; horizontal++) {
                //keeping the intial values
                int verBackUp = vertical;
                int horBackUp = horizontal;
                //if the disk next to me is in another color
                // keep going that direction until its not in another color
                while (gameBoard.isInBorders(point.x + vertical, point.y + horizontal) &&
                        (gameBoard.getSign(point.x + vertical, point.y + horizontal) != current) &&
                        (gameBoard.getSign(point.x + vertical, point.y + horizontal) != ' ')) {
                    //add this location as a flipping point for input point
                    Point flip = new Point();
                    flip.x = point.x + vertical;
                    flip.y = point.y + horizontal;
                    flippingPoints.add(flip);
                    vertical = vertical + verBackUp;
                    horizontal = horizontal + horBackUp;

                }
                //if its empty and i moved more than one step -
                // its an optional move for the player
                if (gameBoard.isInBorders(point.x + vertical, point.y + horizontal)) {
                    if ((gameBoard.getSign(point.x + vertical, point.y + horizontal) == ' ') &&
                            ((horBackUp != horizontal) || (verBackUp != vertical))) {
                        Cell possibleMove = new Cell();
                        possibleMove.x = point.x +vertical;
                        possibleMove.y = point.y + horizontal;
                        possibleMove.flip = flippingPoints;
                        // flippingPoints.clear();
                        possibleMoves.add(possibleMove);
                    }
                }

                flippingPoints = new Vector<>();
                //use the back ups to set them back to original,
                //so the changes wont harm the loop
                vertical = verBackUp;
                horizontal = horBackUp;
            }
        }
        return possibleMoves;
    }

    public String getNextMove(Board b) {
        String choice = new String();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            choice = br.readLine();
        } catch (Exception e) {

        }
        return choice;
    }

 /*   public void printMyOptions(Visualization screen, Vector<Cell> myoptions) {
        //let the human player know his optional moves
        screen.printOptions(this.getSign(), myoptions);
    }*/

    public void passTurn() {
        //"type anything" - gets an input and ignores it
        String key;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            key = br.readLine();
        } catch (Exception e) {

        }
    }

    public String getPlayerTheme() {
        return this.theme_;
    }
    /* public void noMovesForMe(Visualization screen) {
        //for normal human player - print he has no more moves
        screen.printNoMoreMoves(this.getSign());

    }*/

   /* public void printItsnAOption(Visualization screen) {
        screen.printError();
    }*/
}
