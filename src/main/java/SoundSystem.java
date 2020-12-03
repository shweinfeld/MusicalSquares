import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class SoundSystem {

    private MidiChannel midiChannel;

    public SoundSystem() throws MidiUnavailableException {
        // setting up sound
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        midiChannel = synth.getChannels()[0];
    }

    public MidiChannel getSoundSystem()
    {
        return midiChannel;
    }
}
