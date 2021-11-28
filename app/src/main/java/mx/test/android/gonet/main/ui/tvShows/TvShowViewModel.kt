package mx.test.android.gonet.main.ui.tvShows

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mx.test.android.gonet.domainlib.models.ListMoviesModel
import mx.test.android.gonet.domainlib.models.ListTvShowsModel
import mx.test.android.gonet.domainlib.models.MovieRawModel
import mx.test.android.gonet.domainlib.models.TvShowRawModel
import mx.test.android.gonet.main.ui.base.BaseViewModel
import mx.test.android.gonet.main.ui.extensions.applySchedulers
import mx.test.android.gonet.processlib.implement.TvShowsProcess
import mx.test.android.gonet.servicelib.type.FlowEnum

class TvShowViewModel constructor(context: Context) : BaseViewModel(context) {

    private val moviesProcess: TvShowsProcess = TvShowsProcess(context)

    private val _action: MutableLiveData<TvShowsAction> = MutableLiveData()
    val action: LiveData<TvShowsAction> = _action

    private val _latestMovie: MutableLiveData<TvShowRawModel> = MutableLiveData()
    val latestMovie: LiveData<TvShowRawModel> = _latestMovie

    fun getMoviesList() {
        val map: HashMap<FlowEnum, ListTvShowsModel> = hashMapOf()
        disposable.add(
            moviesProcess.listOfTvShows(
                flow = FlowEnum.TvShowsTopRated,
                idRecommended = "", //N/A
                page = 1
            ).flatMap {
                map[FlowEnum.TvShowsTopRated] = it

                moviesProcess.listOfTvShows(
                    flow = FlowEnum.TvShowsPopular,
                    idRecommended = "", //N/A
                    page = 1
                ).flatMap {
                    map[FlowEnum.TvShowsPopular] = it

                    moviesProcess.listOfTvShows(
                        flow = FlowEnum.TvShowsOnAir,
                        idRecommended = "", //N/A
                        page = 1
                    )
                }
            }.applySchedulers { showProgress.value = true }
                .subscribe({
                    showProgress.value = false
                    map[FlowEnum.TvShowsOnAir] = it
                    _action.value = TvShowsAction.PopulateRecyclerView(map)
                }, {
                    showProgress.value = false
                    _action.value = TvShowsAction.ShowError(it.localizedMessage.orEmpty())
                })
        )
    }

    fun getLatestMovie() {
        disposable.add(
            moviesProcess.tvShowLatest()
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