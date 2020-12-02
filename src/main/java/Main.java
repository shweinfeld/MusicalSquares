public class Main {

    public static void main(String[] args) {
        Squares squares = new Squares();
        SquaresView view = new SquaresView(squares);
        SquareMouseListener listener = new SquareMouseListener(squares);
        SquaresFrame frame = new SquaresFrame(listener, view);
        frame.setVisible(true);
    }
}
