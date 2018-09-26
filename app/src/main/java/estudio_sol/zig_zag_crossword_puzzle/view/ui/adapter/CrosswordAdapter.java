package estudio_sol.zig_zag_crossword_puzzle.view.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.sol.estudo.zigzagcrosswordpuzzle.R;
import com.sol.estudo.zigzagcrosswordpuzzle.databinding.LetterAdapterDataBinding;
import java.util.List;
import estudio_sol.zig_zag_crossword_puzzle.model.Label;

public class CrosswordAdapter extends RecyclerView.Adapter<CrosswordAdapter.CrosswordViewHolder> {

    private List<? extends Label> crosswords;
    private Context context;

    // Inicializa o adapter que será responsável pelos itens dinâmicos na tela do Caça-palavras
    public CrosswordAdapter(final Context context, final List<? extends Label>  crosswords) {
        this.context = context;
        this.crosswords = crosswords;
    }

    // Combina os itens dinâmicos com o item de exemplo que será reutilizado no Caça-palavras
    @Override
    public CrosswordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LetterAdapterDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_item_letter,
                parent,
                false);

        return new CrosswordViewHolder(binding);
    }

    // Método que gira e faz a combinação dos componentes dinâmicos da tela com os objetos
    // inicializados que popularão esses mesmos componetes da tela
    @Override
    public void onBindViewHolder(CrosswordViewHolder holder, int position) {
        holder.binding.setLetter(crosswords.get(position));

        holder.binding.labelText.setText(crosswords.get(position).getLetter());

        if (crosswords.get(position).isMarked()) {
            holder.binding.labelText.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            holder.binding.labelText.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.binding.labelText.setTypeface(null, Typeface.BOLD);
        }
    }

    // Método que atualiza os itens dinâmicos do adapter
    public void setCrosswordsList(final List<? extends Label> crosswordsList) {
        this.crosswords = crosswordsList;
        notifyDataSetChanged();
    }

    // Método que conta quantos itens serão populados dinâmicamente no adapter
    @Override
    public int getItemCount() {
        return crosswords.size();
    }

    // Classe que mantém salvos os itens dinâmicos criados
    class CrosswordViewHolder extends RecyclerView.ViewHolder {
        public final LetterAdapterDataBinding binding;

        public CrosswordViewHolder(LetterAdapterDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
