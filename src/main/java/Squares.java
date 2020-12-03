public class Squares {
    public final static int ROW = 40;
    public final static int COL = 12;
    private boolean[][] squaresArray = new boolean[ROW][COL];

    public Squares(){}

    public void play() {
        for(int row = 0; row< ROW; row ++) {
            for(int col = 0; col<COL; col ++){
                //if cell is true
                //get note[column index]
                //add to music player
            }
            //play those notes
            //turn off those notes (optional maybe)
        }
    }


    public void clearSquares() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                squaresArray[i][j] = false;
            }
        }
    }

    public boolean getCell(int row, int col) {
        return squaresArray[row][col];
    }

    public void setCell(int row, int col, boolean value) {
        squaresArray[row][col] = value;
    }
}
