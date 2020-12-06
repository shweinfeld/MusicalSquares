import javax.swing.*;
import java.awt.*;

public class SquaresFrame extends JFrame {

    private Squares squares;
    private SquaresView view;
    private JButton play;
    private JButton clear;
    private JButton stop;
    private JPanel playButtonsPanel;
    private JPanel viewAndButtons;
    private JPanel UIControlPanel;
    private int delay = 200;
    boolean playing = false;

    public SquaresFrame(SquareMouseListener listener, SquaresView view) {
        this.squares = view.getSquares();
        this.view = view;

        setSize(1000, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Musical Squares");
        setLayout(new GridLayout(2,1));
        play = new JButton("Play");
        clear = new JButton("Clear");
        stop = new JButton("Stop");
        playButtonsPanel = new JPanel(new GridLayout(1, squares.COL));
        UIControlPanel = new JPanel(new GridLayout(1, 3));
        viewAndButtons = new JPanel(new BorderLayout());

        stop.addActionListener(ActionEvent -> stopPlaying());
        clear.addActionListener(ActionEvent -> squares.clearSquares());
        play.addActionListener(ActionEvent -> playNotes());

        for (int j = 0; j < Squares.COL; j++) {
                JButton playStanzaButton = new JButton();
                ImageIcon playIcon = new ImageIcon("icons8-circled-play-64.png");
                playStanzaButton.setIcon(playIcon);

                int finalStanza = j;
                playStanzaButton.addActionListener(ActionEvent -> squares.playStanza(finalStanza));
                playButtonsPanel.add(playStanzaButton);
            }

        view.addMouseListener(listener);
        viewAndButtons.add(playButtonsPanel, BorderLayout.NORTH);
        viewAndButtons.add(view, BorderLayout.CENTER);
        add(viewAndButtons);


        UIControlPanel.add(play);
        UIControlPanel.add(clear);
        UIControlPanel.add(stop);
        add(UIControlPanel);

    }

    private void stopPlaying() {
        playing = false;
        squares.setStanza(0);
    }

    private void playNotes() {
        playing = true;
        Thread thread = new Thread(() -> {
            while (playing) {
                squares.playNextLine();
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
