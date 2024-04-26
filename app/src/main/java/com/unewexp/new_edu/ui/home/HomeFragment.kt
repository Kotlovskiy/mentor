package com.unewexp.new_edu.ui.home

import android.animation.AnimatorInflater
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.unewexp.new_edu.R
import com.unewexp.new_edu.R.*
import com.unewexp.new_edu.R.animator.*
import com.unewexp.new_edu.databinding.FragmentHomeBinding
import com.unewexp.new_edu.views.recyclerviews.courses.*
import com.unewexp.new_edu.views.recyclerviews.stories.StoriesListener
import com.unewexp.new_edu.views.recyclerviews.stories.StoriesRecyclerViewAdapter
import com.unewexp.new_edu.views.recyclerviews.stories.StoriesService
import com.unewexp.new_edu.views.searchview.DelayAutoCompleteTextView
import com.unewexp.new_edu.views.searchview.Result
import com.unewexp.new_edu.views.searchview.SearchAdapter
import com.unewexp.new_edu.views.viewpagger.ViewPagerAdapter
import kotlinx.coroutines.*

class HomeFragment : Fragment(){

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var storiesAdapter: StoriesRecyclerViewAdapter
    lateinit var coursesAdapter: CoursesRecyclerViewAdapter

    private val storyListener: StoriesListener = {
        storiesAdapter.stories = it
    }

    private val coursesListener: CoursesListener = {
        coursesAdapter.courses = it
    }

    private lateinit var storiesService: StoriesService
    private lateinit var coursesService: CoursesService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setListenerRadioButton()

