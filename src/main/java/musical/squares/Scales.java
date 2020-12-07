package musical.squares;

public enum Scales {


    CLASSIC(new int[]{1, 13, 25, 37, 49, 61, 73, 85, 97, 109, 121}),
    PENTATONIC(new int[]{60, 62, 76, 67, 69, 72, 74, 76, 79, 81, 84, 86}),
    CHROMATIC(new int[]{72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83}),
    MAJOR(new int[]{60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79}),
    HARMONIC_MINOR(new int[]{69, 71, 72, 76, 76, 77, 80, 81, 83, 84, 86, 88});

    private int[] scale;

    private Scales(int[] scale) {
        this.scale = scale;
    }

    public int[] getScale() {
        return this.scale;
    }


}
