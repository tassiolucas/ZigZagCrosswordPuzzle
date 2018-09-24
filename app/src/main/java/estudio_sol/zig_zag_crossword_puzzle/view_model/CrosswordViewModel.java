package estudio_sol.zig_zag_crossword_puzzle.view_model;

import android.content.Context;
import android.databinding.BaseObservable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import estudio_sol.zig_zag_crossword_puzzle.model.Label;

public class CrosswordViewModel extends BaseObservable {

    private Context context;

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

    public boolean getFilter(final String query) {

        String queryUpperCase = query.toUpperCase();
        List<String> searchItens = Arrays.asList(queryUpperCase.split("\\s*,\\s*"));
        char[] itens = searchItens.get(0).split(" ")[0].toCharArray();

//        int count = 0;
//        for (int line = 0; line < 10; line++) {
//            for (int column = 0; column < 10; column++) {
//                if (matrixLetters[line][column].getLetter() == searchItens.get(count)){
//
//                }
//                count++;
//            }
//        }

        return false;
    }

    public List<Label> getLabels() {
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
}
