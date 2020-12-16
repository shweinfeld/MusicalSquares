package musical.squares;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiUnavailableException;

//Resources:
//https://github.com/andrewoid/InteractivePiano
//https://github.com/shweinfeld/Conways-Game-of-Life
//https://github.com/irshadshalu/music-grid

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