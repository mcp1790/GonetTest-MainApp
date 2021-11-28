package mx.test.android.gonet.main.ui.adapter;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import mx.test.android.gonet.domainlib.models.ListMoviesModel
import mx.test.android.gonet.domainlib.models.ListTvShowsModel
import mx.test.android.gonet.domainlib.models.MovieRawModel
import mx.test.android.gonet.domainlib.models.TvShowRawModel
import mx.test.android.gonet.main.R
import mx.test.android.gonet.main.databinding.GenericItemRecyclerViewBinding
import mx.test.android.gonet.main.ui.extensions.categoryName
import mx.test.android.gonet.servicelib.type.FlowEnum

class ListTvShowsAdapter(
    private var movies: HashMap<FlowEnum, ListTvShowsModel>,
) : RecyclerView.Adapter<ListTvShowsAdapter.ViewHolder>() {

    private val keysMap: ArrayList<String> = arrayListOf()
    private val listMap: ArrayList<List<TvShowRawModel>> = arrayListOf()

    init {
        for ((category, listMovies) in movies){
            keysMap.add(category.categoryName())
            listMap.add(listMovies.results)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val binding: GenericItemRecyclerViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.generic_item_recycler_view,
            parent,
            false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.bind(category = keysMap[p1], listMap[p1])
    }


    inner class ViewHolder(private val binding: GenericItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String, moviesList: List<TvShowRawModel>) {
            binding.apply {
                textViewTitleSection.text = category
                recyclerViewMovies.adapter = MoviesAdapter(moviesList)
            }
        }
    }
}
