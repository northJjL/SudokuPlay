package com.jjl.sudokuplay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SudokuRvAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Integer> mList;
    private final LayoutInflater mLocalinflater;

    public SudokuRvAdapter(Context context, List<Integer> list) {
        mContext = context;
        mList = list;
        mLocalinflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SudokuViewHolder(mLocalinflater.inflate(R.layout.item_sudoku ,viewGroup , false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int index) {
        ((SudokuViewHolder)viewHolder).setData(mList , index);
    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        return 0;
    }

    class SudokuViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;

        public SudokuViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textView);
        }

        public void  setData( List<Integer> data , int index){
            mTextView.setText(String.valueOf(data.get(index)));
        }
    }
}
