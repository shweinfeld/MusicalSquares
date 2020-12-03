import javax.sound.midi.*;

public class Squares {
    public final static int ROW = 40;
    public final static int COL = 11;
    private boolean[][] squaresArray = new boolean[ROW][COL];
    private int[] notesCsharp = {1, 13, 25, 37, 49, 61, 73, 85, 97, 109, 121};
    private MidiChannel midiChannel;
    private int pitch;


    public Squares(MidiChannel midiChannel) {
        this.midiChannel = midiChannel;
    }

    public void play(){
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (squaresArray[row][col]) {
                    pitch = notesCsharp[col];
                    SoundThread s = new SoundThread(pitch, midiChannel); // pass in pitch to play
                    s.start();
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
