package musical.squares;

public enum Scale {
    CHROMATIC(72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83),
    CLASSIC(59, 61, 66, 68, 73, 75, 76, 80, 83, 85, 90, 92),
    PENTATONIC(60, 62, 76, 67, 69, 72, 74, 76, 79, 81, 84, 86),
    MAJOR(60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79),
    HARMONIC_MINOR(69, 71, 72, 76, 76, 77, 80, 81, 83, 84, 86, 88);

    private final int[] scale;

    Scale(int... scale) {
        this.scale = scale;
    }

    public int[] getScale() {
        return this.scale;
    }
}