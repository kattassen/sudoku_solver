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
        int maxIterations = 50;
	
	solverField.drawFieldCorrect();

	// Show possible field
	if (verbose)
	    solverField.drawFieldPossible();

	do {
            // Count iterations
            iterations++;

	    cleanRows();
	    cleanColumns();
	    cleanBox();

            findNakedPairs();

            correctSquares = 0;
            for (int row = 0; row < fieldSize; row++) {
	        for (int col = 0; col < fieldSize ; col++) {
                    if (solverField.getValue(col,row) != 0)
                        correctSquares++;
                }
            }
	} while ((correctSquares != fieldSize*fieldSize) &&
		 (iterations < maxIterations));

	if (verbose)
	    solverField.drawFieldPossible();

	solverField.drawFieldCorrect();

        if (iterations < maxIterations)
            System.out.println("Solved in "+iterations+" iterations"); 
        else
            System.out.println("Not solved in "+iterations+" iterations"); 
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

    protected void cleanBox() {
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

    private boolean findNakedPairs() {
	boolean result = false;
	int[][] pair = new int[2][2];
        int pairCount;
        boolean correctPair = true;
        int firstCol = 0;
        int firstRow = 0; 

        for (int row = 0; row < fieldSize; row++) {
            pairCount = 0;
            correctPair = true;
	    for (int col = 0; col < fieldSize ; col++) {

		// Check if there is 2 numbers left
	        if (solverField.getNumbersLeft(col,row) == 2) {

		    // Check which two numbers are left in square
		    int pairNumbers = 0;
		    for (int i = 1; i < fieldSize + 1; i++) {
			if (solverField.getPossibleNumber(i,col, row)) {
                            if (pairCount == 0) {
			        pair[pairCount][pairNumbers] = i;
                                firstCol = col;
                                firstRow = row;
                            }
                            else if (pairCount == 1) {
                                if (pair[0][pairNumbers] == i) {
                                    if (correctPair) {
                                        correctPair = false;
                                    }
                                    else {
                                        // Second Number correct, naked pair found
                                        for (int j = 0; j < fieldSize; j++) {
                                            if ((col != firstCol) && (col != j)) {
                                                solverField.remPossibleNumber(pair[0][0], j, row);
                                                solverField.remPossibleNumber(pair[0][1], j, row);
                                            }
                                        }
                                    }    
                                }
                            }

			    if (pairNumbers++ > 1) {
                                //error
                            }
			}
		    }
                    pairCount++;
		}
	    }
	}
	return result;
    }

    private boolean fillSinglesRows() {
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
