package musical.squares;

public enum Instruments {
    ACOUSTIC_GRAND_PIANO(1),
    HARPSICHORD(7),
    GLOCKENSPIEL(10),
    CHURCH_ORGAN(20),
    HARMONICA(23),
    ACOUSTIC_GUITAR(25),
    ACOUSTIC_BASS(33),
    VIOLIN(41),
    CELLO(43),
    HARP(47),
    TRUMPET(57),
    SAX(67),
    FLUTE(74),
    BANJO(106);



    private int instrument;

    private Instruments(int instrument) {
        this.instrument = instrument;
    }

    public int getInstrument() {
        return this.instrument;
    }
}
