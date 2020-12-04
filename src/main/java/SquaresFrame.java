import javax.swing.*;
import java.awt.*;

public class SquaresFrame extends JFrame {
    Squares squares;
    SquaresView view;
    JButton play;
    boolean playing = false;

    public SquaresFrame(SquareMouseListener listener, SquaresView view) {
        this.squares = view.getSquares();
        this.view = view;

        setSize(690, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Musical Squares");
        setLayout(new BorderLayout());
        play = new JButton("Play");
        play.addActionListener(ActionEvent -> playNotes());
        add(play, BorderLayout.SOUTH);

        view.addMouseListener(listener);
        add(view, BorderLayout.CENTER);
    }

    private void playNotes() {
        if (!playing) {
            Thread thread = new Thread(() -> {
                playing = true;
                squares.play();
                playing = false;
            });
            thread.start();
        }
    }
}
