/**
 * Class for a square on a sudoku field.
 */

class square {

    // Constructor
    square () {
	correctValue = 0;
        for (int i = 0 ; i < 10 ; i++) {
            possibleNumbers[i] = true;
	}
    }

    // Public methods

    /** 
     *  Function: getCorrectValue()
     *  
     *  Returns correct value for a square,
     *  if no correct value exists 0 is returned.
     */
    public int getCorrectValue() {
	return correctValue;
    };


    /** 
     *  Function: getNumbersLeft()
     *  
     *  Returns how many numbers there is left in a square
     */
    public int getNumbersLeft() {
	return numbersLeft;
    };


    /** 
     *  Function: setCorrectValue()
     *  
     *  Sets correct value for a square.
     */
    public void setCorrectValue(int value) {
	correctValue = value;

	if (value != 0) {
	    // Set all numbers to false
	    for (int i = 0; i < 10; i++) {
		possibleNumbers[i] = false;
	    }
	    possibleNumbers[value] = true;
	    numbersLeft = 1;
	}
    };

    /** 
     *  Function: remPossibleNumber(int value)
     *  
     *  Removes a number from list
     */
    public void remPossibleNumber(int value) {
	if (possibleNumbers[value] == true && correctValue == 0) {
	       possibleNumbers[value] = false;
	       numbersLeft--;
               if (numbersLeft == 1) {
                    for (int i = 1; i < 10; i++) {
                        if (possibleNumbers[i]) {
                            correctValue = i; 
                        }
		    }
              }
	}
    };

    /** 
     *  Function: getPossibleNumbers()
     *  
     *  Gets the possible numbers from list
     */
    public boolean getPossibleNumber(int number) {
	return possibleNumbers[number];
    };

    // Properties for a single square
    protected int correctValue;
    protected boolean[] possibleNumbers = new boolean[10];
    protected int numbersLeft = 9; 
}
