package mx.test.android.gonet.main.ui.details

import mx.test.android.gonet.domainlib.models.MovieRawModel
import mx.test.android.gonet.domainlib.models.TvShowDetailModel

sealed class DetailsAction{
    data class PopulateDetailsMovie(var movieDetails: MovieRawModel) : DetailsAction()
    data class PopulateDetailsTvShow(var tvShowDetails: TvShowDetailModel) : DetailsAction()
    data class ShowError(var errorMessage: String) : DetailsAction()
}
