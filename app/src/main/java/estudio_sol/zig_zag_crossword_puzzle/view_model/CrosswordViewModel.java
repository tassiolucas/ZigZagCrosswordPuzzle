package estudio_sol.zig_zag_crossword_puzzle.view_model;

import android.content.Context;
import android.databinding.BaseObservable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import estudio_sol.zig_zag_crossword_puzzle.model.Label;

public class CrosswordViewModel extends BaseObservable {

    private Context context;

    List<String> index;
    List<String> searchItens;
    String queryUpperCase;
    char[] itens;
    List<String> listIndex;

    private List<String> letters = Arrays.asList(
            "W", "S", "I", "A", "L", "C", "E", "O", "I", "V",
            "V", "A", "L", "P", "A", "L", "H", "E", "T", "A",
            "U", "T", "I", "G", "U", "A", "N", "A", "O", "I",
            "U", "C", "I", "F", "R", "A", "C", "L", "U", "B",
            "A", "L", "C", "O", "G", "E", "E", "U", "V", "R",
            "M", "U", "S", "I", "C", "A", "T", "L", "A", "A",
            "A", "B", "I", "S", "S", "N", "O", "R", "I", "S",
            "N", "P", "A", "L", "C", "O", "A", "H", "B", "E",
            "Z", "M", "P", "T", "R", "E", "S", "J", "R", "L",
            "F", "P", "E", "Q", "T", "A", "M", "L", "O", "J"
    );

    public Label[][] matrixLetters;
    public List<Label> labels;

    public CrosswordViewModel(Context context) {
        this.context = context;
        matrixLetters = new Label[10][10];
        labels = new ArrayList();
    }

    public List<String> getFilter(final String query) {

        String queryUpperCase = query.toUpperCase();

        searchItens = Arrays.asList(queryUpperCase.split("\\s*,\\s*"));
        itens = searchItens.get(0).split(" ")[0].toCharArray();

        searchItens = new ArrayList<>();
        for (char item : itens) {
            searchItens.add(String.valueOf(item));
        }
        
        listIndex = new ArrayList<>();

            for (int line = 0; line < 10; line ++) {
                for (int column = 0; column < 10; column++) {

                    verifyWordRightDown(column, line, itens.length);

                    if (verifyWordRightDown(column, line, itens.length) != null) {
                        listIndex.addAll(verifyWordRightDown(column, line, itens.length));
                    } else if (verifyWordDownRight(column, line, itens.length) != null) {
                        listIndex.addAll(verifyWordDownRight(column, line, itens.length));
                    }

                }
            }

        return listIndex;
    }

    private List<String> verifyWordDownRight(int column, int line, int quantLetter) {
        int step = 0;
        int count1 = 0;
        int count2 = 0;
        index = new ArrayList<>();
        boolean goDown = true;

        do {
            if (column < 10 & line < 10) {
                if (matrixLetters[column][line].getLetter().equals(String.valueOf(itens[step]))) {

                    index.add(String.valueOf(column) + ":" + String.valueOf(line));

                    count1++;
                    count2++;
                } else {
                    break;
                }

                if (goDown == true) {
                    line++;
                    goDown = false;
                } else {
                    column++;
                    goDown = true;
                }

                step++;
            } else {
                break;
            }
        } while (step < quantLetter);

        return index.size() == itens.length ? index : null ;
    }

    private List<String> verifyWordRightDown(int column, int line, int quantLetter) {
        int step = 0;
        int count1 = 0;
        int count2 = 0;
        index = new ArrayList<>();
        boolean goRight = true;

        do {
            if (column < 10 & line < 10) {
                if (matrixLetters[column][line].getLetter().equals(String.valueOf(itens[step]))) {

                    index.add(String.valueOf(column) + ":" + String.valueOf(line));

                    count1++;
                    count2++;
                } else {
                    break;
                }

                    if (goRight == true) {
                        column++;
                        goRight = false;
                    } else {
                        line++;
                        goRight = true;
                    }
                    step++;
            } else {
                break;
            }
        } while (step < quantLetter);

        return index.size() == itens.length ? index : null ;
    }


    public List<Label> getFirstLabels() {
        int count = 0;
        for (int line = 0; line < 10; line++) {
            for (int column = 0; column < 10; column++) {
                matrixLetters[column][line] = new Label(letters.get(count));
                count++;
            }
        }

        count = 0;
        for (String letter : letters) {
            labels.add(new Label(letter));
            count++;
        }

        return labels;
    }
    
    public List<Label> getLabelsAfterSearch(Label[][] matrixLetters) {
        List<Label> labels = new ArrayList<>();

        for (int line = 0; line < 10; line++) {
            for (int column = 0; column < 10; column++) {
               labels.add(matrixLetters[column][line]);
            }
        }
        
        return labels;
    }
}
