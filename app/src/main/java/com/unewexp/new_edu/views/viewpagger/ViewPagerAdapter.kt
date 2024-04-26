package com.unewexp.new_edu.views.viewpagger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.unewexp.new_edu.databinding.CardImmediateTaskBinding
import com.unewexp.new_edu.databinding.CardRecentNotificationsBinding

class ViewPagerAdapter(val items: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            FIRST_CARD -> {
                val view = CardImmediateTaskBinding.inflate(inflater, parent, false)
                PagerVH1(view)
            }
            SECOND_CARD -> {
                val view = CardRecentNotificationsBinding.inflate(inflater, parent, false)
                PagerVH2(view)
            }
            else -> throw IllegalArgumentException("Unknown item type")
        }
    }

    override fun getItemCount(): Int = items

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is PagerVH1 -> {
            }
            is PagerVH2 -> {
                val h = holder
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position){
            0 -> FIRST_CARD
            1 -> SECOND_CARD
            2 -> FIRST_CARD
            3 -> FIRST_CARD
            else -> throw IllegalArgumentException("Unknown item type")
        }
    }

    companion object{
        private const val FIRST_CARD = 1
        private const val SECOND_CARD = 2
    }
}
class PagerVH1(val binding: CardImmediateTaskBinding) : RecyclerView.ViewHolder(binding.root)
class PagerVH2(val binding: CardRecentNotificationsBinding) : RecyclerView.ViewHolder(binding.root)