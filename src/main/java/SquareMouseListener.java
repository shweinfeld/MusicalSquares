import java.awt.event.MouseEvent;

public class SquareMouseListener implements java.awt.event.MouseListener{

    private final Squares squares;


    public SquareMouseListener(Squares squares) {
        this.squares = squares;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = e.getX();
        int col = e.getY();
        int rowIndex = row / SquaresView.BORDERED_CELL_SIZE;
        int colIndex = col/ SquaresView.BORDERED_CELL_SIZE;

        squares.setCell(rowIndex, colIndex, !squares.getCell(rowIndex, colIndex));
        e.getComponent().repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
