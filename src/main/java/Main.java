import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiUnavailableException;

public class Main {

    public static void main(String[] args) throws MidiUnavailableException {
        MidiChannel midiChannel = new SoundSystem().getSoundSystem();
        Squares squares = new Squares(midiChannel);
        SquaresView view = new SquaresView(squares);
        SquareMouseListener listener = new SquareMouseListener(squares);
        SquaresFrame frame = new SquaresFrame(listener, view);
        frame.setVisible(true);
    }
}
