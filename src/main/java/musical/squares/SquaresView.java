package musical.squares;

import javax.swing.*;
import java.awt.*;

public class SquaresView extends JComponent {
    public static final int CELL_SIZE = 21;
    public static final int BORDERED_CELL_SIZE = 24;
    private static final Color ON_COLOR = Color.cyan;
    private  Color OFF_COLOR = Color.lightGray;
    private final Squares squares;
    private int stanza = 0;

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
                g.setColor(squares.getCell(i,j)? ON_COLOR : OFF_COLOR);
                g.fillRoundRect(i * BORDERED_CELL_SIZE, j * BORDERED_CELL_SIZE, CELL_SIZE, CELL_SIZE, 10, 10);
                repaint();
            }
        }

    }

    public void setOffColor(int row){
        if(row == squares.getStanza() - 1){
            OFF_COLOR = Color.blue;
        }
        else { OFF_COLOR = Color.lightGray;}
    }

    public void resetOffColor(int row){
        OFF_COLOR = Color.lightGray;
        repaint();
    }
}


