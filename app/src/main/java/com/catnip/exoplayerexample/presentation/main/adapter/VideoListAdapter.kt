package com.catnip.exoplayerexample.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.catnip.exoplayerexample.data.model.VideoItem
import com.catnip.exoplayerexample.databinding.ItemVideoBinding

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class VideoListAdapter(private val itemClick: (VideoItem) -> Unit) :
    RecyclerView.Adapter<VideoListAdapter.VideoItemViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<VideoItem>() {
                override fun areItemsTheSame(
                    oldItem: VideoItem,
                    newItem: VideoItem
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: VideoItem,
                    newItem: VideoItem
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            }
        )

    fun submitData(data: List<VideoItem>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemViewHolder {
        val binding =
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoItemViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: VideoItemViewHolder, position: Int) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class VideoItemViewHolder(
        private val binding: ItemVideoBinding,
        private val itemClick: (VideoItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: VideoItem) {
            with(item) {
                binding.root.setOnClickListener { itemClick.invoke(this) }
                binding.tvVideoTitle.text = this.title
                binding.tvVideoAuthor.text = this.author
            }
        }
    }
}
