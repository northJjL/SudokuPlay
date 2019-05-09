package com.jjl.sudokuplay

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.util.concurrent.Executors
import kotlin.collections.ArrayList
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

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

    class ResourceID() {
        val sudokuRv: Int = R.id.sudokuRv
        val refreshButton: Int = R.id.refreshButton
        val refreshButton2: Int = R.id.refreshButton2
    }

    companion object {
        var TYPE_REPEAT = 1;
        var TYPE_SUN = 2;
    }

   class MyUI (activity : Activity){
       val sudokuRv by bindResource(activity ,ResourceID())
       val refreshButton by bindResource(activity ,ResourceID())
       val refreshButton2  by bindResource(activity ,ResourceID())
       fun  bindResource(activity: Activity, id: ResourceID): ResourceLoader {
           var res = ResourceLoader(activity ,id);
           return res
       }
   }

    class ResourceLoader(val activity: Activity ,val id: ResourceID) {
        operator fun provideDelegate( thisRef: MyUI, prop: KProperty<*>): ReadOnlyProperty<MyUI, View> {
            if(checkProperty(thisRef, prop.name)){
                return findViewById(activity ,id)
            }else{
                throw Exception("Error ${prop.name}")
            }
        }
        private fun checkProperty(thisRef: MyUI, name: String):Boolean {
            return name.equals("sudokuRv") || name.equals("refreshButton") || name.equals("refreshButton2")
        }
    }

    class findViewById(val context: Activity,val id: ResourceID) : ReadOnlyProperty<MyUI, View> {
        override fun getValue(thisRef: MyUI, property: KProperty<*>): View {
            if(property.name.equals("sudokuRv"))
                return context.findViewById<ImageView>(id.sudokuRv)
            else if(property.name.equals("refreshButton"))
                return context.findViewById<ImageView>(id.refreshButton)
            else
                return context.findViewById<ImageView>(id.refreshButton2)
        }
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
//        sudokuRv = findViewById<RecyclerView>(R.id.sudokuRv);
//        refreshButton = findViewById<Button>(R.id.refreshButton);
//        refreshButton2 = findViewById<Button>(R.id.refreshButton2);
        val ui : MyUI = MyUI(this)
        sudokuRv = ui.sudokuRv as RecyclerView;
        refreshButton = ui.refreshButton as Button;
        refreshButton2 = ui.refreshButton2 as Button;
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
            run(refreshButton ,ui3)
//            refreshButton.text = "刷新中"
        }else{
            run(refreshButton2 ,ui3)
//            refreshButton2.text = "刷新中"
        }
//        refreshButton.isEnabled = false
//        refreshButton2.isEnabled = false
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
//                refreshButton.isEnabled = true
//                refreshButton2.isEnabled = true
                if(type == TYPE_REPEAT){
//                    refreshButton.text = "刷新按钮(不重复)"
                    run(refreshButton ,ui)
                }else{
//                    refreshButton2.text = "刷新按钮(相加等于45)"
                    run(refreshButton2 ,ui2)
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

    // 先封装一个UI操作列表
    class Ui(val uiOps: List<UiOp> = emptyList()) {
        operator fun plus(uiOp: UiOp) = Ui(uiOps + uiOp)
    }

    sealed class UiOp {
        class Enabled(val isEnabled : Boolean): UiOp()
        class Text(val text: String ): UiOp()
    }
    fun execute(view: TextView, op: UiOp) = when (op) {
        is UiOp.Enabled -> view.isEnabled = op.isEnabled
        is UiOp.Text -> view. text = op.text
    }

    // 定义一组操作
    val ui = Ui() +
            UiOp.Enabled(true) +
            UiOp.Text("刷新按钮(不重复)")

    val ui2 = Ui() +
            UiOp.Enabled(true) +
            UiOp.Text("刷新按钮(相加等于45)")

    val ui3= Ui() +
            UiOp.Enabled(false) +
            UiOp.Text("刷新中")

    // 定义调用的函数
    fun run(view: TextView, ui: Ui) {
        ui.uiOps.forEach { execute(view, it) }
    }


    //在扩展函数内， 可以通过 this 来判断接收者是否为 NULL,这样，即使接收者为 NULL,也可以调用扩展函数。例如:
    fun Any?.toString(): String {
        if (this == null) return "null"
        // 空检测之后，“this”会自动转换为非空类型，所以下面的 toString()
        // 解析为 Any 类的成员函数
        return toString()
    }


}


