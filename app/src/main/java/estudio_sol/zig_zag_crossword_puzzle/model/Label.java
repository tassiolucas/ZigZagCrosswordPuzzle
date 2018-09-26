package estudio_sol.zig_zag_crossword_puzzle.model;

public class Label {
    // Classe que instancia-rá os itens do Caça-palavras, suas marcações na matriz e suas letras
    String letter;
    boolean marked = false;

    public Label(String latter) {
        this.letter = latter;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }
}
