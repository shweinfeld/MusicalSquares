package musical.squares;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.util.Objects;

public class SquaresFrame extends JFrame implements ItemListener {


    private final Squares squares;
    private final SquaresView view;
    private final JPanel playButtonsPanel;
    private final JComboBox<Scales> scaleOptions;
    private final JComboBox<Instrument> instrumentOptions;
    private final int delay = 200;
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

        Box UIControlPanel = Box.createVerticalBox();
        Dimension sizePanel = new Dimension(200, 80);
        UIControlPanel.setPreferredSize(sizePanel);
        UIControlPanel.setMaximumSize(sizePanel);
        UIControlPanel.setMinimumSize(sizePanel);
        Dimension size = new Dimension(100, 30);
        UIControlPanel.add(createFiller(20, 30));
        JButton play;
        buttons.add(play = createButton("Play", size));
        play.setAlignmentX(UIControlPanel.getAlignmentX());
        UIControlPanel.add(play);
        UIControlPanel.add(createFiller(20, 20));
        JButton stop;
        buttons.add(stop = createButton("Stop", size));
        stop.setAlignmentX(UIControlPanel.getAlignmentX());
        UIControlPanel.add(stop);
        UIControlPanel.add(createFiller(20, 20));
        JButton clear;
        buttons.add(clear = createButton("Clear", size));
        clear.setAlignmentX(UIControlPanel.getAlignmentX());
        UIControlPanel.add(clear);
        UIControlPanel.add(createFiller(20, 20));

        scaleOptions = new JComboBox<>(Scale.values());
        scaleOptions.addItemListener(this);
        JLabel scaleLabel = new JLabel("Choose Scale:");
        scaleLabel.setAlignmentX(UIControlPanel.getAlignmentX());
        UIControlPanel.add(scaleLabel);
        UIControlPanel.add(scaleOptions);
        UIControlPanel.add(createFiller(20, 20));
        instrumentOptions = new JComboBox<>(Instrument.values());
        instrumentOptions.addItemListener(this);
        JLabel instrumentLabel = new JLabel("Choose Instrument:");
        instrumentLabel.setAlignmentX(UIControlPanel.getAlignmentX());
        UIControlPanel.add(instrumentLabel);
        UIControlPanel.add(instrumentOptions);
        UIControlPanel.add(createFiller(20, 20));

        Border blackline = BorderFactory.createLineBorder(Color.black);
        UIControlPanel.setBorder(blackline);

        playButtonsPanel = new JPanel(new GridLayout(1, Squares.ROW));
        JPanel instructionPanel = new JPanel(new GridLayout(1, Squares.ROW));
        JTextArea welcome = new JTextArea("Welcome to Musical Squares! To play, click squares along the grid. Select a scale " +
                "and an instrument from the select buttons. \nHit play at the bottom of each column to play a single column or hit play on the right" +
                " to play all the stanzas in a row.");
        welcome.setLineWrap(true);
        welcome.setEditable(false);
        instructionPanel.add(welcome);
        JPanel viewAndButtons = new JPanel(new BorderLayout());
        viewAndButtons.setBackground(Color.WHITE);
        stop.addActionListener(ActionEvent -> stopPlaying());
        clear.addActionListener(ActionEvent -> clearNotes());
        play.addActionListener(ActionEvent -> playNotes());
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
            URL imageUrl = ClassLoader.getSystemResource("icons8-circled-play-64.png");
            ImageIcon playIcon = new ImageIcon(new ImageIcon(imageUrl).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            playStanzaButton.setIcon(playIcon);
            int row = j;
            playStanzaButton.addActionListener(ActionEvent -> {
                playColumn(row);
                view.repaint();
            });
            playButtonsPanel.add(playStanzaButton);
        }
    }

    private void playColumn(int row)  {
        squares.playStanza(row);
        squares.setStanza(row + 1);
    }

    private void stopPlaying() {
        playing = false;
        squares.setStanza(0);
    }

    private void clearNotes() {
        squares.clearSquares();
        view.repaint();
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

            squares.changeScales((Scale) scaleOptions.getSelectedItem());
        }
        if (event.getSource() == instrumentOptions) {
            squares.changeInstrument((Instrument) Objects.requireNonNull(instrumentOptions.getSelectedItem()));
        }
    }
}