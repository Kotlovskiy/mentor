package com.unewexp.new_edu.views.searchview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.unewexp.new_edu.R

class SearchAdapter(val context: Context): BaseAdapter(), Filterable {

    private var mResult = mutableListOf<Result>()
    private var inHistory: Boolean = true

    override fun getCount(): Int {
        return mResult.size
    }

    override fun getItem(p0: Int): Any {
        return mResult[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val result = mResult[position]
        if (inHistory) {
            if (convertView == null) {
                val inflater = LayoutInflater.from(context)
                convertView = inflater.inflate(R.layout.dropdown_item_search_in_history, parent, false)
            }
            convertView?.findViewById<TextView>(R.id.text_history)?.setText(result.text)
        }else{
            if (convertView == null) {
                val inflater = LayoutInflater.from(context)
                convertView = inflater.inflate(R.layout.dropdown_item_search_in_db, parent, false)
            }
            convertView?.findViewById<TextView>(R.id.text_search)?.setText(result.text)
        }

        return convertView ?: throw IllegalStateException("View should not be null")
    }

    override fun getFilter(): Filter {
        val filter = object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                constraint?.let {
                    val results = find(it.toString())
                    filterResults.values = results
                    filterResults.count = results.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                if (results != null && results.count > 0) {
                    mResult = (results.values as List<Result>).toMutableList()
                    notifyDataSetChanged()
                }else{
                    notifyDataSetInvalidated()
                }
            }
        }
        return filter
    }

    private fun find(query: String): List<Result>{
        TODO("Create search query in history/db")
    }

}



data class Result(
    val text: String
)