package musical.squares;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SquaresFrame extends JFrame implements ItemListener {

    private Squares squares;
    private SquaresView view;
    private JButton play;
    private JButton clear;
    private JButton stop;
    private JPanel playButtonsPanel;
    private JPanel instructionPanel;
    private JPanel viewAndButtons;
    private Box UIControlPanel;
    private JComboBox<Scales> scaleOptions;
    private JComboBox<Instruments> instrumentOptions;
    private JLabel scaleLabel;
    private JLabel instrumentLabel;
    private int delay = 200;
    boolean playing = false;
    ButtonGroup buttons = new ButtonGroup();


    public SquaresFrame(SquareMouseListener listener, SquaresView view) {

        this.squares = view.getSquares();
        this.view = view;

        setSize(1175, 380);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Musical musical.squares.Squares");
        setLayout(new GridLayout(2, 1));
        setResizable(false);

        setTitle("Musical Squares");
        setLayout(new BorderLayout());

        UIControlPanel = Box.createVerticalBox();
        Dimension sizePanel = new Dimension(200, 80);
        UIControlPanel.setPreferredSize(sizePanel);
        UIControlPanel.setMaximumSize(sizePanel);
        UIControlPanel.setMinimumSize(sizePanel);
        Dimension size = new Dimension(100, 30);
        UIControlPanel.add(createFiller(20, 30));
        buttons.add(play = createButton("Play", size));
        play.setAlignmentX(UIControlPanel.getAlignmentX());
        UIControlPanel.add(play);
        UIControlPanel.add(createFiller(20, 20));
        buttons.add(stop = createButton("Stop", size));
        stop.setAlignmentX(UIControlPanel.getAlignmentX());
        UIControlPanel.add(stop);
        UIControlPanel.add(createFiller(20, 20));
        buttons.add(clear = createButton("Clear", size));
        clear.setAlignmentX(UIControlPanel.getAlignmentX());
        UIControlPanel.add(clear);
        UIControlPanel.add(createFiller(20, 20));

        scaleOptions = new JComboBox<>(Scales.values());
        scaleOptions.addItemListener(this);
        scaleLabel = new JLabel("Choose Scale:");
        scaleLabel.setAlignmentX(UIControlPanel.getAlignmentX());
        UIControlPanel.add(scaleLabel);
        UIControlPanel.add(scaleOptions);
        UIControlPanel.add(createFiller(20, 20));
        instrumentOptions = new JComboBox<>(Instruments.values());
        instrumentOptions.addItemListener(this);
        instrumentLabel = new JLabel("Choose Instrument:");
        instrumentLabel.setAlignmentX(UIControlPanel.getAlignmentX());
        UIControlPanel.add(instrumentLabel);
        UIControlPanel.add(instrumentOptions);
        UIControlPanel.add(createFiller(20, 20));

        Border blackline = BorderFactory.createLineBorder(Color.black);
        UIControlPanel.setBorder(blackline);

        playButtonsPanel = new JPanel(new GridLayout(1, Squares.ROW));
        instructionPanel = new JPanel(new GridLayout(1, Squares.ROW));
        JTextArea welcome = new JTextArea("Hello! Welcome to the most awesome instrument player!" +
                "click as many boxes as you like to hear a sound. Then choose to play the row or the whole" +
                " sequence. Additionally the scale and instrument can be chosen. Have fun!");
        welcome.setLineWrap(true);
        instructionPanel.add(welcome);



        viewAndButtons = new JPanel(new BorderLayout());
        viewAndButtons.setBackground(Color.WHITE);

        stop.addActionListener(ActionEvent -> stopPlaying());
        clear.addActionListener(ActionEvent -> squares.clearSquares());
        play.addActionListener(ActionEvent -> playNotes());

        //might want to separate out into a separate function
        createPlayStanzaButtons();
        view.addMouseListener(listener);
        viewAndButtons.add(instructionPanel, BorderLayout.NORTH);
        viewAndButtons.add(playButtonsPanel, BorderLayout.SOUTH);
        viewAndButtons.add(view, BorderLayout.CENTER);
        add(viewAndButtons);

        add(UIControlPanel, BorderLayout.EAST);

    }


    private void createPlayStanzaButtons() {
        for (int j = 0; j < Squares.ROW; j++) {
            JButton playStanzaButton = new JButton();
            //playStanzaButton.setBackground(new Color(47, 191, 51));
            playStanzaButton.setPreferredSize(new Dimension(SquaresView.CELL_SIZE, SquaresView.CELL_SIZE));
            ImageIcon playIcon = new ImageIcon(new ImageIcon("icons8-circled-play-64.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            playStanzaButton.setIcon(playIcon);

            int stanza = j;
            playStanzaButton.addActionListener(ActionEvent -> {
                playColumn(stanza);
                view.repaint();
            });
            playButtonsPanel.add(playStanzaButton);
        }
    }

    private void playColumn(int stanza)  {

        squares.playStanza(stanza);
        squares.setStanza(stanza + 1);

    }

    private void stopPlaying() {
        playing = false;
        squares.setStanza(0);
    }

    private void playNotes() {
        if (playing) {
            return;
        }
        playing = true;
        squares.setStanza(0);
        view.repaint();
        Thread thread = new Thread(() -> {
            while (playing) {
                squares.playNextLine();
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                view.repaint();
            }
        });
        thread.start();
    }

    private JButton createButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        return button;
    }

    private Box.Filler createFiller(int width, int height) {
        Dimension minSize = new Dimension(width, height);
        Dimension prefSize = new Dimension(width, height);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, height);
        return (new Box.Filler(minSize, prefSize, maxSize));
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
