package com.jjl.sudokuplay

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SudokuRvAdapter(private val mContext: Context, private val mList: List<Int>?) : RecyclerView.Adapter<SudokuRvAdapter.SudokuViewHolder>() {
    private val mLocalinflater: LayoutInflater

    init {
        mLocalinflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SudokuViewHolder {
        return SudokuViewHolder(mLocalinflater.inflate(R.layout.item_sudoku, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: SudokuViewHolder, index: Int) {
        viewHolder.setData(mList, index)
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    inner class SudokuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mTextView: TextView

        init {
            mTextView = itemView.findViewById(R.id.textView)
        }

        fun setData(data: List<Int>?, index: Int) {
            mTextView.text = data?.get(index).toString()
        }
    }

    fun Any?.toString(): String {
        if (this == null) return "null"
        return toString()
    }
}
