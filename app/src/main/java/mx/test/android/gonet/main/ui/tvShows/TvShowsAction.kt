package mx.test.android.gonet.main.ui.tvShows

import mx.test.android.gonet.domainlib.models.ListMoviesModel
import mx.test.android.gonet.domainlib.models.ListTvShowsModel
import mx.test.android.gonet.servicelib.type.FlowEnum

sealed class TvShowsAction(){
    data class PopulateRecyclerView(var tvShowList: HashMap<FlowEnum, ListTvShowsModel>) : TvShowsAction()
    data class ShowError(var errorMessage: String) : TvShowsAction()
}
