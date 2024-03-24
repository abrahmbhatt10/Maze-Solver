/**
 * A class to test our MazeSolver
 * @author: Agastya "Gus" Brahmbhatt
 * Added generateAnswerKeys and compareAnswer functions to Test DFS and BFS answers
 * @version: 03/04/2022
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MazeTester {

    /**
     * Tests the getSolution() function against a
     * hardcoded solution.
     */
    public static void testGetSolution() {
        // Read in a new maze
        Maze maze = new Maze("Resources/mazeGetSolutionTester.txt");

        // Create our expected solution
        ArrayList<MazeCell> expected = new ArrayList<MazeCell>();
        expected.add(maze.getCell(5, 0));
        expected.add(maze.getCell(5, 1));
        expected.add(maze.getCell(4, 1));
        expected.add(maze.getCell(3, 1));
        expected.add(maze.getCell(2, 1));
        expected.add(maze.getCell(1, 1));
        expected.add(maze.getCell(0, 1));
        expected.add(maze.getCell(0, 2));
        expected.add(maze.getCell(0, 3));
        expected.add(maze.getCell(0, 4));
        expected.add(maze.getCell(0, 5));

        // Manually set parents according to our solution
        for (int i=expected.size()-1; i>0; i--) {
            expected.get(i).setParent(expected.get(i-1));
        }

        // Get the solution from MazeSolver
        MazeSolver ms = new MazeSolver(maze);
        ArrayList<MazeCell> received = ms.getSolution();

        // Check if solutions are equal size
        if (received.size() != expected.size()) {
            System.out.println("Test Get Solution Failed: Paths are different sizes");
            System.out.println("Expected: " + expected.size() + " but got: " + received.size());
            return;
        }

        // Check that each cell in the solution is the same
        for (int i=0; i<expected.size(); i++) {
            MazeCell exCell = expected.get(i);
            MazeCell recCell = received.get(i);

            if (exCell.getRow() != recCell.getRow() || exCell.getCol() != recCell.getCol()) {
                System.out.println("Test Get Solution Failed");
                System.out.println("Expected: (" + exCell.getRow() + ", " + exCell.getCol() + ")");
                System.out.println("But Got: (" + recCell.getRow() + ", " + recCell.getCol() + ")");
                return;
            }
        }

        System.out.println("Test Get Solution Passed!");
    }

    public static void main(String[] args) {
        MazeTester.testGetSolution();

        if(args.length > 1 && args[1].equals("-generate"))
        {
            generateAnswerKeys();
            return;
        }
        Maze maze1 = null;
        MazeSolver ms1 = new MazeSolver();
        ArrayList<MazeCell> sol = null;
        int errflags = 0;
        for(int i = 1; i < 6; i++)
        {
            maze1 = new Maze("Resources/maze" + i + ".txt");
            ms1.setMaze(maze1);
            // Solve the maze using DFS and print the solution
            sol = ms1.solveMazeDFS();
            if(compareAnswer(ms1,"Resources/maze" + i + "-DFS-sol.txt") == false)
            {
                System.out.println("Answer does not match to DFS answer key");
                errflags++;
            }


            // Reset the maze
            maze1.reset();

            // Solve the maze using BFS and print the solution
            sol = ms1.solveMazeBFS();
            if(compareAnswer(ms1,"Resources/maze" + i + "-BFS-sol.txt") == false)
            {
                System.out.println("Answer does not match to BFS answer key");
                errflags++;
            }
        }
        System.out.println();
        System.out.println("TESTS COMPLETED");
        if(errflags == 0)
        {
            System.out.println("All answers matched answer keys");
        }
        else {
            System.out.println(errflags+" answers did not match answer keys");
        }
    }

    // Generates answer keys for extras part of the problem set
    // Manually create text files of different sizes with mazeFILENO.txt in Resources
    // This function will create the DFS and BFS answer keys
    public static void generateAnswerKeys(){
        // Create the Maze to be solved
        String fileName = "Resources/maze";
        String fileExtension = ".txt";
        String fileStr = "";
        Maze maze1 = null;
        MazeSolver ms1 = new MazeSolver();
        ArrayList<MazeCell> sol = null;
        for(int i = 1; i < 6; i++)
        {
            fileStr = fileName + i + fileExtension;
            maze1 = new Maze(fileStr);
            ms1.setMaze(maze1);
            // Solve the maze using DFS and print the solution
            sol = ms1.solveMazeDFS();
            maze1.printSolution(sol);
            maze1.printSolutionToFile(sol, "Resources/maze" + i + "-DFS-sol.txt");

            // Reset the maze
            maze1.reset();

            // Solve the maze using BFS and print the solution
            sol = ms1.solveMazeBFS();
            maze1.printSolution(sol);
            maze1.printSolutionToFile(sol, "Resources/maze" + i + "-BFS-sol.txt");
        }
    }

    // Compares answer to answer key txts
    // First param is object MaseSolver for function getSolutions to compare with answers in txt file
    // Second param is text file with the answer keys that is uploaded to a 2D char array
    // Returns true is compare matches, false if compare fails
    public static boolean compareAnswer(MazeSolver rSol, String fileNameAnswerKey)
    {
        try {
            File myObj = new File(fileNameAnswerKey);
            myObj.setReadOnly();
            Scanner myReader = new Scanner(myObj);
            System.out.println("Comparing file "+fileNameAnswerKey);
            char tanswerState = ' ';
            char[][] answerGrid = new char[rSol.getMaze().getNumRows()][rSol.getMaze().getNumCols()];
            //int row = rSol.getMaze().getNumRows()-1;
            int row = 0;
            int col = 0;
            int lcol = 0;
            String lineStr;
            while (myReader.hasNextLine()) {
                lineStr = myReader.nextLine();
                for(lcol = 0; lcol < lineStr.length(); lcol++)
                {
                    tanswerState = lineStr.charAt(lcol);
                    answerGrid[row][col] = tanswerState;
                    col++;
                    if (col >= rSol.getMaze().getNumCols()) {
                        break;
                    }
                }
                row++;
                col = 0;
                if(row >= rSol.getMaze().getNumRows())
                {
                    break;
                }
            }
            myReader.close();
            /*System.out.println("ANSWER GRID");
            for (row=0; row< rSol.getMaze().getNumRows(); row++) {
                for (col=0; col<rSol.getMaze().getNumCols(); col++) {
                        System.out.print(answerGrid[row][col]);
                }
                System.out.print('\n');
            }
            System.out.println("END ANSWER GRID");
            */
            ArrayList<MazeCell> solGrid = rSol.getSolution();
            MazeCell tCell = null;
            for(int i = 0; i < solGrid.size(); i++)
            {
                tCell = solGrid.get(i);
                // System.out.println("Solution i "+i+" "+tCell.getRow()+"-"+tCell.getCol()+"="+answerGrid[tCell.getRow()][tCell.getCol()]);
                if(i == 0 && !(answerGrid[tCell.getRow()][tCell.getCol()] == 'A'))
                {
                    System.out.println("Starting does not match "+tCell.getRow()+"-"+tCell.getCol()+"="+answerGrid[tCell.getRow()][tCell.getCol()]+"END");
                    return false;
                }
                else if(i == solGrid.size() - 1 && !(answerGrid[tCell.getRow()][tCell.getCol()] == 'B'))
                {
                    System.out.println("Ending does not match"+tCell.getRow()+"-"+tCell.getCol()+"="+answerGrid[tCell.getRow()][tCell.getCol()]+"END");
                    return false;
                }
                else if(i > 0 && i < solGrid.size() - 1 && !(answerGrid[tCell.getRow()][tCell.getCol()] == '*'))
                {
                    System.out.println("Does does not match i "+i+" row "+tCell.getRow()+" col "+tCell.getCol()+"="+answerGrid[tCell.getRow()][tCell.getCol()]+"END");
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println("Solution matches "+fileNameAnswerKey);
        return true;
    }
}
