/**
 * Developer:       Kelvin Chan
 * Language:        Java 1.8.0_181
 * Version:         1.0.0
 * Last revised:    30/09/2018
 */

package java_solution;
import java_solution.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Maze {
    private String mazefile;
    private int width;
    private int height;
    private int start_x;
    private int start_y;
    private int end_x;
    private int end_y;
    private int[][] storeMaze;

    public Maze(String filename){
        this.mazefile = filename;
        readfile(this.mazefile);
    }

    public int getStart_x(){
        return this.start_x;
    }

    public int getStart_y(){
        return this.start_y;
    }

    public int getEnd_x(){
        return this.end_x;
    }

    public int getEnd_y(){
        return this.end_y;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public int[][] getStoreMaze(){
        return this.storeMaze;
    }

    public void changeStoreMaze(int i, int j, char letter){
        this.storeMaze[i][j] = letter;
    }

    private void readfile(String mazefile) {

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(mazefile);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            String[] storeProperties = new String[3];

            // Read up to, but not including, the 4th line
            for (int i=0; i<3; i++){
                storeProperties[i] = bufferedReader.readLine();
            }

            // Store properties of maze from first 3 lines.
            this.width = Integer.valueOf(storeProperties[0].split(" ")[0]);
            this.height = Integer.valueOf(storeProperties[0].split(" ")[1]);
            this.start_x = Integer.valueOf(storeProperties[1].split(" ")[0]);
            this.start_y = Integer.valueOf(storeProperties[1].split(" ")[1]);
            this.end_x = Integer.valueOf(storeProperties[2].split(" ")[0]);
            this.end_y = Integer.valueOf(storeProperties[2].split(" ")[1]);

            // Store the maze map
            this.storeMaze = new int[this.height][this.width];
            String[] line;
            for (int i=0; i<this.height; i++){
                line = bufferedReader.readLine().split(" ");
                for (int j=0; j<this.width; j++) {
                    this.storeMaze[i][j] = Integer.valueOf(line[j]);
                }
            }

            // Close file because of EOL
            bufferedReader.close();

        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            mazefile + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + mazefile + "'");
        }
    }
}

