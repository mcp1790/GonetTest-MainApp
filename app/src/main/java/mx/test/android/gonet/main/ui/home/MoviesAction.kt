package mx.test.android.gonet.main.ui.home

import mx.test.android.gonet.domainlib.models.ListMoviesModel
import mx.test.android.gonet.domainlib.models.ListTvShowsModel
import mx.test.android.gonet.servicelib.type.FlowEnum

sealed class MoviesAction(){
    data class PopulateRecyclerView(var moviesList: HashMap<FlowEnum, ListMoviesModel>) : MoviesAction()
    data class ShowError(var errorMessage: String) : MoviesAction()
}
