/**
 * Solves the given maze using DFS or BFS
 * @author Agastya "Gus" Brahmbhatt
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    // Getter for maze


    public Maze getMaze() {
        return maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // New stack to reverse arraylist
        Stack<MazeCell> tStack = new Stack<MazeCell>();
        // Creating empty arraylist and maze cell with maze cell type
        ArrayList<MazeCell> mSolution = new ArrayList<MazeCell>();
        //Defines temporary cell
        MazeCell tMazeCell = null;
        // Defines current cell
        MazeCell cMazeCell = null;
        // Should be from start to end cells
        tMazeCell = maze.getEndCell();
        // Gets all the cells in the solution till it reaches startCell
        while(tMazeCell != null && tMazeCell != maze.getStartCell())
        {
            tStack.push(tMazeCell);
            cMazeCell = tMazeCell;
            tMazeCell = cMazeCell.getParent();
        }
        tStack.push(maze.getStartCell());
        while(!(tStack.isEmpty()))
        {
            mSolution.add(tStack.pop());
        }
        return mSolution;
    }

    // Returns the row based on direction
    public int getDirectionRow(int direction, int currentRow)
    {
        int result = -1;
        if(direction == 0)
        {
            // Going north
            result = currentRow - 1;
        }
        if(direction == 1)
        {
            // Going East
            result = currentRow;
        }
        if(direction == 2)
        {
            // Going South
            result = currentRow + 1;
        }
        if(direction == 3)
        {
            // Going West
            result = currentRow;
        }
        return result;
    }
    // Returns the col based on direction
    public int getDirectionCol(int direction, int currentCol)
    {
        int result = -1;
        if(direction == 0)
        {
            // Going north
            result = currentCol;
        }
        if(direction == 1)
        {
            // Going East
            result = currentCol + 1;
        }
        if(direction == 2)
        {
            // Going South
            result = currentCol;
        }
        if(direction == 3)
        {
            // Going West
            result = currentCol - 1;
        }
        return result;
    }



    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Stack<MazeCell> dfsStack = new Stack<MazeCell>();
        dfsStack.push(maze.getStartCell());
        // Sets start cell to is explored
        maze.getStartCell().setExplored(true);
        // Defines temporary row and col for each mazeCell
        int tRow;
        int tCol;
        MazeCell tCell = null;
        // Pushes neighboring cells to stack
        while(!dfsStack.isEmpty())
        {
            // Pops the first cell from the array
            tCell = dfsStack.pop();
            for(int i = 0; i < 4; i++)
            {
                tRow = getDirectionRow(i, tCell.getRow());
                tCol = getDirectionCol(i, tCell.getCol());
                if(maze.isValidCell(tRow,tCol) && !(maze.getCell(tRow,tCol).isExplored()))
                {
                    dfsStack.push(maze.getCell(tRow,tCol));
                    maze.getCell(tRow,tCol).setExplored(true);
                    maze.getCell(tRow,tCol).setParent(tCell);
                    // Stops when it reaches end cell
                    if(maze.getCell(tRow,tCol) == maze.getEndCell())
                    {
                        break;
                    }
                }
            }
        }
        return getSolution();
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Queue<MazeCell> tQueue = new LinkedList<MazeCell>();
        tQueue.add(maze.getStartCell());
        // Sets start cell to is explored
        maze.getStartCell().setExplored(true);
        // Defines temporary row and col for each mazeCell
        int tRow;
        int tCol;
        MazeCell tCell = null;
        // Pushes neighboring cells to stack
        boolean endflag = false;
        while(!tQueue.isEmpty() && !endflag)
        {
            // Pops the first cell from the array
            tCell = tQueue.remove();
            for(int i = 0; i < 4; i++)
            {
                tRow = getDirectionRow(i, tCell.getRow());
                tCol = getDirectionCol(i, tCell.getCol());
                if(maze.isValidCell(tRow,tCol) && !(maze.getCell(tRow,tCol).isExplored()))
                {
                    tQueue.add(maze.getCell(tRow,tCol));
                    maze.getCell(tRow,tCol).setExplored(true);
                    maze.getCell(tRow,tCol).setParent(tCell);
                    // Stops when it reaches end cell
                    if(maze.getCell(tRow,tCol) == maze.getEndCell())
                    {
                        endflag = true;
                        break;
                    }
                }
            }
        }
        return getSolution();
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);
        //maze.printSolutionToFile(sol, "Resources/maze3-DFS-sol.txt");

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
        //maze.printSolutionToFile(sol, "Resources/maze3-BFS-sol.txt");
    }
}
