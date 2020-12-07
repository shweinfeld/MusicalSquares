package musical.squares;

import javax.sound.midi.*;

public class Squares {
    public final static int ROW = 40;
    public final static int COL = 12;
    private boolean[][] squaresArray = new boolean[ROW][COL];
    private int[] scale = Scales.CHROMATIC.getScale();
    private MidiChannel midiChannel;
    private int pitch;
    private SoundThread soundThread;
    private int stanza = 0;

    public Squares(MidiChannel midiChannel) {
        this.midiChannel = midiChannel;
    }

    public void playNextLine() {
        if (stanza == ROW) {
            stanza = 0;
        }
        playStanza(stanza);
        stanza++;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void changeScales(Scales updatedScale){
        scale = updatedScale.getScale();
    }
    public void playStanza(int stanza){
        for (int col = 0; col < COL; col++) {
            if (squaresArray[stanza][col]) {
                pitch = scale[col];
                soundThread = new SoundThread(pitch, midiChannel); // pass in pitch to play
                soundThread.start();
            }
        }
    }
    public void setStanza(int col) {

        this.stanza = col;
    }
    public int getStanza(){ return this.stanza;}

    public void playNote(int index){
        pitch = scale[index];
        SoundThread s = new SoundThread(pitch, midiChannel); // pass in pitch to play
        s.start();
    }

    public void clearSquares() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                squaresArray[i][j] = false;
            }
        }
    }

    public boolean getCell(int row, int col) {
        return squaresArray[row][col];
    }

    public void setCell(int row, int col, boolean value) {
        squaresArray[row][col] = value;
    }
}
