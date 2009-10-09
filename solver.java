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
        int maxIterations = 15;
	
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

            findSinglesRows();
            findSinglesCols();
            findSinglesBox();
            findNakedPairsRow();

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

    private void cleanRows() {
        boolean result = false;
	if (verbose)
	    System.out.println("cleanRows()");
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


    private void cleanColumns() {
	if (verbose)
	    System.out.println("cleanColumns()");
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
	if (verbose)
	    System.out.println("cleanBox()");
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

    private boolean findNakedPairsRow() {
	boolean result = false;
	int[][] pair = new int[2][2];
        int pairCount;
        boolean correctPair = true;
        int firstCol = 0;
        int firstRow = 0; 

	if (verbose)
	    System.out.println("findNakedPairsRow()");

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
                                        // Remove possible numbers from other squares
                                        for (int j = 0; j < fieldSize; j++) {
                                            if ((firstCol != j) && (col != j)) {
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

    private boolean findSinglesRows() {
	boolean result = false;
        int singleCol = 0; 
        int singleRow = 0; 
        int singles = 0;

	if (verbose)
	    System.out.println("findSinglesRows()");
	boolean values[] = new boolean[10];

        for (int row = 0; row < fieldSize; row++) {
            // Check each number...
            for (int number = 1; number < fieldSize+1; number++) {
                singles = 0;
                // ...in each column.
                for (int col = 0; col < fieldSize; col++) {
                    // If square is not set, check if the number is left in square
                    if (solverField.getPossibleNumber(number , col, row)) {
                        singleCol = col;
                        singleRow = row;
                        singles++;
                    }
                }
                if (singles == 1) {
                    solverField.setValue(number, singleCol, singleRow);
                    result = true;
                }
            }
	}
	return result;
    };

    private boolean findSinglesCols() {
	boolean result = false;
        int singleCol = 0; 
        int singleRow = 0; 
        int singles = 0;

	if (verbose)
	    System.out.println("findSinglesCols()");
	boolean values[] = new boolean[10];

        for (int col = 0; col < fieldSize; col++) {
            // Check each number...
            for (int number = 1; number < fieldSize+1; number++) {
                singles = 0;
                // ...in each row.
                for (int row = 0; row < fieldSize; row++) {
                    // If square is not set, check if the number is left in square
                    if (solverField.getPossibleNumber(number , col, row)) {
                        singleCol = col;
                        singleRow = row;
                        singles++;
                    }
                }
                if (singles == 1) {
                    solverField.setValue(number, singleCol, singleRow);
                    result = true;
                }
            }
	}
	return result;
    };

    protected boolean findSinglesBox() {
	if (verbose)
	    System.out.println("findSinglesBox()");
        int singleCol = 0;
        int singleRow = 0;
        int singles;
        boolean result = false;

        // For each box (3 x 3)...
        for (int YBox = 0; YBox < fieldSize/3; YBox++) {
            for (int XBox = 0; XBox < fieldSize/3; XBox++) {
        
                // ...check each number...
                for (int number = 1; number < fieldSize+1; number++) {
                    singles = 0;

                    // ...in the box column...
                    for (int boxCol = 0; boxCol < fieldSize / 3; boxCol++) {

                        // ...and in each row.
                        for (int boxRow = 0; boxRow < fieldSize / 3; boxRow++) {
                            // If square is not set, check if the number is left in square
                            if (solverField.getPossibleNumber(number , boxCol + XBox * 3, boxRow + YBox * 3)) {
                                singleCol = boxCol + XBox * 3;
                                singleRow = boxRow + YBox * 3;
                                singles++;
                            }
                        }
                    }
                    if (singles == 1) {
                        solverField.setValue(number, singleCol, singleRow);
                        result = true;
                    }
	        }
            }
        }
        return result;
    }

    private boolean verbose = false;
    protected field solverField = new field();
    protected int fieldSize;
}
