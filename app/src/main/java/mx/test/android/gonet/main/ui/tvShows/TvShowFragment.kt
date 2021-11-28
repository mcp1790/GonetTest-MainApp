package mx.test.android.gonet.main.ui.tvShows

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import mx.test.android.gonet.domainlib.models.ListTvShowsModel
import mx.test.android.gonet.domainlib.models.MovieRawModel
import mx.test.android.gonet.domainlib.models.TvShowRawModel
import mx.test.android.gonet.main.databinding.FragmentTvShowBinding
import mx.test.android.gonet.main.ui.adapter.GenericTextAdapter
import mx.test.android.gonet.main.ui.adapter.ListMoviesAdapter
import mx.test.android.gonet.main.ui.adapter.ListTvShowsAdapter
import mx.test.android.gonet.main.ui.base.*
import mx.test.android.gonet.main.ui.details.DetailsActivity
import mx.test.android.gonet.servicelib.type.FlowEnum

class TvShowFragment : BaseFragment() {

    private val viewModel: TvShowViewModel by viewModels { ViewModelFactory(requireContext()) }
    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)

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

    private fun handleAction(action: TvShowsAction) {
        when (action) {
            is TvShowsAction.PopulateRecyclerView -> {
                populateView(action.tvShowList)
            }
            is TvShowsAction.ShowError -> {
                showErrorMessage(action.errorMessage)
            }
        }
    }

    private fun showLatestMovie(movie: TvShowRawModel) {

    }

    private fun populateView(map: HashMap<FlowEnum, ListTvShowsModel>) {

        val tvShow = map[FlowEnum.TvShowsPopular]?.results?.random()!!

        with(binding) {
            recyclerViewGenresLatestTvShows.adapter =
                GenericTextAdapter(tvShow.genres.map { it.name })
            textViewTitleLatestTvShows.text = tvShow.name

            Glide.with(requireContext())
                .load(UrlBaseImageOriginalSize.plus(tvShow.backdropPath))
                .centerCrop()
                .into(binding.imageViewTopRatedTvShow)

            recyclerViewGeneralList.adapter = ListTvShowsAdapter(map)

            containerMainTvShow.setOnClickListener {
                startActivity(Intent(requireContext(), DetailsActivity::class.java).apply {
                    putExtra(TvShowModel, tvShow)
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}