package mx.test.android.gonet.main.ui.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import mx.test.android.gonet.domainlib.models.*
import mx.test.android.gonet.main.databinding.ActivityDetailsBinding
import mx.test.android.gonet.main.ui.adapter.GenericTextAdapter
import mx.test.android.gonet.main.ui.adapter.ListMoviesAdapter
import mx.test.android.gonet.main.ui.adapter.ListTvShowsAdapter
import mx.test.android.gonet.main.ui.base.*
import mx.test.android.gonet.servicelib.type.FlowEnum

class DetailsActivity : BaseActivity() {

    private lateinit var binding : ActivityDetailsBinding
    private val viewModel: DetailsViewModel by viewModels { ViewModelFactory(applicationContext) }
    private val durationFormat = "% 02d:% 02d:% 02d"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLoading()
        initComponents()
        initObservablesViewModel()
    }

    override fun initComponents() {
        with(intent){
            when{
                hasExtra(MovieModel) ->{
                    val movieModel = intent.getParcelableExtra<MovieRawModel>(MovieModel)
                    viewModel.getMovieDetails(movieModel?.id.toString())
                    viewModel.getRecommendedMovie(movieModel?.id.toString())
                }
                hasExtra(TvShowModel) ->{
                    val tvShowModel = intent.getParcelableExtra<TvShowRawModel>(TvShowModel)
                    viewModel.getTvShowDetails(tvShowModel?.id.toString())
                    viewModel.getRecommendedTvShow(tvShowModel?.id.toString())
                }
            }
        }
    }

    override fun initObservablesViewModel() {
        viewModel.action.observe(this, Observer(this::handleAction))
        viewModel.recommendedMovie.observe(this, Observer(this::populateRecommendedMovies))
        viewModel.recommendedTvShow.observe(this, Observer(this::populateRecommendedTvShows))
        viewModel.getShowProgress().observe(this, Observer(this::showProgressDialog))
    }

    private fun handleAction(action: DetailsAction){
        when(action){
            is DetailsAction.PopulateDetailsMovie -> printDetailsMovie(action.movieDetails)
            is DetailsAction.ShowError -> showErrorMessage(action.errorMessage)
            is DetailsAction.PopulateDetailsTvShow -> printDetailsTvShow(action.tvShowDetails)
        }
    }

    private fun printDetailsMovie(model: MovieRawModel){
        with(binding){
            Glide.with(applicationContext)
                .load(UrlBaseImageOriginalSize.plus(model.backdropPath))
                .centerCrop()
                .into(imageViewBackdropDetails)

            Glide.with(applicationContext)
                .load(UrlBaseImagePosterSize.plus(model.posterPath))
                .circleCrop()
                .fitCenter()
                .into(imageViewPosterDetails)

            textViewTitle.text = model.title
            textViewYear.text = model.releaseDate
            textViewDuration.text = parseTime(model.runtime)
            recyclerViewGenres.adapter = GenericTextAdapter(model.genres.map { it.name })
            textViewDescription.text = model.overview
        }
    }

    private fun printDetailsTvShow(model: TvShowDetailModel){
        with(binding){
            Glide.with(applicationContext)
                .load(UrlBaseImageOriginalSize.plus(model.backdropPath))
                .centerCrop()
                .into(imageViewBackdropDetails)

            Glide.with(applicationContext)
                .load(UrlBaseImagePosterSize.plus(model.posterPath))
                .circleCrop()
                .fitCenter()
                .into(imageViewPosterDetails)

            textViewTitle.text = model.name
            textViewYear.text = "${model.firstAirDate} - ${model.lastEpisodeToAir.airDate}"
            textViewDuration.text = "${model.numberOfSeasons} temporadas"
            recyclerViewGenres.adapter = GenericTextAdapter(model.genres.map { it.name })
            textViewDescription.text = model.overview
        }
    }

    private fun populateRecommendedMovies(list: ListMoviesModel){
        with(binding){
            val map: HashMap<FlowEnum, ListMoviesModel> = HashMap()
            map[FlowEnum.MoviesRecommendation] = list
            recyclerViewRecommendedMovies.adapter = ListMoviesAdapter(map)
        }
    }

    private fun populateRecommendedTvShows(list: ListTvShowsModel){
        with(binding){
            val map: HashMap<FlowEnum, ListTvShowsModel> = HashMap()
            map[FlowEnum.TvShowsRecommendation] = list
            recyclerViewRecommendedMovies.adapter = ListTvShowsAdapter(map)
        }
    }

    private fun parseTime(time: Int): String{
        val hour = time / 60
        val minutes = time % 60
        return "$hour h :$minutes min"
    }
}