/**
 * Developer:       Kelvin Chan
 * Language:        Java 1.8.0_181
 * Version:         1.0.0
 * Last revised:    30/09/2018
 */

package java_solution;
import java_solution.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        // Allows user to find the maze file.
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(
                "Text files", "txt");
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setDialogTitle("Choose your maze file");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(null);
        String filename = "";

        int counter = 0;
        while (result != JFileChooser.APPROVE_OPTION && counter < 3){
            counter ++;
            result = fileChooser.showOpenDialog(null);
        }

        if (result == JFileChooser.APPROVE_OPTION) {
            filename = fileChooser.getSelectedFile().getName();
        } else {
            System.exit(0);
        }

        // Instantiated Maze to store the properties and the map
        Maze maze = new Maze(filename);

        // List of directions
        List<Character> allDirections = new ArrayList<Character>(Arrays.asList('N', 'E', 'S', 'W'));
        Character direction;

        // Instantiated Player to allow it to move in the maze.
        Player player = new Player(maze.getStart_x(), maze.getStart_y());

        // True to stop the search loop
        boolean stop = false;

        // True to show dead-end. Resets the 'Player' to last junction if dead-end is found.
        boolean stuck;

        // True if there are any walkable directions (i.e. not a dead-end)
        boolean check;

        // True if there is a solution
        boolean solution = false;

        // Stores walkable_directions.
        List<Character> walkable_directions = new ArrayList<Character>();

        while (stop == false && solution == false){
            stuck = true;
            walkable_directions.clear();

            // Finds all walkable directions from the location of the player.
            for (Character temp_direction: allDirections){
                check = player.checkAdjacent(temp_direction, maze);
                if (check == true){
                    walkable_directions.add(temp_direction);
                }
            }

            // Move the player if it is not at a dead-end
            if (walkable_directions.size() > 0){
                direction = walkable_directions.get(0);
                player.move(direction, walkable_directions.size());
                stuck = false;

                // Stop searching is solution is found.
                if (player.location().get(0) == maze.getEnd_x() && player.location().get(1) == maze.getEnd_y()){
                    solution = true;
                }
            }

            // If Player cannot move due to walls or dead-end paths:
            if (stuck == true){

                // End the search if all possibilities are tested (i.e. Player is back at starting position)
                if (player.getPath().size() == 1){
                    stop = true;
                    break;
                }

                // Reset back to last junction if moved from the junction
                // Go to last 2 junction if cannot move in the last junction (last junction is dead-end).
                // Also deletes last junction if jumping back 2 junctions.
                if (player.isMoved() == true){
                    player.reset_to_last_junction();
                } else {
                    player.reset_to_last2_junction();
                }
            }
        }

        // If solution is found, the solution is drawn to console
        if (solution) {
            List<Integer> temp_coord = new ArrayList<Integer>(2);

            char[][] outputMaze2D = new char[maze.getHeight()][maze.getWidth()];

            for (int i = 0; i < maze.getWidth(); i++) {
                for (int j = 0; j < maze.getHeight(); j++) {
                    temp_coord.clear();
                    temp_coord.add(i);
                    temp_coord.add(j);
                    if (i == maze.getStart_x() && j == maze.getStart_y()) {
                        outputMaze2D[j][i] = 'S';
                    } else if (i == maze.getEnd_x() && j == maze.getEnd_y()) {
                        outputMaze2D[j][i] = 'E';
                    } else if (maze.getStoreMaze()[j][i] == 1) {
                        outputMaze2D[j][i] = '#';
                    } else if (player.getPath().contains(temp_coord)) {
                        outputMaze2D[j][i] = 'X';
                    } else {
                        outputMaze2D[j][i] = ' ';
                    }
                }
            }

            // Print out all lines of the solution to console
            for (int k = 0; k < maze.getHeight(); k++) {
                System.out.println(outputMaze2D[k]);
            }

            // Ask if the user wants to output the solution to a text file.
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("\nWould you like to store the solution to a text file? (Y/N): ");
            String store_bool = reader.nextLine();

            // Store solution to text file if answered yes or y
            if (store_bool.toUpperCase().equals("Y") || store_bool.toUpperCase().equals("YES")){

                // Ask for file name
                System.out.println("Please type the name of the output file: ");
                String output_file = reader.nextLine();
                reader.close();

                // Write solution to file.
                BufferedWriter output = null;
                try {
                    File file = new File(output_file + ".txt");
                    output = new BufferedWriter(new FileWriter(file));
                    String output_line;
                    for (int k = 0; k < maze.getHeight(); k++) {
                        output_line = new String(outputMaze2D[k]);
                        output.write(output_line + '\n');
                    }
                    reader.close();

                } catch ( IOException e ) {
                    e.printStackTrace();
                } finally {
                    if ( output != null ) {
                        output.close();
                    }
                }
            } else {
                reader.close();
            }

        } else {
            System.out.println("No solution is found. All possibilities were tested.");
        }
    }
}
