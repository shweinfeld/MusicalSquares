import javax.swing.*;
import java.awt.*;

public class SquaresView extends JComponent {
    public static final int CELL_SIZE = 15;
    public static final int BORDERED_CELL_SIZE = 17;
    private static final Color ON_COLOR = Color.cyan;
    private static final Color OFF_COLOR = Color.gray;
    private final Squares squares;

    public SquaresView(Squares squares) {
        this.squares = squares;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintLifeStatus(g);

    }

    public Squares getSquares() {
        return squares;
    }

    private void paintLifeStatus(Graphics g) {

        for (int i = 0; i < Squares.ROW; i++) {
            for (int j = 0; j < Squares.COL; j++) {
                g.setColor(squares.getCell(i,j)? ON_COLOR : OFF_COLOR);
                g.fillRect(i * BORDERED_CELL_SIZE, j * BORDERED_CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }
}


