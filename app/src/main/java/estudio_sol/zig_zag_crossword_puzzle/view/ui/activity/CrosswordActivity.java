package estudio_sol.zig_zag_crossword_puzzle.view.ui.activity;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
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

public class CrosswordActivity extends AppCompatActivity implements LifecycleOwner {

    private final LifecycleRegistry registry = new LifecycleRegistry(this);

    // Combina os componentes da tela com os objetos programáveis dos itens
    @Nullable @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @Nullable @BindView(R.id.search_view)
    protected MaterialSearchView searchView;
    @Nullable @BindView(R.id.toolbar_title)
    protected TextView toolbarTitle;
    @Nullable @BindView(R.id.toolbar_subtitle)
    protected TextView toolbarSubtitle;
    @Nullable @BindView(R.id.search_results)
    protected TextView searchResults;

    // Define os objetos do modelo, do adapter e da combinação dos componentes da tela
    private CrosswordDataBinding binding;
    private CrosswordViewModel viewModel;
    private CrosswordAdapter crosswordAdapter;

    // Inicializa o programa, inicializando e populando o modelo e o adapter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_crossword);

        ButterKnife.bind(this);

        setToolbar(getString(R.string.app_title_toolbar), getString(R.string.app_subtitle_toolbar), false);

        viewModel = new CrosswordViewModel(getApplication());

        crosswordAdapter = new CrosswordAdapter(this, viewModel.getFirstLabels());

        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 10));

        binding.recyclerView.setAdapter(crosswordAdapter);

        binding.setCrossword(viewModel);

        handleSearch();

        observe(crosswordAdapter);
    }

    // Método que gerencia a procura no aplicativo, e a abertura e fechamento do item da procura
    private void handleSearch() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<String> wordIndex = viewModel.getFilter(query);

                if (wordIndex.size() > 0 & viewModel.getOccurrences() > 1) {
                    searchResults.setText(viewModel.getOccurrences() + " ocorrências foram encontradas de '" + query + "'");
                    searchResults.setVisibility(View.VISIBLE);
                } else if (wordIndex.size() > 0 & viewModel.getOccurrences() == 1) {
                    searchResults.setText(viewModel.getOccurrences() + " ocorrência foi encontrada de '" + query + "'");
                    searchResults.setVisibility(View.VISIBLE);
                } else {
                    searchResults.setText(viewModel.getOccurrences() + " ocorrência foi encontrada de '" + query + "'");
                    searchResults.setVisibility(View.VISIBLE);
                }

                for (String index : wordIndex) {
                    String[] letterIndex = index.split(":");
                    viewModel.matrixLetters[Integer.valueOf(letterIndex[0])][Integer.valueOf(letterIndex[1])].setMarked(true);
                }

                viewModel.setLabels(viewModel.getLabelsAfterSearch(viewModel.matrixLetters));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                viewModel.resetOccurrences();
                viewModel.setLabels(viewModel.getFirstLabels());
                searchResults.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
            }
        });
    }

    // Método que insere item da procura na barra superior do aplicativo
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar_search, menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView.setMenuItem(item);
        return true;
    }

    // Metodo que inicaliza a barra de ferramentas com o título e subtítulo do aplicativo
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

    // Observa as mudanças dinâmicas nos itens do Caça-palavras ao longo das pesquisas
    public void observe(final CrosswordAdapter crosswordAdapter) {
        viewModel.getLabelsListObservable().observe(this, new Observer<List<Label>>() {
            @Override
            public void onChanged(@Nullable List<Label> labels) {
                crosswordAdapter.setCrosswordsList(labels);
            }
        });
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return registry;
    }
}
