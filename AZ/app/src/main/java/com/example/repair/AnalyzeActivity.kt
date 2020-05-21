package com.example.repair

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.repair.data.LoginDataSource
import com.example.repair.data.model.Stop
import com.example.repair.ui.notifications.NotificationsViewModel
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.main.activity_analyze.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnalyzeActivity : AppCompatActivity() {


    var stops: MutableList<Stop> = ArrayList()
    var state: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyze)
        CoroutineScope(Dispatchers.Main).launch {

            val _list = withContext(Dispatchers.IO) {

                LoginDataSource().getStop()
            }
            stops.addAll(_list)
            changeState()
        }
        err.setOnClickListener {
            state = 1
            changeState()
        }
        device.setOnClickListener {
            state = 2
            changeState()
        }
        time.setOnClickListener {
            state = 3
            changeState()
        }
        dept.setOnClickListener {
            state = 4
            changeState()
        }


    }

    fun changeState() {
        if (state == 1) {
            pieChart.setNoDataText("加载数据中")

            var a = 0;
            var b = 0;
            var c = 0;
            var d = 0
            for (i in stops) {
                if (i.comment.equals("动力")) {
                    a++
                } else if (i.comment.equals("传动")) {
                    b++;
                } else if (i.comment.equals("执行")) {
                    c++;
                } else {
                    d++;
                }
            }
            if (stops.size == 0) {
                Toast.makeText(this, "暂时没有信息", Toast.LENGTH_LONG).show();
                finish()
            }


            val pieEntry1 = PieEntry((a * 1.0f / stops.size * 100).toInt().toFloat(), "动力")
            val pieEntry2 = PieEntry((b * 1.0f / stops.size * 100).toInt().toFloat(), "传动")
            val pieEntry3 = PieEntry((a * 1.0f / stops.size * 100).toInt().toFloat(), "执行")
            val pieEntry4 = PieEntry((d * 1.0f / stops.size * 100).toInt().toFloat(), "控制")

            val list = mutableListOf(pieEntry1, pieEntry2, pieEntry3, pieEntry4)

            val pieDataSet = PieDataSet(list, "")

            //一般有多少项数据，就配置多少个颜色的，少的话会复用最后一个颜色，多的话无大碍
            pieDataSet.colors = mutableListOf(
                Color.parseColor("#feb64d"),
                Color.parseColor("#ff7c7c"),
                Color.parseColor("#9287e7"),
                Color.parseColor("#60acfc")
            )

            val pieData = PieData(pieDataSet!!)

            pieChart.data = pieData
            //显示值格式化，这里使用Api,添加百分号
            pieData.setValueFormatter(PercentFormatter())
            //设置值得颜色
            pieData.setValueTextColor(Color.parseColor("#FFFFFF"))
            //设置值得大小
            pieData.setValueTextSize(10f)

            val description = Description()

            description.text = ""
            //把右下边的Description label 去掉，同学也可以设置成饼图说明
            pieChart.description = description

            //去掉中心圆，此时中心圆半透明
            pieChart.holeRadius = 0f
            //去掉半透明
            pieChart.setTransparentCircleAlpha(0)

            pieChart.setDrawEntryLabels(true)

            pieChart.invalidate()
        } else if (state == 2) {
            pieChart.setNoDataText("加载数据中")

            var a = 0.0f;
            var b: Float =
                ViewModelProvider(application as App).get(NotificationsViewModel::class.java).devices.value!!.size + 0.0f
            for (i in stops) {
                if (i.downTime == null || i.downTime == 0) {
                    a++
                }
            }
            b = b - a


            val pieEntry1 = PieEntry((a * 1.0f / (a + b) * 100).toInt().toFloat(), "停运")
            val pieEntry2 = PieEntry((b * 1.0f / (a + b) * 100).toInt().toFloat(), "正常")

            val list = mutableListOf(pieEntry1, pieEntry2)

            val pieDataSet = PieDataSet(list, "")

            //一般有多少项数据，就配置多少个颜色的，少的话会复用最后一个颜色，多的话无大碍
            pieDataSet.colors = mutableListOf(
                Color.parseColor("#feb64d"),
                Color.parseColor("#ff7c7c")
            )

            val pieData = PieData(pieDataSet!!)

            pieChart.data = pieData
            //显示值格式化，这里使用Api,添加百分号
            pieData.setValueFormatter(PercentFormatter())
            //设置值得颜色
            pieData.setValueTextColor(Color.parseColor("#FFFFFF"))
            //设置值得大小
            pieData.setValueTextSize(10f)

            val description = Description()

            description.text = ""
            //把右下边的Description label 去掉，同学也可以设置成饼图说明
            pieChart.description = description

            //去掉中心圆，此时中心圆半透明
            pieChart.holeRadius = 0f
            //去掉半透明
            pieChart.setTransparentCircleAlpha(0)

            pieChart.setDrawEntryLabels(true)

            pieChart.invalidate()
        } else if (state == 3) {
            pieChart.setNoDataText("加载数据中")

            var a = 0.0f;
            var b = 0.0f
            var d = 0.0f
            var c = 0.0f
            for (i in stops) {
                if (i.downTime == null) {
                    a++
                } else if (i.downTime > 500) {
                    b++
                } else if (i.downTime > 100) {
                    d++
                } else {
                    c++
                }
            }

            val pieEntry1 = PieEntry((a * 1.0f / stops.size * 100).toInt().toFloat(), "仍在停机")
            val pieEntry2 = PieEntry((b * 1.0f / stops.size * 100).toInt().toFloat(), "大于500分钟")
            val pieEntry3 = PieEntry((c * 1.0f / stops.size * 100).toInt().toFloat(), "小于100分钟")
            val pieEntry4 = PieEntry((d * 1.0f / stops.size * 100).toInt().toFloat(), "大于100分钟")

            val list = mutableListOf(pieEntry1, pieEntry2, pieEntry3, pieEntry4)

            val pieDataSet = PieDataSet(list, "")

            //一般有多少项数据，就配置多少个颜色的，少的话会复用最后一个颜色，多的话无大碍
            pieDataSet.colors = mutableListOf(
                Color.parseColor("#feb64d"),
                Color.parseColor("#ff7c7c"),
                Color.parseColor("#9287e7"),
                Color.parseColor("#60acfc")
            )
            val pieData = PieData(pieDataSet!!)

            pieChart.data = pieData
            //显示值格式化，这里使用Api,添加百分号
            pieData.setValueFormatter(PercentFormatter())
            //设置值得颜色
            pieData.setValueTextColor(Color.parseColor("#FFFFFF"))
            //设置值得大小
            pieData.setValueTextSize(10f)

            val description = Description()

            description.text = ""
            //把右下边的Description label 去掉，同学也可以设置成饼图说明
            pieChart.description = description

            //去掉中心圆，此时中心圆半透明
            pieChart.holeRadius = 0f
            //去掉半透明
            pieChart.setTransparentCircleAlpha(0)

            pieChart.setDrawEntryLabels(true)

            pieChart.invalidate()
        }
    }


}
