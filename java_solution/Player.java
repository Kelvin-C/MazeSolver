/**
 * Developer:       Kelvin Chan
 * Language:        Java 1.8.0_181
 * Version:         1.0.0
 * Last revised:    30/09/2018
 */

package java_solution;
import java_solution.*;

import java.util.ArrayList;
import java.util.List;

public class Player {
    /**
     * An object which essentially spawns a user on the maze.
     * The object can move around the maze and follow the paths.
     */

    // Current x, y coordinates.
    private int x;
    private int y;

    // Directions from junctions that give dead-ends
    private List<List<Character>> dead_end_direction = new ArrayList<List<Character>>();

    // Boolean to show whether the object has moved in the maze.
    private boolean moved = false;

    // Index of locations with maze junctions
    private List<Integer> junction_index = new ArrayList<Integer>(1);

    // Stores all locations the object has travelled.
    private List<List<Integer>> path = new ArrayList<List<Integer>>();;

    public Player(int start_x, int start_y){
        this.x = start_x;
        this.y = start_y;

        // dead_end_direction = [[]]
        List<Character> empty = new ArrayList<Character>(0);
        this.dead_end_direction.add(empty);

        // junction_index = [1]
        this.junction_index.add(1);

        // path = [[start_x, start_y]]
        List<Integer> xy = new ArrayList<Integer>(2);
        xy.add(this.x);
        xy.add(this.y);
        this.path.add(xy);
    }

    public void reset_to_last_junction(){
        /**
         * Resets to its last seen junction.
         */
        this.path = this.path.subList(0, this.junction_index.get(this.junction_index.size() - 1));
        this.x = this.path.get(this.path.size() - 1).get(0);
        this.y = this.path.get(this.path.size() - 1).get(1);
        this.moved = false;
    }

    public void reset_to_last2_junction(){
        /**
         * Reset to its 2nd-to-last seen junction
         */
        this.junction_index.remove(this.junction_index.size() - 1 );
        this.dead_end_direction.remove(this.dead_end_direction.size() - 1);
        this.reset_to_last_junction();
    }

    public List<Integer> getJunction_index(){
        return this.junction_index;
    }

    public List<List<Character>> getDead_end_direction(){
        return this.dead_end_direction;
    }

    public boolean isMoved(){
        return this.moved;
    }

    public List<Integer> location(){
        return this.path.get(this.path.size() - 1);
    }

    public List<List<Integer>> getPath(){
        return this.path;
    }

    public boolean checkAdjacent(Character direction, Maze maze){
        /**
         * Checks whether the adjacent location is free to walk. This function checks whether if the adjacent location is
         * a wall, if it follows a failed path or if it goes back to a previous location.
         */
        int temp_y = this.y;
        int temp_x = this.x;

        // Temporarily shift x or y depending on the given location
        switch (direction){
            case 'N': temp_y = temp_y - 1;
                break;
            case 'S': temp_y = temp_y + 1;
                break;
            case 'E': temp_x = temp_x + 1;
                break;
            case 'W': temp_x = temp_x - 1;
                break;
        }

        // Coordinate of the new location
        List<Integer> coord1 = new ArrayList<Integer>(2);
        coord1.add(temp_x);
        coord1.add(temp_y);

        // Check the new location for obstacles and if the new location follows the wrong path.
        if (maze.getStoreMaze()[temp_y][temp_x] == 1 ||
                this.path.contains(coord1) ||
                (this.isMoved() == false &&
                        this.dead_end_direction.get(this.dead_end_direction.size()-1).contains(direction))
            ){
            return false;
        } else {
            return true;
        }
    }

    public void move(Character direction, int no_of_exits){
        /**
         * Moves the object to a new location. Best used after checking with checkAdjacent.
         */

        // Shifts x or y to move the object
        switch (direction){
            case 'N': this.y = this.y - 1;
                break;
            case 'S': this.y = this.y + 1;
                break;
            case 'E': this.x = this.x + 1;
                break;
            case 'W': this.x = this.x - 1;
                break;
        }

        // Stores the new location
        List<Integer> coord = new ArrayList<Integer>(2);
        coord.add(this.x);
        coord.add(this.y);
        this.path.add(coord);

        // Stores the direction recently moved from a new junction so it is known if the current path is wrong.
        if (this.isMoved() == false){
            this.dead_end_direction.get(this.dead_end_direction.size() - 1).add(direction);
        }

        // Stores the direction and the location when testing a new juncion.
        if (no_of_exits > 1 && this.isMoved() == true){
            List<Character> new_direction = new ArrayList<Character>(1);
            new_direction.add(direction);
            this.dead_end_direction.add(new_direction);
            this.junction_index.add(this.path.size()-1);
        }
        this.moved = true;

    }

}
