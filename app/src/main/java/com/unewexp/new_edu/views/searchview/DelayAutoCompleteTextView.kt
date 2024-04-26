package com.unewexp.new_edu.views.searchview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet

class DelayAutoCompleteTextView(context: Context, attributeSet: AttributeSet):
    androidx.appcompat.widget.AppCompatAutoCompleteTextView(context, attributeSet) {
        private var delay = 750

    public fun setAutoCompleteDelay(autoCompleteDelay: Int){
        delay = autoCompleteDelay
    }

    private val handler = Handler(Looper.getMainLooper()) {
        super.performFiltering(it.obj as CharSequence, it.arg1)
        true
    }

    override fun performFiltering(text: CharSequence?, keyCode: Int) {
        handler.removeMessages(100)
        handler.sendMessageDelayed(handler.obtainMessage(100, text), delay.toLong())
    }
}