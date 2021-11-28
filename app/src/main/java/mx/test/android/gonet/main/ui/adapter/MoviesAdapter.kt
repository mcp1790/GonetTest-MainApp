package mx.test.android.gonet.main.ui.adapter;

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import mx.test.android.gonet.domainlib.models.ListMoviesModel
import mx.test.android.gonet.domainlib.models.MovieRawModel
import mx.test.android.gonet.domainlib.models.TvShowRawModel
import mx.test.android.gonet.main.R
import mx.test.android.gonet.main.databinding.GenericItemRecyclerViewBinding
import mx.test.android.gonet.main.databinding.ItemMoviesBinding
import mx.test.android.gonet.main.ui.base.MovieModel
import mx.test.android.gonet.main.ui.base.TvShowModel
import mx.test.android.gonet.main.ui.base.UrlBaseImagePosterSize
import mx.test.android.gonet.main.ui.details.DetailsActivity
import mx.test.android.gonet.main.ui.extensions.categoryName
import mx.test.android.gonet.servicelib.type.FlowEnum

class MoviesAdapter(
    private var movies: List<Any>,
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val binding: ItemMoviesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movies,
            parent,
            false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.bind(movieModel = movies[p1])
    }


    inner class ViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movieModel: Any) {
            val posterPath = when (movieModel){
                is MovieRawModel -> {
                    movieModel.posterPath
                }
                is TvShowRawModel -> {
                    movieModel.posterPath
                }
                else -> ""
            }

            binding.apply {
                Glide.with(itemView.context)
                    .load(UrlBaseImagePosterSize.plus(posterPath))
                    .centerCrop()
                    .into(imageViewMoviePoster)

                itemView.setOnClickListener { v->
                    v.context.apply {
                        startActivity(Intent(this, DetailsActivity::class.java).apply {
                            when (movieModel) {
                                is MovieRawModel -> {
                                    putExtra(MovieModel, movieModel)
                                }
                                is TvShowRawModel -> {
                                    putExtra(TvShowModel, movieModel)
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}
