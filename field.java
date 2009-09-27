/**
 * Class for a sudoku field.
 */

class field {

    // Constructors
    public field () {	

	fieldSquare = new square[9][9];
	for (int i = 0; i < size; i++)
	    for (int j = 0; j < size; j++)
		fieldSquare[i][j] = new square();
    }

    // Public methods

    // Set a value for a square
    public void setValue (int value, int posX, int posY) {
	if (false)
	    System.out.println("Set correct number: ("+posX+","+posY+") Value:"+value);

	fieldSquare[posX][posY].setCorrectValue(value);
    }

    // Get a value for a square
    public int getValue (int posX, int posY) {
	int value = fieldSquare[posX][posY].getCorrectValue();

	if (false)
	    System.out.println("Get correct number: ("+posX+","+posY+") Value:"+value);
	
	return value;
    }

    // Get numbersLeft for a square
    public int getNumbersLeft (int posX, int posY) {
	int value = fieldSquare[posX][posY].getNumbersLeft();

	if (false)
	    System.out.println("Get Numbers left for square: ("+posX+","+posY+") NumbersLeft:"+value);
	
	return value;
    }

    // Remove a value from possible list
    public void remPossibleNumber(int value, int posX, int posY) {
	if (false)
	    System.out.println("Remove possible number: ("+posX+","+posY+") Value:"+value);

	fieldSquare[posX][posY].remPossibleNumber(value);
    }

     /**
     * Method for drawing the sudoku field (correct numbers) in a terminal.
     */
    public void drawFieldCorrect() {
	String temp = "";
   
	for (int i = 0; i < size ;i++)
	    {
		temp = "";
		for (int j = 0; j < size ;j++)
		    {
			// Add a horisontal line for grouping
			if (((j % (size/3)) == 0) && j > 0)
			    temp = temp.concat("| ");

			// Get the number from a square
			int squareVal = fieldSquare[j][i].getCorrectValue();
			
			// Add the number to the a horisontal line of numbers 
			if (squareVal == 0)
			    temp = temp.concat("  ");
			else
			    temp = temp.concat(Integer.toString(squareVal)).concat(" ");
		    }
		// Add a vertical line for grouping
		if ((((i+1) % (size/3)) == 0) && i < size-1)
		    {
			temp = temp.concat("\n");
			for (int k = 0; k < size*2+(2*(size/3)) ; k++)
			    {
				temp = temp.concat("-");
			    }
		    }
		System.out.println(temp);
	    }
	System.out.println("\n");
    }

    /**
     * Method for drawing the sudoku field (possible numbers) in a terminal.
     */
    public void drawFieldPossible() {
	String temp = "";

	for (int i = 0; i < size ;i++) {
	    System.out.println("------------------------------------------------------------------------");
	    for (int i2 = 0; i2 < 3; i2++) {
		temp = "| ";
	
		for (int j = 0; j < size ;j++) {
		    for (int k = i2*3+1; k < i2*3+4 ;k++) {
			// Get the possible numbers from all squares
			{
			    if (fieldSquare[j][i].
				getPossibleNumbers(k))
				temp = temp.concat(""+k+"");
			    else
				temp = temp.concat(" ");
			}
		    }
		    temp = temp.concat("  |  ");
		}
		System.out.println(temp);
	    }
	}
	System.out.println("------------------------------------------------------------------------");
    }

    // Squares in the field
    protected square[][] fieldSquare;

    // Properties of field
    protected int size = 9;
}
