# Developer:    Kelvin Chan
# Language:     Python 3.6.4
# Version:      1.0.0
# Last revised: 30/09/2018


# Defines how coordinates shift when moving north, south, east or west.
shift = {
    'N': lambda y: y - 1,
    'S': lambda y: y + 1,
    'E': lambda x: x + 1,
    'W': lambda x: x - 1
}
NS = ['N', 'S']
EW = ['E', 'W']

class Player():
    """
    This class allows an object to be created on the maze which can move along the maze to find the solution.
    The path and the junctions along the path are stored.
    Tested directions at each junction are stored to prevent repeated steps.
    This object can reset back to its last or 2nd-to-last seen junction to allow other steps to be taken.
    """

    def __init__(self, start_x, start_y):
        """
        Instantiate the object with its starting position.
        """
        self.x = start_x
        self.y = start_y

        self._dead_end_direction = [[]]
        self._moved = False
        self._junction_index = [1]

        self.path = [[start_x, start_y]]

    def reset_to_last_junction(self):
        """
        Reset to its last seen junction
        """
        self.path = self.path[:self._junction_index[-1]]
        self.x, self.y = self.path[-1]
        self._moved = False

    def reset_to_last2_junction(self):
        """
        Reset to its 2nd-to-last seen junction
        """
        del self._junction_index[-1]
        del self._dead_end_direction[-1]
        self.reset_to_last_junction()

    def junction_index(self):
        return self._junction_index

    def dead_end_direction(self):
        return self._dead_end_direction

    def moved(self):
        return self._moved

    def location(self):
        return self.path[-1]

    def fullpath(self):
        return self.path

    def checkAdjacent(self, direction, maze):
        """
        Checks whether the adjacent location is free to walk. This function checks whether if the new location is
        a wall, if it follows a failed path or if it goes back to the start.
        :param x: Current location x
        :param y: Current location y
        :param direction: One of 'N' 'S' 'E' 'W'
        :return:
        """

        y = self.y
        x = self.x

        # Shift x or y depending on the given direction.
        if direction in NS:
            y = shift[direction](y)
        elif direction in EW:
            x = shift[direction](x)

        # Check new location for obstacle or unwanted direction
        if maze[y][x] == 1 or ([x, y] in self.fullpath()) or (self.moved() is False and direction in self.dead_end_direction()[-1]):
            return False
        else:
            return True

    def move(self, direction, no_of_exits):
        """
        Moves the object to a new location. Best used after checking with checkAdjacent.
        :param direction: One of 'N' 'S' 'E' 'W':
        :param no_of_exits: Number of walkable directions (i.e. number of exits for junctions or just 1 for hallway)
        """
        if direction in NS:
            self.y = shift[direction](self.y)
        else:
            self.x = shift[direction](self.x)

        # Store new movement
        self.path.append([self.x, self.y])

        # Stores new tested junction direction after reset
        if self.moved() is False:
            self._dead_end_direction[-1].append(direction)

        # Stores the direction and location when testing a new junction.
        if no_of_exits > 1 and self.moved() is True:
            self._dead_end_direction.append([direction])
            self._junction_index.append(len(self.fullpath()) - 1)

        self._moved = True