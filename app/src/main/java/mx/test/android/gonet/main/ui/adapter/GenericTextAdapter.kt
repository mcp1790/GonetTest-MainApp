package mx.test.android.gonet.main.ui.adapter;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import mx.test.android.gonet.main.R
import mx.test.android.gonet.main.databinding.ItemTextBinding

class GenericTextAdapter(
    private var entries: List<String>,
) : RecyclerView.Adapter<GenericTextAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val binding: ItemTextBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_text,
            parent,
            false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.bind(text = entries[p1])
    }


    inner class ViewHolder(private val binding: ItemTextBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String) {
            binding.textView.text = text
            binding.divider.visibility = if (adapterPosition == itemCount.minus(1))
                View.GONE
            else
                View.VISIBLE
        }
    }
}
