package mx.test.android.gonet.main.ui.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mx.test.android.gonet.domainlib.models.ListMoviesModel
import mx.test.android.gonet.domainlib.models.ListTvShowsModel
import mx.test.android.gonet.main.ui.base.BaseViewModel
import mx.test.android.gonet.main.ui.extensions.applySchedulers
import mx.test.android.gonet.processlib.implement.MoviesProcess
import mx.test.android.gonet.processlib.implement.TvShowsProcess
import mx.test.android.gonet.servicelib.type.FlowEnum

class DetailsViewModel constructor(context: Context) : BaseViewModel(context) {

    private val moviesProcess: MoviesProcess = MoviesProcess(context)
    private val tvShowProcess: TvShowsProcess = TvShowsProcess(context)

    private val _action: MutableLiveData<DetailsAction> = MutableLiveData()
    val action: LiveData<DetailsAction> = _action

    private val _recommendedMovie: MutableLiveData<ListMoviesModel> = MutableLiveData()
    val recommendedMovie: LiveData<ListMoviesModel> = _recommendedMovie

    private val _recommendedTvShow: MutableLiveData<ListTvShowsModel> = MutableLiveData()
    val recommendedTvShow: LiveData<ListTvShowsModel> = _recommendedTvShow

    fun getMovieDetails(id: String) {
        disposable.add(
            moviesProcess.moviesDetails(
                movieId = id
            ).applySchedulers { showProgress.value = true }
                .subscribe({
                    showProgress.value = false
                    _action.value = DetailsAction.PopulateDetailsMovie(it)
                }, {
                    showProgress.value = false
                    _action.value = DetailsAction.ShowError(it.localizedMessage.orEmpty())
                })
        )
    }

    fun getRecommendedMovie(id: String) {
        disposable.add(
            moviesProcess.listOfMovies(
                flow = FlowEnum.MoviesRecommendation,
                idRecommended = id,
                page = 1
            ).applySchedulers { showProgress.value = true }
                .subscribe({
                    showProgress.value = false
                    _recommendedMovie.value = it
                }, {
                    showProgress.value = false
                    _action.value = DetailsAction.ShowError(it.localizedMessage.orEmpty())
                })
        )
    }


    fun getTvShowDetails(id: String) {
        disposable.add(
            tvShowProcess.tvShowDetails(
                tvShowId = id
            ).applySchedulers { showProgress.value = true }
                .subscribe({
                    showProgress.value = false
                    _action.value = DetailsAction.PopulateDetailsTvShow(it)
                }, {
                    showProgress.value = false
                    _action.value = DetailsAction.ShowError(it.localizedMessage.orEmpty())
                })
        )
    }

    fun getRecommendedTvShow(id: String) {
        disposable.add(
            tvShowProcess.listOfTvShows(
                flow = FlowEnum.TvShowsRecommendation,
                idRecommended = id,
                page = 1
            ).applySchedulers { showProgress.value = true }
                .subscribe({
                    showProgress.value = false
                    _recommendedTvShow.value = it
                }, {
                    showProgress.value = false
                    _action.value = DetailsAction.ShowError(it.localizedMessage.orEmpty())
                })
        )
    }
}