package musical.squares;

import javax.swing.*;
import java.awt.*;

public class SquaresView extends JComponent {
    public static final int CELL_SIZE = 21;
    public static final int BORDERED_CELL_SIZE = 24;
    private static final Color ON_COLOR = Color.cyan;
    private  Color deadCellsColor = Color.lightGray;
    private final Squares squares;

    public SquaresView(Squares squares) {
        this.squares = squares;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintOnStatus(g);

    }

    public Squares getSquares() {
        return squares;
    }

    private void paintOnStatus(Graphics g) {

        for (int i = 0; i < Squares.ROW; i++) {
            for (int j = 0; j < Squares.COL; j++) {
                setOffColor(i);
                g.setColor(squares.getCell(i,j)? ON_COLOR : deadCellsColor);
                g.fillRoundRect(i * BORDERED_CELL_SIZE, j * BORDERED_CELL_SIZE, CELL_SIZE, CELL_SIZE, 10, 10);

            }
        }
    }

    public void setOffColor(int row){
        deadCellsColor = (row == squares.getStanza() - 1)? Color.BLUE : Color.LIGHT_GRAY;
    }

}


