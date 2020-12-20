package musical.squares;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;

public class SquaresFrame extends JFrame implements ItemListener {


    private final Squares SQUARES;
    private final SquaresView VIEW;
    private final JPanel PLAY_BUTTONS_PANEL = new JPanel(new GridLayout(1, Squares.ROW));
    private final JPanel INSTRUCTION_PANEL = new JPanel(new GridLayout(1, Squares.ROW));
    private final JPanel SQUARES_AND_PLAYS = new JPanel(new BorderLayout());
    private Box UIControlPanel;
    private JComboBox<Scale> scaleOptions;
    private JComboBox<Instrument> instrumentOptions;
    private JLabel scaleLabel;
    private JLabel instrumentLabel;
    private final int DELAY = 200;

    private final int delay = 200;

    boolean playing = false;
    private final ButtonGroup CONTROL_BUTTONS = new ButtonGroup();
    private final String PLAY_ICON_IMAGE = "icons8-circled-play-64.png";


    public SquaresFrame(SquareMouseListener listener, SquaresView view) {

        this.SQUARES = view.getSquares();
        this.VIEW = view;

        setSize(1175, 380);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Musical Squares");
        setLayout(new BorderLayout());

        setUpControlPanel();

        setUpInstructionPanel();
        setUpSquaresAndPlays(listener, view);

        add(SQUARES_AND_PLAYS);
        add(UIControlPanel, BorderLayout.EAST);
    }

    private void setUpSquaresAndPlays(SquareMouseListener listener, SquaresView view) {
        SQUARES_AND_PLAYS.setBackground(Color.WHITE);

        createPlayStanzaButtons();
        view.addMouseListener(listener);
        SQUARES_AND_PLAYS.add(INSTRUCTION_PANEL, BorderLayout.NORTH);
        SQUARES_AND_PLAYS.add(PLAY_BUTTONS_PANEL, BorderLayout.SOUTH);
        SQUARES_AND_PLAYS.add(view, BorderLayout.CENTER);
    }

    private void setUpInstructionPanel() {
        JTextArea welcome = new JTextArea("Welcome to Musical Squares! To play, click squares along the grid. Select a scale " +
                "and an instrument from the select buttons. \nHit play at the bottom of each column to play a single column or hit play on the right" +
                " to play all the stanzas in a row.");
        welcome.setLineWrap(true);
        INSTRUCTION_PANEL.add(welcome);
    }

    private void setUpControlPanel() {
        Dimension panelSize = new Dimension(200, 80);
        sizePanel(panelSize);
        UIControlPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        UIControlPanel.add(createFiller(20, 30));

        setUpControlButtons();
        setUpScaleControl();
        setUpInstrumentControl();

        addComponentsToControlPanel();
    }

    private void addComponentsToControlPanel() {
        UIControlPanel.add(scaleLabel);
        UIControlPanel.add(scaleOptions);
        UIControlPanel.add(createFiller(20, 20));
        UIControlPanel.add(instrumentLabel);
        UIControlPanel.add(instrumentOptions);
        UIControlPanel.add(createFiller(20, 20));
    }

    private void setUpInstrumentControl() {
        instrumentOptions = new JComboBox<>(Instrument.values());
        UIControlPanel.add(createFiller(20, 20));
        instrumentOptions = new JComboBox<>(Instrument.values());
        instrumentOptions.addItemListener(this);
        instrumentLabel = new JLabel("Choose Instrument:");
        instrumentLabel.setAlignmentX(UIControlPanel.getAlignmentX());
    }

    private void setUpScaleControl() {
        scaleOptions = new JComboBox<>(Scale.values());
        scaleOptions.addItemListener(this);
        scaleLabel = new JLabel("Choose Scale:");
        scaleLabel.setAlignmentX(UIControlPanel.getAlignmentX());
    }

    private void setUpControlButtons() {
        Dimension buttonSize = new Dimension(100, 30);
        setUpPlayButton(buttonSize);
        UIControlPanel.add(createFiller(20, 20));

        setUpStopButton(buttonSize);
        UIControlPanel.add(createFiller(20, 20));

        setUpClearButton(buttonSize);
        UIControlPanel.add(createFiller(20, 20));
    }

    private void setUpClearButton(Dimension buttonSize) {
        JButton clear;
        CONTROL_BUTTONS.add(clear = createButton("Clear", buttonSize));
        clear.addActionListener(ActionEvent -> clearNotes());
        clear.setAlignmentX(UIControlPanel.getAlignmentX());
        UIControlPanel.add(clear);
    }

    private void setUpStopButton(Dimension buttonSize) {
        JButton stop;
        CONTROL_BUTTONS.add(stop = createButton("Stop", buttonSize));
        stop.addActionListener(ActionEvent -> stopPlaying());
        stop.setAlignmentX(UIControlPanel.getAlignmentX());
        UIControlPanel.add(stop);
    }

    private void setUpPlayButton(Dimension buttonSize) {
        JButton play;
        CONTROL_BUTTONS.add(play = createButton("Play", buttonSize));
        play.addActionListener(ActionEvent -> playNotes());
        play.setAlignmentX(UIControlPanel.getAlignmentX());
        UIControlPanel.add(play);
    }

    private void sizePanel(Dimension panelSize) {
        UIControlPanel = Box.createVerticalBox();
        UIControlPanel.setPreferredSize(panelSize);
        UIControlPanel.setMaximumSize(panelSize);
        UIControlPanel.setMinimumSize(panelSize);
    }

    private void createPlayStanzaButtons() {
        for (int j = 0; j < Squares.ROW; j++) {
            JButton playStanzaButton = new JButton();
            playStanzaButton.setPreferredSize(new Dimension(SquaresView.CELL_SIZE, SquaresView.CELL_SIZE));
            URL imageUrl = ClassLoader.getSystemResource(PLAY_ICON_IMAGE);
            ImageIcon playIcon = new ImageIcon(new ImageIcon(imageUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            playStanzaButton.setIcon(playIcon);
            int stanza = j;
            playStanzaButton.addActionListener(ActionEvent -> {

                playColumn(stanza);
                VIEW.repaint();

            });
            PLAY_BUTTONS_PANEL.add(playStanzaButton);
        }
    }


    private void playColumn(int stanza) {
        SQUARES.playStanza(stanza);
        SQUARES.setStanza(stanza + 1);
    }

    private void stopPlaying() {
        playing = false;
        SQUARES.setStanza(0);
    }

    private void clearNotes() {
        SQUARES.clearSquares();
        VIEW.repaint();
    }

    private void playNotes() {
        if (playing) {
            return;
        }
        playing = true;
        SQUARES.setStanza(0);
        VIEW.repaint();
        Thread thread = new Thread(() -> {
            while (playing) {
                SQUARES.playNextLine();
                delaySound();
                VIEW.repaint();
            }
        });
        thread.start();
    }

    private void delaySound() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        Dimension prefSize = new Dimension(minSize);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, height);
        return (new Box.Filler(minSize, prefSize, maxSize));
    }


    @Override
    public void itemStateChanged(ItemEvent event) {

        if (event.getSource() == scaleOptions && scaleOptions.getSelectedItem() != null) {
            SQUARES.changeScales((Scale) scaleOptions.getSelectedItem());
        }
        if (event.getSource() == instrumentOptions && instrumentOptions.getSelectedItem() != null) {
            SQUARES.changeInstrument((Instrument) instrumentOptions.getSelectedItem());

        }
    }
}