package com.unewexp.new_edu.views.recyclerviews.courses

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unewexp.new_edu.databinding.CardOfCourseBinding

class CoursesRecyclerViewAdapter: RecyclerView.Adapter<CourseVH>() {

    var courses: List<CoursesData> = emptyList()
        set(value) {
            field = value
            Log.e("e", "Update")
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardOfCourseBinding.inflate(inflater, parent, false)
        return CourseVH(binding)
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    override fun onBindViewHolder(holder: CourseVH, position: Int) {
        val course = courses[position]
        with(holder.binding){
            this.imageView.setImageResource(course.image)
            this.name.text = course.name
            this.description.text = course.description
        }
    }

}

class CourseVH(val binding: CardOfCourseBinding): RecyclerView.ViewHolder(binding.root)