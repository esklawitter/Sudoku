/**
 * ATCS SudokuSolver Lab
 * See the readme for a description of all files
 * 
 * @author Eric Klawitter
 * @version 11.7.2013
 */
public class Solver
{
    // insance variable stack that contains all the moves in order
    static Stack<Location> theStack;
    //the game board that the moves are made on
    static SudokuBoard b;
    
    /**
     * main method that solves the board stored in the csv file
     * to edit change String boardFile
     */
    public static void main(String[] args)
    {
        String boardFile= "Blank.csv";
        theStack= new LinkedList<Location>();
        b= new SudokuBoard(boardFile);
        solve();
        
    }
    
    /**
     * public method to call which solves and then outputs the board
     */
    private static void solve()
    {
        //while the board is not solved
        while (! b.solved())
        {
            //find teh most constrained location
            Location toPush= getMostConstrained();
            //if the most constrained location has 0 possible values pops the move off the top of the board
            if (toPush.getConstrain()== 0)
            {
                change();
            }
            //otherwise pops the first value off the stack in the Location and puts it on the board
            //pushes the location onto the solver's stack
            else
            {
                b.set(toPush.getRow(), toPush.getCol(), toPush.getNext());
                theStack.push(toPush);
            }
        }
        //when solved, it prints!
        b.print();
            
            
    }
    
    /**
     * returns the most constrained location
     * @returns Location the most contstrained location
     */
    private static Location getMostConstrained()
    {
        //calls helper method to get all unoccupied locations
        Vector<Location> locs= getConstraints();
        //searches sequentially for the Location with the fewest possible locations
        Location toReturn= locs.get(0);
        int possibles= toReturn.getConstrain();
        for (int i= 1; i < locs.size(); i++)
        {
            if (locs.get(i).getConstrain() < possibles)
            {
                toReturn= locs.get(i);
                possibles= toReturn.getConstrain();
            }
        }
        //returns the location with the fewest possible values
        return toReturn;
    }
    
    /**
     * helper method for getMostConstrained()
     * @returns Vector<Location> a vector containing a Location for every unsolved spot on the board
     */
    private static Vector<Location> getConstraints()
    {
        //creates a new Vector<Location> to hold a Location for every unoccupied space
        Vector<Location> all= new Vector<Location>();
        //looks at every spot on the board, if empty, creates a new Location
        for (int r = 0; r < 9; r++)
        {
            for (int c= 0; c < 9; c++)
            {
                if (b.get(r, c) == 0)
                {
                    all.add(new Location(r,c,b));
                }
            }
        }
        //returns the Vector
        return all;
    }
    
    /**
     * helper method for solve() which pops objects off the stack and removes them from the board if the current board is unsolvable
     */
    private static void change()
    {
        //if the top Location on the stack has  no further moves
        if (theStack.peek().getConstrain() ==0)
        {
            //While the top location on the stack has no more possible moves
            while (theStack.peek().getConstrain() ==0)
            {
                //if removing this Location makes theStack empty, the game is unsolvable
                if (theStack.size() == 1)
                {
                    throw new Error("game unsolvable");
                }
                //pops the location from the stack
                Location temp=theStack.pop();
                //removes the value of the popped Location from the board
                b.remove(temp.getRow(), temp.getCol());
            }
        }
        //updates the board with the next possible move in the most constrained location
        b.set(theStack.peek().getRow(), theStack.peek().getCol(), theStack.peek().getNext());
    
    }
       
}
