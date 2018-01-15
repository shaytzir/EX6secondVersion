package myapp;


import java.io.*;

/**
 * Created by shaytzir on 14/01/2018.
 */
public class SettingsFields {
    private final String fileName = "settings.txt";
    private int size;
    //private char beginner;
    private String firstColor;
    private String secondColor;


    /**
     * BuildSettings constructor which sets the game with default settings.
     */
    public SettingsFields() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/settings.txt"));
            String line = reader.readLine();
            if (line == null) {
                throw new Exception("Empty File");
            }
            firstColor = line;
            line = reader.readLine();
            if (line == null) {
                throw new Exception("Settings aren't valid");
            }
            secondColor = line;
            line = reader.readLine();
            if (line == null) {
                throw new Exception("Settings aren't valid");
            }
            size = Integer.parseInt(line);
            reader.close();
        } catch (Exception e) {
            System.out.println("couldn't read Settings file.");
        }
    }

    /**
     *
     * @return the size of the of the desired board
     */
    public int getSize(){
        return this.size;
    }

    /**
     *
     * @return the color (in string) of the first player
     */
    public String getFirstColor() {
        return this.firstColor;
    }

    /**
     *
     * @return the color (in string) of the secp player
     */
    public String getSecondColor() {
        return this.secondColor;
    }
}
