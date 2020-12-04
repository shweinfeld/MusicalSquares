import javax.swing.*;
import java.awt.*;

public class SquaresFrame extends JFrame {
    private Squares squares;
    private SquaresView view;
    private JButton play;
    private JButton clear;
    private int delay = 200;
    public SquaresFrame(SquareMouseListener listener, SquaresView view) {
        this.squares = view.getSquares();
        this.view = view;

        setSize(690, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Musical Squares");
        setLayout(new BorderLayout());
        play = new JButton("Play");
        clear = new JButton("Clear");
        clear.addActionListener(ActionEvent -> squares.clearSquares());
        play.addActionListener(ActionEvent -> playNotes());
        add(play, BorderLayout.EAST);
        add(clear, BorderLayout.WEST);
        view.addMouseListener(listener);
        add(view, BorderLayout.CENTER);
    }

    private void playNotes() {

        Thread thread = new Thread(() -> {
            while (true) {
                squares.playPiano();
                view.repaint();
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
