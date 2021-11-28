package mx.test.android.gonet.main.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import mx.test.android.gonet.domainlib.models.ListMoviesModel
import mx.test.android.gonet.main.databinding.FragmentMoviesBinding
import mx.test.android.gonet.servicelib.type.FlowEnum
import mx.test.android.gonet.domainlib.models.MovieRawModel
import mx.test.android.gonet.main.ui.adapter.GenericTextAdapter
import mx.test.android.gonet.main.ui.adapter.ListMoviesAdapter
import mx.test.android.gonet.main.ui.base.*
import mx.test.android.gonet.main.ui.details.DetailsActivity

class MoviesFragment : BaseFragment() {

    private val viewModel: MoviesViewModel by viewModels { ViewModelFactory(requireContext()) }
    private var _binding: FragmentMoviesBinding? = null
    private val binding: FragmentMoviesBinding
        get() = _binding!!
    private var itemDecoration: DividerItemDecoration? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
        initObservablesViewModel()
    }

    override fun initComponents() {
        viewModel.getMoviesList()
        viewModel.getLatestMovie()
    }

    override fun initObservablesViewModel() {
        viewModel.action.observe(viewLifecycleOwner, Observer(this::handleAction))
        viewModel.latestMovie.observe(viewLifecycleOwner, Observer(this::showLatestMovie))
        viewModel.getShowProgress().observe(viewLifecycleOwner, Observer(this::showProgressDialog))
    }

    private fun handleAction(action: MoviesAction) {
        when (action) {
            is MoviesAction.PopulateRecyclerView -> {
                populateView(action.moviesList)
            }
            is MoviesAction.ShowError -> {
                showErrorMessage(action.errorMessage)
            }
        }
    }

    private fun showLatestMovie(movie: MovieRawModel) {

    }

    private fun populateView(map: HashMap<FlowEnum, ListMoviesModel>) {

        val movie = map[FlowEnum.MoviesPopular]?.results?.random()!!

        with(binding) {
            recyclerViewGenresLatestMovies.adapter =
                GenericTextAdapter(movie.genres.map { it.name })
            textViewTitleLatestMovies.text = movie.title

            Glide.with(requireContext())
                .load(UrlBaseImageOriginalSize.plus(movie.backdropPath))
                .centerCrop()
                .into(binding.imageViewTopRatedMovie)

            recyclerViewGeneralList.adapter = ListMoviesAdapter(map)

            containerMainMovie.setOnClickListener {
                startActivity(Intent(requireContext(), DetailsActivity::class.java).apply {
                    putExtra(MovieModel, movie)
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}