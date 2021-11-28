package mx.test.android.gonet.main.ui.extensions

import mx.test.android.gonet.servicelib.type.FlowEnum

fun FlowEnum.categoryName(): String {
    return when (this) {
        FlowEnum.MoviesTopRated, FlowEnum.TvShowsTopRated -> "Mejor calificadas"
        FlowEnum.MoviesPopular, FlowEnum.TvShowsPopular -> "Populares"
        FlowEnum.MoviesRecommendation, FlowEnum.TvShowsRecommendation -> "Recomendadas"
        FlowEnum.MoviesUpcoming -> "Próximamente"
        FlowEnum.TvShowsOnAir -> "Al aire"
        else -> ""
    }
}