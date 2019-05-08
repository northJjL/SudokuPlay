package com.jjl.sudokuplay

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var tempRefresh = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    var a = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    var b = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    var c = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    var d = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    var e = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    var f = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    var g = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    var h = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    var i = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    var set = setOf<IntArray>(a, b, c, d, e, f, g, h, i)
    lateinit var sudokuRv: RecyclerView;
    lateinit var refreshButton: Button;
    lateinit var refreshButton2: Button;

    companion object {
        var TYPE_REPEAT = 1;
        var TYPE_SUN = 2;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView();
        initRecyclerView();
    }

    private fun initRecyclerView() {
        val list: ArrayList<Int> = arrayListOf()
        for (i in set) {
            for (number in i.iterator()) {
                list.add(number)
            }
        }
        sudokuRv.adapter = SudokuRvAdapter(applicationContext, list)
    }

    private fun initView() {
        sudokuRv = findViewById<RecyclerView>(R.id.sudokuRv);
        refreshButton = findViewById<Button>(R.id.refreshButton);
        refreshButton2 = findViewById<Button>(R.id.refreshButton2);
        sudokuRv.layoutManager = GridLayoutManager(applicationContext, 9)
        refreshButton.setOnClickListener(this)
        refreshButton2.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.refreshButton -> refreshData(1)
            R.id.refreshButton2 -> refreshData(2)
            else -> {

            }
        }
    }

    fun refreshData(type : Int) {
        Toast.makeText(applicationContext, "请稍等，刷新中...", Toast.LENGTH_SHORT).show()
        if(type == TYPE_REPEAT){
            refreshButton.text = "刷新中"
        }else{
            refreshButton2.text = "刷新中"
        }
        refreshButton.isEnabled = false
        refreshButton2.isEnabled = false
        Executors.newCachedThreadPool().execute {
//            var list: ArrayList<Int> = arrayListOf()
            do {
                for (intArray in set) {
                    /*for (number in tempRefresh.iterator()) {
                        list.add(number)
                    }*/
                    var list: IntArray = tempRefresh
                    for (i: Int in 0..8) {
                        intArray[i] = list.random();
                        list = list.remove(intArray[i])
                    }
                }
            } while (check(type))
            runOnUiThread {
                initRecyclerView();
                refreshButton.isEnabled = true
                refreshButton2.isEnabled = true
                if(type == TYPE_REPEAT){
                    refreshButton.text = "刷新按钮(不重复)"
                }else{
                    refreshButton2.text = "刷新按钮(相加等于45)"
                }
            }
        }
    }

    fun check(type : Int): Boolean {
        if(type == TYPE_REPEAT){
            //测试每一列没有重复
            var list: ArrayList <Int> = arrayListOf()
            var tempIndex = 0;
//            for (i in 0..8) {
            for (i in 0..0) {
                list.clear()
                for (intArray in set) {
                    if(!list.contains(intArray[i])){
                        list.add(intArray[i])
                    }else{
                        return true
                    }
                }
            }
            /*//测试下斜线
            tempIndex = 0
            list.clear()
            for (intArray in set) {
                if(!list.contains(intArray[tempIndex])){
                    list.add(intArray[tempIndex])
                    tempIndex++
                }else{
                    return true
                }
            }*/
           /* //测试上斜线
            tempIndex = 8
            list.clear()
            for (intArray in set) {
                if(!list.contains(intArray[tempIndex])){
                    list.add(intArray[tempIndex])
                    tempIndex--
                }else{
                    return true
                }
            }*/
        }else{
            var tempSun = 0
            var tempIndex = 0;
            //测试每一列相加等于45
//        for (i in 0..8) {
            for (i in 0..0) {
                tempSun = 0
                for (intArray in set) {
                    tempSun += intArray[i];
                }
                if (tempSun != 45)
                    return true
            }
            /*//测试下斜线
            tempSun = 0
            tempIndex = 0
            for (intArray in set) {
                tempSun += intArray[tempIndex];
                tempIndex++
            }
            if (tempSun != 45)
                return true
            //测试上斜线
            tempSun = 0
            tempIndex = 8
            for (intArray in set) {
                tempSun += intArray[tempIndex];
                tempIndex--
            }
            if (tempSun != 45)
                return true*/
        }
        return false
    }

    fun IntArray.remove(i: Int): IntArray {
        val temp : ArrayList<Int> = ArrayList()
        for (item in this.iterator()) {
            if(item == i)continue
            temp.add(item)
        }
        return temp.toIntArray()
    }

}


