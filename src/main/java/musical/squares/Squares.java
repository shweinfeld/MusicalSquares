package musical.squares;

import javax.sound.midi.*;

public class Squares {
    public final static int ROW = 40;
    public final static int COL = 12;
    private final boolean[][] squaresArray = new boolean[ROW][COL];
    private int[] scale = Scale.CHROMATIC.getScale();
    private final MidiChannel midiChannel;
    private int pitch;
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

    public void changeScales(Scale updatedScale){
        scale = updatedScale.getScale();
    }

    public void changeInstrument(Instrument updatedInstrument) {
        midiChannel.programChange( updatedInstrument.getInstrument());
    }

    public void playStanza(int row){
        for (int stanza = 0; stanza < COL; stanza++) {
            if (squaresArray[row][stanza]) {
                pitch = scale[stanza];
                SoundThread soundThread = new SoundThread(pitch, midiChannel); // pass in pitch to play
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

    public boolean toggleCell(int row, int col){
        squaresArray[row][col] = !squaresArray[row][col];
        return squaresArray[row][col];
    }
}
