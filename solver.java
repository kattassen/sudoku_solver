/**
 * Class for solving sudoku.
 */

class solver {

    // Constructor
    solver (field tempField, int size, boolean v) {
        solverField = tempField;
        fieldSize = size;
        verbose = v;
    }

    // Public methods
    public void solve() {
        int correctSquares = 0;
        int iterations = 0;
	
	solverField.drawFieldCorrect();

	// Show possible field
	if (verbose)
	    solverField.drawFieldPossible();

	do {
            // Count iterations
            iterations++;

           
	    cleanRows();
	    cleanColumns();
	    cleanSections();

	    //fillSinglesRows();
	    //fillSinglesCols();

            correctSquares = 0;
            for (int row = 0; row < fieldSize; row++) {
	        for (int col = 0; col < fieldSize ; col++) {
                    if (solverField.getValue(col,row) != 0)
                        correctSquares++;
                }
            }
	} while ((correctSquares != fieldSize*fieldSize) &&
		 (iterations < 50));

	if (verbose)
	    solverField.drawFieldPossible();

	solverField.drawFieldCorrect();

        System.out.println("Solved in "+iterations+" iterations"); 
    }

    private void cleanColumns() {
        boolean result = false;
	if (false)
	    System.out.println("cleanColumns()");
	int steps = 0;
        
        for (int row = 0; row < fieldSize; row++) {
	    for (int col = 0; col < fieldSize ; col++) {
		int squareVal = solverField.getValue(col,row);
		if (squareVal != 0) {
		    // Remove squareVal from column
		    for (int remRow =  0; remRow < fieldSize ; remRow++) {
			solverField.remPossibleNumber(squareVal, col, remRow);
		    }		
		}
	    }
	}
    }


    private void cleanRows() {
	if (false)
	    System.out.println("cleanRows()");
	int steps = 0;
        
        for (int col = 0; col < fieldSize; col++) {
	    for (int row = 0; row < fieldSize ; row++) {
		//
		int squareVal = solverField.getValue(col,row);
		if (squareVal != 0) {
		    // Remove squareVal from row
		    for (int remCol =  0; remCol < fieldSize ; remCol++) {
			solverField.remPossibleNumber(squareVal, remCol, row);
		    }		
		}
	    }
	}
    }

    protected void cleanSections() {
	if (false)
	    System.out.println("cleanSections()");
        int steps = 0;
        
        for (int row = 0; row < fieldSize; row++) {
	    for (int col = 0; col < fieldSize ; col++) {

		// Get a number from a square
		int squareVal = solverField.getValue(col,row);

                // Check if the square has a correct number
		if (squareVal != 0) {
		    // Remove squareVal from 3x3 section
		    int startRow = (row/3)*3;
		    int startCol = (col/3)*3;
		    for (int remRowSection = startRow;
			 remRowSection < startRow+3 ; 
			 remRowSection++) {
			for (int remColSection = startCol; 
			     remColSection < startCol+3 ; 
			     remColSection++) {
			    
			    solverField.remPossibleNumber(squareVal, 
							  remColSection, 
							  remRowSection);
			}
		    }
		}
	    }
	}
    }

    protected boolean fillSinglesRows() {
	boolean result = false;
	
	if (false)
	    System.out.println("fillSinglesRows()");
	boolean values[] = new boolean[10];

        for (int row = 0; row < fieldSize; row++) {
	    int emptyX = 0;
	    int emptyY = 0;
	    int emptyCnt = 0;
	    // Set all tried values to false
	    for (int i = 0 ; i < 10 ; i++)
		values[i] = false;

	    for (int col = 0; col < fieldSize; col++) {			
		// Retrive a values from a row
		int tempVal = solverField.getValue(col,row);
		if (tempVal == 0) {
		    emptyCnt++;
		    emptyX = col;
		    emptyY = row;
		}
		else {
		    values[tempVal] = true;
		}
				
		//if (emptyCnt > 1)
		    //break from loop
		//    break;

		if (col == 8 && emptyCnt < 2) {
		    result = true;
		    for (int j = 1; j < 10; j++) {
			if (values[j] == false) {
			    solverField.setValue(j, emptyX, emptyY);
                        }
		    }
		}
	    }
	}
	return result;
    };

    protected boolean fillSinglesCols() {
	boolean result = false;

	if (false)
	    System.out.println("fillSinglesCols()");
	boolean values[] = new boolean[10];

        for (int col = 0; col < fieldSize; col++) {
	    int emptyX = 0;
	    int emptyY = 0;
	    int emptyCnt = 0;
	    // Set all tried values to false
	    for (int i = 0 ; i < 10 ; i++)
		values[i] = false;

	    for (int row = 0; row < fieldSize; row++) {			
		// Retrive a values from a columne
		int tempVal = solverField.getValue(col,row);

		if (tempVal == 0) {
		    emptyCnt++;
		    emptyX = col;
		    emptyY = row;
		}
		else {
		    values[tempVal] = true;
		}
				
		//if (emptyCnt > 1)
		    //break from loop
		//    break;

		if (row == 8 && emptyCnt < 2) {
		    result = true;
		    for (int j = 1; j < 10; j++) {
			if (values[j] == false) {
			    solverField.setValue(j, emptyX, emptyY);
                        }
		    }	
		}
	    }
	}
	return result;
    };

    private boolean verbose = false;
    protected field solverField = new field();
    protected int fieldSize;
}
