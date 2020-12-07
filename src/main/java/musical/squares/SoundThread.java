package musical.squares;

import javax.sound.midi.MidiChannel;

public class SoundThread  extends Thread{
    private int pitch;
    private MidiChannel midiChannel;

    public SoundThread(int pitch, MidiChannel midiChannel)
    {
        this.pitch = pitch;
        this.midiChannel = midiChannel;
    }

    @Override
    public void run()
    {
        super.run();
        midiChannel.noteOn(pitch, 80);
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        midiChannel.noteOff(pitch);
    }
}
