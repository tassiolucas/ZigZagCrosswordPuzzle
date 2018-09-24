package estudio_sol.zig_zag_crossword_puzzle.model;

public class Label {

    String letter;

    public Label(String latter) {
        this.letter = latter;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
