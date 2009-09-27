/**
 * Head class for a sudoku.
 */

class sudoku {

    public static void main(String[] args) {

	// Check input arguments if any
	if (args.length != 0) {
	    checkArgs(args);
	}

	System.out.println("Sudoku solver version 0.1\n");

	//sudokuField.drawFieldCorrect();

	solver solverSudoku = new solver(sudokuField, size, verbose);

	solverSudoku.solve();
    }

    protected static void checkArgs(String[] args) {
	
	String argument = args[0];
	int argStep = 0;
        int argCnt = args.length; 
	System.out.println("checkArgs "+argCnt);

        while (argStep < argCnt) {
	if (args[argStep].equals("-v")) {
	    verbose = true;
	    argStep++;
	}
	else if (args[argStep].equals("-size")) {
	    size = Integer.parseInt(args[argStep+1]);
	    argStep=argStep+2;
	}
	else if (args[argStep].equals("-set")) {
	    if (args[argStep+1].length() != size*size) {
                //Error case
                System.out.println("Wrong amount of arguments for -set, should be "+size*size);
		return;
	    }
            // Initiate position counter for the number array
	    int pos = 0;

	    for (int i = 0; i < size; i++)
	        {
		    for (int j = 0; j < size; j++)
			{
                            // Pick a number from the number array and put in a square
			    sudokuField.setValue(Character.getNumericValue(args[argStep+1].charAt(pos)),j,i);
			    pos++;
			}
		}
            argStep=argStep+2;
	}
        }
    }

    // Attributes
    private static int size = 9;
    private static field sudokuField = new field();
    private static Boolean verbose = false;
}
