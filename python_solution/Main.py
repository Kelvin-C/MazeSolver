# Developer:    Kelvin Chan
# Language:     Python 3.6.4
# Version:      1.0.0
# Last revised: 30/09/2018

from player import Player

# Start the script with a statement to show user that they will choose the maze file.
print("Importing the maze text file. Press any key to continue.")

try:
    #Allows user to read the statement.
    import msvcrt
    msvcrt.getch()
except ImportError:
    pass

# Try to use tkinter to make finding the file easier.
try:
    from tkinter import filedialog, Tk

    Tk().withdraw()
    mazefile = filedialog.askopenfilename(filetypes=(("txt files", "*.txt"), ("all files", "*.*")), title="Choose the maze file")

    # Limit the number of 'cancels' to 3.
    counter = 0
    while mazefile == '' and counter < 3:
        counter += 1
        mazefile = filedialog.askopenfilename(filetypes=(("txt files", "*.txt"), ("all files", "*.*")),
                                               title="Choose the maze file")

# Allows file to be typed if tkinter is not installed.
except ImportError:
    mazefile = input("Please type in the filename or path of the maze file.\n")

# Exit script if no file is chosen.
if mazefile == '':
    import sys
    sys.exit()

# Open the text file with the maze
file = open(mazefile, 'r')

# First 3 lines of the text file has the properties of the maze
width, height = list(map(int, file.readline().split()))
start_x, start_y = list(map(int, file.readline().split()))
end_x, end_y = list(map(int, file.readline().split()))

# Store the maze map
maze = file.readlines()

# Close the text file
file.close()

# Convert the maze map into a list of integers to use smaller storage.
for i in range(len(maze)):
    maze[i] = list(map(int, maze[i].split()))

# Movable directions
allDirections = ['N', 'E', 'S', 'W']

# True when the solution is found or when all paths are tested
stop = False

# True when a path has been found. False when maze has no solution.
solution = False

player = Player(start_x, start_y)

# Loop to find the solution
while (stop == False) and (solution == False):

    # Check if the 'Player' have moved in a loop cycle. False means dead-end.
    stuck = True

    # Store all walkable directions from the Player's current position
    walkable_directions = []
    for direction in allDirections:
        check = player.checkAdjacent(direction, maze)
        if check is True:
            walkable_directions.append(direction)

    # If there are any walkable directions, move the Player
    if len(walkable_directions) > 0:
        direction = walkable_directions[0]
        player.move(direction, len(walkable_directions))
        stuck = False

        # Stop searching when solution is found.
        if player.location() == [end_x, end_y]:
            print(len(player.fullpath()))
            solution = True

    # If player cannot move due to walls or redundant paths.
    if stuck is True:

        # End the search if all possibilities are tested.
        if len(player.fullpath()) == 1:
            stop = True
            break

        # Reset back to last junction if moved from the junction
        # Go to last 2 junction if cannot move in the last junction (last junction is dead-end).
        # Also deletes last junction if jumping back 2 junctions.
        if player.moved() is True:
            player.reset_to_last_junction()
        else:
            player.reset_to_last2_junction()

if solution:
    # Draw the solution
    for i in range(width):
        for j in range(height):
            if i == start_x and j == start_y:
                maze[j][i] = 'S'
            elif i == end_x and j == end_y:
                maze[j][i] = 'E'
            elif maze[j][i] == 1:
                maze[j][i] = '#'
            elif [i, j] in player.fullpath():
                maze[j][i] = 'X'
            else:
                maze[j][i] = ' '

    # Appends the 'new line' string at the end of each row.
    for k in range(height):
        maze[k][-1] = maze[k][-1]+'\n'

    # Convert the maze list into lines of a single string.
    maze = list(map(lambda line: ' '.join(line), maze))

    # Output answer to console.
    for line in maze:
        print(line)

    store_bool = input("\nWould you like to store the solution to a text file? (Y/N): ")

    # Output to text file.
    if store_bool.upper() in ['Y','YES']:
        outputfile = input("Please type the name of the output file: ")
        output = open(outputfile+'.txt', 'w')
        for line in maze:
            output.write(line)
        output.close()
else:
    print("No solution is found. All possibilities were tested.")