package mx.test.android.gonet.main.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mx.test.android.gonet.domainlib.models.ListMoviesModel
import mx.test.android.gonet.domainlib.models.MovieRawModel
import mx.test.android.gonet.main.ui.base.BaseViewModel
import mx.test.android.gonet.main.ui.extensions.applySchedulers
import mx.test.android.gonet.processlib.implement.MoviesProcess
import mx.test.android.gonet.servicelib.type.FlowEnum

class MoviesViewModel constructor(context: Context) : BaseViewModel(context) {

    private val moviesProcess: MoviesProcess = MoviesProcess(context)

    private val _action: MutableLiveData<MoviesAction> = MutableLiveData()
    val action: LiveData<MoviesAction> = _action

    private val _latestMovie: MutableLiveData<MovieRawModel> = MutableLiveData()
    val latestMovie: LiveData<MovieRawModel> = _latestMovie

    fun getMoviesList() {
        val map: HashMap<FlowEnum, ListMoviesModel> = hashMapOf()
        disposable.add(
            moviesProcess.listOfMovies(
                flow = FlowEnum.MoviesTopRated,
                idRecommended = "", //N/A
                page = 1
            ).flatMap {
                map[FlowEnum.MoviesTopRated] = it

                moviesProcess.listOfMovies(
                    flow = FlowEnum.MoviesPopular,
                    idRecommended = "", //N/A
                    page = 1
                ).flatMap {
                    map[FlowEnum.MoviesPopular] = it

                    moviesProcess.listOfMovies(
                        flow = FlowEnum.MoviesUpcoming,
                        idRecommended = "", //N/A
                        page = 1
                    )
                }
            }.applySchedulers { showProgress.value = true }
                .subscribe({
                    showProgress.value = false
                    map[FlowEnum.MoviesUpcoming] = it
                    _action.value = MoviesAction.PopulateRecyclerView(map)
                }, {
                    showProgress.value = false
                    _action.value = MoviesAction.ShowError(it.localizedMessage.orEmpty())
                })
        )
    }

    fun getLatestMovie() {
        disposable.add(
            moviesProcess.movieLatest()
                .applySchedulers { showProgress.value = true }
                .subscribe({
                    showProgress.value = false
                    _latestMovie.value = it
                }, {
                    showProgress.value = false
                    showErrorMessage.value = it.localizedMessage.orEmpty()
                })
        )
    }
}