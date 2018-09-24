package estudio_sol.zig_zag_crossword_puzzle.view.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
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

    public CrosswordAdapter(final Context context, final List<? extends Label>  crosswords) {
        this.context = context;
        this.crosswords = crosswords;
    }

    @Override
    public CrosswordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LetterAdapterDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_item_letter,
                parent,
                false);

        return new CrosswordViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CrosswordViewHolder holder, int position) {
        Label label = crosswords.get(position);

        holder.binding.labelText.setText(label.getLetter());

        holder.binding.setLetter(label);
    }

    @Override
    public int getItemCount() {
        return crosswords.size();
    }

    class CrosswordViewHolder extends RecyclerView.ViewHolder {
        public final LetterAdapterDataBinding binding;

        public CrosswordViewHolder(LetterAdapterDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
