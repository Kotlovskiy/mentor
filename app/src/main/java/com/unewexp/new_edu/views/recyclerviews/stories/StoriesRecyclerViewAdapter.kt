package com.unewexp.new_edu.views.recyclerviews.stories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unewexp.new_edu.databinding.StoriesCardBinding

class StoriesRecyclerViewAdapter(): RecyclerView.Adapter<StoriesVH>() {

    var stories: List<StoriesData> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StoriesCardBinding.inflate(inflater, parent, false)
        return StoriesVH(binding)
    }

    override fun getItemCount(): Int {
        return stories.size
    }

    override fun onBindViewHolder(holder: StoriesVH, position: Int) {
        val story = stories[position]
        with(holder.binding){
            this.imageStory.setImageResource(story.image)
            this.textOfStory.text = story.text
            if (story.isNew){
                this.newStoryImage.visibility = View.VISIBLE
            }else{
                this.newStoryImage.visibility = View.GONE
            }
        }
    }
}
class StoriesVH(val binding: StoriesCardBinding): RecyclerView.ViewHolder(binding.root)