        val viewPager = binding.dynamicLayout.cards
        val coursesRecycler = binding.dynamicLayout.coursesRecycler
        val items = 4
        viewPager.adapter = ViewPagerAdapter(items)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                change_dot(position)
            }
        })

        storiesAdapter = StoriesRecyclerViewAdapter()
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.stories.layoutManager = layoutManager
        binding.stories.adapter = storiesAdapter

        storiesService = StoriesService()

        storiesService.addListener(storyListener)

        coursesAdapter = CoursesRecyclerViewAdapter()
        val layoutManagerForCourses = LinearLayoutManager(requireContext())
        coursesRecycler.layoutManager = layoutManagerForCourses
        coursesRecycler.adapter = coursesAdapter
        coursesService = CoursesService()
        coursesService.addListener(coursesListener)

        var isHide = false


        //animation coursesRecycler
        coursesRecycler.addOnScrollListener(CoursesScrollListener(
            {
                raise_recycler(isHide)
                isHide = true
            },
            {
                drop_recycler(isHide)
                isHide = false
            }
        ))



        binding.dynamicLayout.rbutton1.isChecked = true

        createSearch(requireContext())


        return root
    }



    private fun setListenerRadioButton(){
        binding.dynamicLayout.rbutton1.setOnClickListener {
            selectingASection(it as RadioButton)
        }
        binding.dynamicLayout.rbutton2.setOnClickListener {
            selectingASection(it as RadioButton)
        }
        binding.dynamicLayout.rbutton3.setOnClickListener {
            selectingASection(it as RadioButton)
        }
        binding.dynamicLayout.rbutton4.setOnClickListener {
            selectingASection(it as RadioButton)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        storiesService.removeListener(storyListener)
        coursesService.removeListener(coursesListener)
        _binding = null
    }

    private fun selectingASection(view: RadioButton){
        //TODO("update data a CoursesRecycler")
    }

    private fun change_dot(position: Int){
        val selected = R.drawable.selected_dot
        val default = R.drawable.default_dot
        when(position){
            0 -> {
                binding.dynamicLayout.dotsInd.dot1.setBackgroundResource(selected)
                binding.dynamicLayout.dotsInd.dot2.setBackgroundResource(default)
                binding.dynamicLayout.dotsInd.dot4.setBackgroundResource(default)
            }
            1 -> {
                binding.dynamicLayout.dotsInd.dot1.setBackgroundResource(default)
                binding.dynamicLayout.dotsInd.dot2.setBackgroundResource(selected)
                binding.dynamicLayout.dotsInd.dot3.setBackgroundResource(default)
            }
            2 -> {
                binding.dynamicLayout.dotsInd.dot2.setBackgroundResource(default)
                binding.dynamicLayout.dotsInd.dot3.setBackgroundResource(selected)
                binding.dynamicLayout.dotsInd.dot4.setBackgroundResource(default)
            }
            3 -> {
                binding.dynamicLayout.dotsInd.dot1.setBackgroundResource(default)
                binding.dynamicLayout.dotsInd.dot3.setBackgroundResource(default)
                binding.dynamicLayout.dotsInd.dot4.setBackgroundResource(selected)
            }
        }

    }

    private fun createSearch(context: Context){
        val title = binding.textInputLayout
        title.setThreshold(1)
        title.setAdapter(SearchAdapter(context))
        title.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            val result = parent.getItemAtPosition(position) as Result
            title.setText(result.text)
        }
    }



    //animation
    fun raise_recycler(isHide: Boolean) {
        var enableScrolling = false
        if (!isHide) {
            runBlocking {

                binding.dynamicLayout.coursesRecycler.setEnableScrolling(enableScrolling)

                binding.dynamicLayout.cards.clearAnimation()
                binding.dynamicLayout.cards.animate()
                    .y(-binding.dynamicLayout.cards.height.toFloat())
                    .start()

                binding.dynamicLayout.dotsInd.dot1.clearAnimation()
                binding.dynamicLayout.dotsInd.dot1.animate()
                    .y(-binding.dynamicLayout.dotsInd.dot1.height.toFloat())
                    .start()

                binding.dynamicLayout.dotsInd.dot2.clearAnimation()
                binding.dynamicLayout.dotsInd.dot2.animate()
                    .y(-binding.dynamicLayout.dotsInd.dot2.height.toFloat())
                    .start()

                binding.dynamicLayout.dotsInd.dot3.clearAnimation()
                binding.dynamicLayout.dotsInd.dot3.animate()
                    .y(-binding.dynamicLayout.dotsInd.dot3.height.toFloat())
                    .start()

                binding.dynamicLayout.dotsInd.dot4.clearAnimation()
                binding.dynamicLayout.dotsInd.dot4.animate()
                    .y(-binding.dynamicLayout.dotsInd.dot4.height.toFloat())
                    .start()

                CoroutineScope(Dispatchers.IO).async {
                    delay(200)
                    /*binding.dynamicLayout.cards.visibility = View.GONE
                    binding.dynamicLayout.dotsInd.dot1.visibility = View.GONE
                    binding.dynamicLayout.dotsInd.dot2.visibility = View.GONE
                    binding.dynamicLayout.dotsInd.dot3.visibility = View.GONE
                    binding.dynamicLayout.dotsInd.dot4.visibility = View.GONE*/
                    enableScrolling = true
                    binding.dynamicLayout.coursesRecycler.setEnableScrolling(enableScrolling)

                    CoroutineScope(Dispatchers.Main).async{

                        binding.dynamicLayout.group.clearAnimation()
                        binding.dynamicLayout.group.animate()
                            .y(0f)
                            .start()

                        binding.dynamicLayout.coursesRecycler.clearAnimation()
                        binding.dynamicLayout.coursesRecycler.animate()
                            .y(40 + binding.dynamicLayout.group.height.toFloat())
                            .start()
                    }
                }
            }
        }
    }

    fun drop_recycler(isHide: Boolean){

        var enableScrolling = false
        if (isHide) {
            runBlocking {

                binding.dynamicLayout.coursesRecycler.setEnableScrolling(enableScrolling)

                binding.dynamicLayout.cards.clearAnimation()
                binding.dynamicLayout.cards.animate()
                    .y(0f)
                    .start()

                binding.dynamicLayout.dotsInd.dot1.clearAnimation()
                binding.dynamicLayout.dotsInd.dot1.animate()
                    .y(0f)
                    .start()

                binding.dynamicLayout.dotsInd.dot2.clearAnimation()
                binding.dynamicLayout.dotsInd.dot2.animate()
                    .y(0f)
                    .start()

                binding.dynamicLayout.dotsInd.dot3.clearAnimation()
                binding.dynamicLayout.dotsInd.dot3.animate()
                    .y(0f)
                    .start()

                binding.dynamicLayout.dotsInd.dot4.clearAnimation()
                binding.dynamicLayout.dotsInd.dot4.animate()
                    .y(0f)
                    .start()

                CoroutineScope(Dispatchers.IO).async {
                    delay(200)
                    /*binding.dynamicLayout.cards.visibility = View.GONE
                    binding.dynamicLayout.dotsInd.dot1.visibility = View.GONE
                    binding.dynamicLayout.dotsInd.dot2.visibility = View.GONE
                    binding.dynamicLayout.dotsInd.dot3.visibility = View.GONE
                    binding.dynamicLayout.dotsInd.dot4.visibility = View.GONE*/
                    enableScrolling = true
                    binding.dynamicLayout.coursesRecycler.setEnableScrolling(enableScrolling)

                    CoroutineScope(Dispatchers.Main).async{

                        binding.dynamicLayout.group.clearAnimation()
                        binding.dynamicLayout.group.animate()
                            .y(binding.dynamicLayout.cards.height.toFloat() + 10)
                            .start()

                        binding.dynamicLayout.coursesRecycler.clearAnimation()
                        binding.dynamicLayout.coursesRecycler.animate()
                            .y(binding.dynamicLayout.cards.height.toFloat() + binding.dynamicLayout.group.height.toFloat() + 40)
                            .start()
                    }
                }
            }
        }

    }

}