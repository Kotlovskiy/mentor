package com.unewexp.new_edu.views.recyclerviews.stories

import com.unewexp.new_edu.R

typealias StoriesListener = (stories: List<StoriesData>) -> Unit

class StoriesService {
    private var stories = mutableListOf<StoriesData>()
    private val listeners = mutableSetOf<StoriesListener>()
    init {
        stories.add(StoriesData(true, "Новые направления", R.drawable.story))
        stories.add(StoriesData(true, "Новые направления", R.drawable.story))
        stories.add(StoriesData(true, "Новые направления", R.drawable.story))
        stories.add(StoriesData(true, "Новые направления", R.drawable.story))
        stories.add(StoriesData(true, "Новые направления", R.drawable.story))
        stories.add(StoriesData(true, "Новые направления", R.drawable.story))
    }

    fun getStories(): List<StoriesData>{
        return stories
    }

    fun addListener(listener: StoriesListener){
        listeners.add(listener)
        listener.invoke(stories)
    }

    fun removeListener(listener: StoriesListener){
        listeners.remove(listener)
    }
}