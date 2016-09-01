import java.util.*;
import java.io.*;
/**
 * ATCS SudokuSolver Lab
 * See the readme for a description of all files
 * 
 * @author Eric Klawitter
 * @version 11.7.2013
 */
public class SudokuBoard
{
    int [][] board;
    
    
    /**
     * constructor
     * @param String toImport name of .csv file to be used for the board
     */
    public SudokuBoard(String toImport)
    {
        board= new int[9][9];
        readIn(toImport);
        
    }

    /**
     * reads from the .csv file to create the game board
     * @param String toImport the filename of the .csv file to be imported
     * @precondition String toImport is a valid file
     */
    private void readIn(String toImport)
    {
        //reads from a scanner using .nextLine()
        File file= new File(toImport);
        Scanner input= null;
        try
        {
            input= new Scanner(file);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("can't open file");
            System.exit(1);
        }
        int count= 0;
        //while there is another line
        while (input.hasNextLine())
        {
            String temp= input.nextLine();
            //for each row
            for (int i = 0; i < 9; i++)
            {

                //isolates the integer
                String temp1;
                //if it is the last digit in the row it is the only character remaining
                if(i >  7)
                {
                    temp1=temp;
                }
                //otherwise it is the first letter
                else
                {
                    temp1=temp.substring(0,1);
                }
                //board = the integer of temp1
                board[count][i]=Integer.parseInt(temp1);
                //if it is the last index in the row, ignores cutting the string
                if (i < 8)
                {
                    //removes the added value and comma from the string
                    temp=temp.substring(2);
                }
            }
            //increments the current row
            count++;
        }
    }
                 
    /**
     * prints a string representation of the board
     */
    public void print() // print out the board
    { 
        //prints each row followed by a return character
        for (int i = 0; i < board.length; i++)
        {
            if (i % 3 == 0)
            {
                System.out.println("--------------------");
            }
            for (int x = 0; x < board[i].length; x++)
            {
                if(x%3==0)
                {
                    System.out.print(" | ");
                }
                System.out.print(board[i][x]);
                if(x == 8)
                {
                    System.out.print(" |");
                }
            }
            System.out.println("");

        }
    }
    
    /**
     * acessor for a specific spot on the board
     * @paran int r the row of the spot to be returned
     * @param int c the column of the spot to be returned
     * @returns int the value at the spot represented by the passed values
     * @precondition r and c are contained within the board
     */
    public int get(int r, int c) // return the numeral at position (r,c) 
    {
        return board[r][c];
    }
    
    /**
     * modifier for the board, removes the number at a specific position
     * @param int r the row of the location to be removed
     * @param int c the column of the location to be removed
     * @precondition r and c are contained within the board
     */
    public void remove(int r, int c) // remove the numeral at position (r,c) 
    {
        set(r, c, 0);
    }
    
    /**
     * modifier for the board, sets a specific location to the passed integer
     * @param int r the row of the location to be set
     * @param int c the column of the location to be set
     * @param int toPlace the value to be set at the indicated location
     * @precondition r and c are contained within the board
     * @postcondition all values on the board are between 0 and 9
     */
    public void set(int r, int c, int toPlace)
    {
        board[r][c]= toPlace;
    }
    
    /**
     * accessor to check if a value can be placed at a specific location
     * @param int r the row of the location to be checked
     * @param int c the column of the location to be checked
     * @param int n the prospective value to be added to the board
     * @precondition r and c are valid locations on the board
     */
    public boolean canPlace(int r, int c, int n) // true if the board would allow placing n at (r,c) 
    {
        //returns true if if the value can be placed in its row, column, and box
        return ((canPlaceRow(r,n) && canPlaceCol(c,n))&& canPlaceBox(r,c,n));
    }
    
    /**
     * helper method to check if a value can be placed in its row
     * @param int r the row to be checked
     * @param int n the integer to be tested
     * @precondition r is a valid row
     * @returns boolean true if can be placed, false if the value is contained in the row
     */
    private boolean canPlaceRow(int r, int n)
    {
        //isolates the row and checks if n is contained
        int [] temp= board[r];
        for (int i = 0; i <temp.length; i++)
        {
            if (temp[i] == n)
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * helper method to check if a value can be placed in its column
     * @param int c the column to be checked
     * @param int n the value to be tested
     * @precondition c is a valid column
     * @returns boolean true if can be placed, false if the value is contained in the column
     */
    private boolean canPlaceCol(int c, int n)
    {
        //isolates the column and checks if n is contained
        for (int i= 0; i < board.length; i++)
        {
            if (board[i][c]==n)
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * helper method to determine if a value can be placed in its 3x3 box
     * @param int r the row of the location to be tested
     * @param int c the column of the location to be tested
     * @param int n the value of the location to be tested
     * @returns boolean true if can be placed, false if the value is contained in the box
     */
    private boolean canPlaceBox(int r, int c, int n)
    {
        //new 3x3 grid representing the box
        int[][] box= new int[3][3];
        //what "row" of boxes its in (1-3)
        int rowSet= r / 3;
        //what "column" of boxes it is in (1-3)
        int colSet= c / 3;
        //first row/column in the box is the set of boxes * 3
        int firstRow= rowSet * 3;
        int firstCol= colSet * 3;
        int row= 0;
        //from the first row to 2 rows after it:
        for (int i= firstRow; i< firstRow + 3; i++)
        {
            //from the first column to 2 columns after it
            int col= 0;
            for (int x = firstCol ; x < firstCol + 3; x++)
            {
                //the appropriate spot in the 3x3 grid is the value
                box[row][col]= board[i][x];
                col++;
            }
            row++;
        }
        //checks the 3x3 grid to see if n is contained
        for (int y=0; y < box.length; y++)
        {
            for(int z = 0; z< box[y].length; z++)
            {
                if (box[y][z]==n)
                {
                    return false;
                }
            }
        }
        return true;
    }
                
   /**
    * determines if the board is solved
    * @returns boolean true if all spots are filled, false otherwise
    * @precondition no rules are violated
    */         
    public boolean solved()
    {
        //tests if the sum of all the digits is equal to (1+2+3...+9) * 9
        //if it is, then every spot is filled
        int count= 0;
        for (int i = 0; i < 9; i++)
        {
            for (int x = 0; x < 9; x++)
            {
                count+= board[i][x];
            }
        }
        return (count== 405);
    }

}
