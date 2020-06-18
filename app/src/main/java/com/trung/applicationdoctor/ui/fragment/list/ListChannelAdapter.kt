package com.trung.applicationdoctor.ui.fragment.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trung.applicationdoctor.data.remote.response.ChannelList
import com.trung.applicationdoctor.databinding.ItemListChannelBinding
import com.trung.applicationdoctor.util.extension.hideKeyboard
import java.util.*
import kotlin.collections.ArrayList

class ListChannelAdapter : ListAdapter<ChannelList, ListChannelAdapter.ViewHolder>(DiffCallback), Filterable {
    val items: ArrayList<ChannelList> = arrayListOf()

    lateinit var binding: ItemListChannelBinding

    private var onItemClickListener: ((Int, ChannelList, view: View) -> Unit)? = null

    var photoFilterList = ArrayList<ChannelList>()

    init {
        photoFilterList = items
    }

    fun setIetms(items: List<ChannelList>?) {
        items?.let {
            this.items.clear()
            this.items.addAll(items)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return photoFilterList.size
    }

    fun setOnItemClickListener(onItemClickListener: ((Int, ChannelList, View) -> Unit)) {
        this.onItemClickListener = onItemClickListener
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemListChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(photoFilterList[position], onItemClickListener)
    }

    /**
     * The ViewHolder constructor takes the binding variable from the associated
     * ItemPhotolist, which nicely gives it access to the full [channelList] information.
     */
    class ViewHolder(private val binding: ItemListChannelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            channelList: ChannelList,
            onItemClickListener: ((Int, ChannelList, View) -> Unit)?
        ) {

            binding.channelList = channelList

            binding.root.setOnClickListener {
                binding.root.hideKeyboard()
                onItemClickListener?.invoke(position, channelList, binding.ivPhotoUrl)
            }

            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

//    override fun onViewRecycled(holder: ViewHolder) {
//        super.onViewRecycled(holder)
//
//        GlideApp.with(context).clear(binding.ivPhotoUrl)
//    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    photoFilterList = items
                } else {
                    val resultList = ArrayList<ChannelList>()
                    for (item in items) {
                        if (item.title?.toLowerCase(Locale.ROOT)
                                ?.contains(charSearch.toLowerCase(Locale.ROOT))!!
                        ) {
                            resultList.add(item)
                        }
                    }
                    photoFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = photoFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                photoFilterList = results?.values as ArrayList<ChannelList>
                notifyDataSetChanged()
            }

        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [ChannelList]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<ChannelList>() {
        //Use Kotlin's referential equality operator (===), which returns true if the object references for oldItem and newItem are the same.
        //override fun areItemsTheSame(oldItem: PhotoDetailEntity, newItem: PhotoDetailEntity) = oldItem === newItem
        override fun areItemsTheSame(oldItem: ChannelList, newItem: ChannelList) =
            oldItem.boardIdx == newItem.boardIdx

        override fun areContentsTheSame(oldItem: ChannelList, newItem: ChannelList) =
            oldItem == newItem
    }
}