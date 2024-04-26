package com.unewexp.new_edu.views.recyclerviews.courses

import com.unewexp.new_edu.R

typealias CoursesListener = (courses: List<CoursesData>) -> Unit

class CoursesService {
    private var courses = mutableListOf<CoursesData>()
    private val listeners = mutableSetOf<CoursesListener>()

    init {
        courses.add(CoursesData("База для всех программистов", "То - что должен знать каждый программист", R.drawable.course_of_bcg))
        courses.add(CoursesData("База для всех программистов", "То - что должен знать каждый программист", R.drawable.course_of_bcg))
        courses.add(CoursesData("База для всех программистов", "То - что должен знать каждый программист", R.drawable.course_of_bcg))
        courses.add(CoursesData("База для всех программистов", "То - что должен знать каждый программист", R.drawable.course_of_bcg))
        courses.add(CoursesData("База для всех программистов", "То - что должен знать каждый программист", R.drawable.course_of_bcg))
    }

    fun getCourses(): List<CoursesData>{
        return courses
    }

    fun addListener(listener: CoursesListener){
        listeners.add(listener)
        listener.invoke(courses)
    }

    fun removeListener(listener: CoursesListener){
        listeners.remove(listener)
    }
}