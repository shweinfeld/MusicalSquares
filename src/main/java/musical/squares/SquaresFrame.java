package musical.squares;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SquaresFrame extends JFrame implements ItemListener {

    private Squares squares;
    private SquaresView view;
    private JButton play;
    private JButton clear;
    private JButton stop;
    private JPanel playButtonsPanel;
    private JPanel viewAndButtons;
    private JPanel UIControlPanel;
    private JComboBox<Scales> scaleOptions;
    private JComboBox<Instruments> instrumentOptions;
    private int delay = 200;
    boolean playing = false;

    public SquaresFrame(SquareMouseListener listener, SquaresView view) {
        this.squares = view.getSquares();
        this.view = view;

        setSize(692, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Musical musical.squares.Squares");
        setLayout(new GridLayout(2, 1));
        play = new JButton("Play");
        clear = new JButton("Clear");
        stop = new JButton("Stop");

        playButtonsPanel = new JPanel(new GridLayout(1, Squares.ROW));
        UIControlPanel = new JPanel(new GridLayout(1, 5));
        scaleOptions = new JComboBox<>(Scales.values());
        scaleOptions.addItemListener(this);

        instrumentOptions = new JComboBox<>(Instruments.values());
        instrumentOptions.addItemListener(this);

        viewAndButtons = new JPanel(new BorderLayout());

        stop.addActionListener(ActionEvent -> stopPlaying());
        clear.addActionListener(ActionEvent -> squares.clearSquares());
        play.addActionListener(ActionEvent -> playNotes());

        //might want to separate out into a separate function
        createPlayStanzaButtons();

        view.addMouseListener(listener);
        viewAndButtons.add(playButtonsPanel, BorderLayout.NORTH);
        viewAndButtons.add(view, BorderLayout.CENTER);
        add(viewAndButtons);


        UIControlPanel.add(play);
        UIControlPanel.add(clear);
        UIControlPanel.add(stop);
        UIControlPanel.add(scaleOptions);
        UIControlPanel.add(instrumentOptions);
        add(UIControlPanel);

    }


    private void createPlayStanzaButtons() {
        for (int j = 0; j < Squares.ROW; j++) {
            JButton playStanzaButton = new JButton();
            playStanzaButton.setPreferredSize(new Dimension(SquaresView.CELL_SIZE, SquaresView.CELL_SIZE));
            ImageIcon playIcon = new ImageIcon(new ImageIcon("icons8-circled-play-64.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            playStanzaButton.setIcon(playIcon);

            int finalStanza = j;
            playStanzaButton.addActionListener(ActionEvent -> playColumn(finalStanza));
            playButtonsPanel.add(playStanzaButton);
        }
    }

    private void playColumn(int finalStanza) {
        squares.playStanza(finalStanza);
        squares.setStanza(finalStanza + 1);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //this line removes the highlighting effect. not sure why
        squares.setStanza(0);

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
                //view.repaint();
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void itemStateChanged(ItemEvent event) {

        if (event.getSource() == scaleOptions) {
            squares.changeScales((Scales) scaleOptions.getSelectedItem());
        }
        if (event.getSource() == instrumentOptions) {
            squares.changeInstrument((Instruments) instrumentOptions.getSelectedItem());
        }
    }
}
