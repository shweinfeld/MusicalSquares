import javax.swing.*;
import java.awt.*;

public class SquaresFrame extends JFrame {
    Squares squares;
    SquaresView view;

    public SquaresFrame(SquareMouseListener listener, SquaresView view) {
        this.squares = view.getSquares();
        this.view = view;

        setSize(690, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Musical Squares");
        setLayout(new BorderLayout());

        // play button that calls squares.play

        view.addMouseListener(listener);
        add(view, BorderLayout.CENTER);
    }
}
