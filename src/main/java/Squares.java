public class Squares {
    public final static int ROW = 40;
    public final static int COL = 12;
    private boolean[][] squaresArray = new boolean[ROW][COL];

    public Squares(){}


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
