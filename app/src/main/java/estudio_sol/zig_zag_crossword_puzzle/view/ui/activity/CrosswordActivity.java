package estudio_sol.zig_zag_crossword_puzzle.view.ui.activity;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.sol.estudo.zigzagcrosswordpuzzle.R;
import com.sol.estudo.zigzagcrosswordpuzzle.databinding.CrosswordDataBinding;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import estudio_sol.zig_zag_crossword_puzzle.model.Label;
import estudio_sol.zig_zag_crossword_puzzle.view.ui.adapter.CrosswordAdapter;
import estudio_sol.zig_zag_crossword_puzzle.view_model.CrosswordViewModel;

public class CrosswordActivity extends AppCompatActivity {

    @Nullable @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @Nullable @BindView(R.id.search_view)
    protected MaterialSearchView searchView;
    @Nullable @BindView(R.id.toolbar_title)
    protected TextView toolbarTitle;
    @Nullable @BindView(R.id.toolbar_subtitle)
    protected TextView toolbarSubtitle;

    private CrosswordDataBinding binding;
    private CrosswordViewModel viewModel;
    private CrosswordAdapter crosswordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_crossword);

        ButterKnife.bind(this);

        setToolbar(getString(R.string.app_title_toolbar), getString(R.string.app_subtitle_toolbar), false);

        viewModel = new CrosswordViewModel(this);

        crosswordAdapter = new CrosswordAdapter(this, viewModel.getFirstLabels());

        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 10));

        binding.recyclerView.setAdapter(crosswordAdapter);

        binding.setCrossword(viewModel);

        handleSearch();
    }

    private void handleSearch() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<String> wordIndex = viewModel.getFilter(query);

                for (String index : wordIndex) {
                    String[] letterIndex = index.split(":");
                    viewModel.matrixLetters[Integer.valueOf(letterIndex[0])][Integer.valueOf(letterIndex[1])].setMarked(true);
                }

                crosswordAdapter = new CrosswordAdapter(getApplicationContext(), viewModel.getLabelsAfterSearch(viewModel.matrixLetters));

                binding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 10));

                binding.setCrossword(viewModel);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar_search, menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView.setMenuItem(item);
        return true;
    }

    protected void setToolbar(final String title, final String subtitle, boolean homeAsUpEnable) {
        toolbar.setTitle("");
        toolbarTitle.setText(title);
        if (subtitle != null) {
            toolbarSubtitle.setVisibility(View.VISIBLE);
            toolbarSubtitle.setText(subtitle);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnable);
    }

}
