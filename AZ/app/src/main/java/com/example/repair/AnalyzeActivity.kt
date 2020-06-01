package com.example.repair

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.repair.data.LoginDataSource
import com.example.repair.data.model.Stop
import com.example.repair.ui.dashboard.DashboardViewModel
import com.example.repair.ui.notifications.NotificationsViewModel
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.renderer.PieChartRenderer
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
            if (stops.size == 0) {
                Toast.makeText(this, "暂时没有信息", Toast.LENGTH_LONG).show();
                return
            }
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
                return
            }


            val pieEntry1 = PieEntry((a * 1.0f / stops.size * 100).toInt().toFloat(), "动力")
            val pieEntry2 = PieEntry((b * 1.0f / stops.size * 100).toInt().toFloat(), "传动")
            val pieEntry3 = PieEntry((a * 1.0f / stops.size * 100).toInt().toFloat(), "执行")
            val pieEntry4 = PieEntry((d * 1.0f / stops.size * 100).toInt().toFloat(), "控制")

            val list = mutableListOf(pieEntry1, pieEntry2, pieEntry3, pieEntry4)

            val list1 = mutableListOf<PieEntry>()
            for (i in list) {
                if (i.value != 0.0f) {
                    list1.add(i)
                }
            }

            val pieDataSet = PieDataSet(list1, "")

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
            var c = 0.0f
            var qqqqqq =
                ViewModelProvider(application as App).get(DashboardViewModel::class.java).checks.value!!
            if (qqqqqq.size == 0) {
                Toast.makeText(this, "暂时没有信息", Toast.LENGTH_LONG).show();
                return
            }
            var b: Float = 0.0f
            for (i in qqqqqq) {
                if (i.comment != null) {
                    if (i.comment!!.startsWith("正常")) {
                        a++;
                    } else if (i.comment!!.startsWith("异常")) {
                        b++;
                    } else {
                        c++;
                    }
                }

            }


            val pieEntry1 = PieEntry((a * 1.0f / (a + b + c) * 100).toInt().toFloat(), "停运")
            val pieEntry2 = PieEntry((b * 1.0f / (a + b + c) * 100).toInt().toFloat(), "正常")
            val pieEntry3 = PieEntry((c * 1.0f / (a + b + c) * 100).toInt().toFloat(), "异常")
            val list = mutableListOf(pieEntry1, pieEntry2, pieEntry3)
            val list1 = mutableListOf<PieEntry>()
            for (i in list) {
                if (i.value != 0.0f) {
                    list1.add(i)
                }
            }

            val pieDataSet = PieDataSet(list1, "")

            //一般有多少项数据，就配置多少个颜色的，少的话会复用最后一个颜色，多的话无大碍
            pieDataSet.colors = mutableListOf(
                Color.parseColor("#feb64d"),
                Color.parseColor("#9287e7"),
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
            if (stops.size == 0) {
                Toast.makeText(this, "暂时没有信息", Toast.LENGTH_LONG).show();
                return
            }
            var a = 0.0f;
            var b = 0.0f
            var d = 0.0f
            var c = 0.0f
            var e = 0.0f
            for (i in stops) {
                if (i.downTime == null) {
                    a++
                } else if (i.downTime > 60) {
                    b++
                } else if (i.downTime > 30) {
                    d++
                } else if (i.downTime > 10) {
                    e++
                } else {
                    c++
                }
            }

            val pieEntry1 = PieEntry((a * 1.0f / stops.size * 100).toInt().toFloat(), "仍在停机")
            val pieEntry2 = PieEntry((b * 1.0f / stops.size * 100).toInt().toFloat(), "大于60分钟")
            val pieEntry3 = PieEntry((c * 1.0f / stops.size * 100).toInt().toFloat(), "小于10分钟")
            val pieEntry4 = PieEntry((d * 1.0f / stops.size * 100).toInt().toFloat(), "30到60分钟")
            val pieEntry5 = PieEntry((d * 1.0f / stops.size * 100).toInt().toFloat(), "10到30分钟")

            val list = mutableListOf(pieEntry1, pieEntry2, pieEntry3, pieEntry4, pieEntry5)

            val list1 = mutableListOf<PieEntry>()
            for (i in list) {
                if (i.value != 0.0f) {
                    list1.add(i)
                }
            }

            val pieDataSet = PieDataSet(list1, "")

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
        } else {


            pieChart.setNoDataText("加载数据中")
            if (stops.size == 0) {
                Toast.makeText(this, "暂时没有信息", Toast.LENGTH_LONG).show();
                return
            }
            var length = MyUser.depts.size
            var jjj = Array(length) { it -> 0 }
            val listss =
                ViewModelProvider(application as App).get(NotificationsViewModel::class.java).devices.value!!
            for (i in stops) {
                for (ii in listss) {
                    if (i.device == ii.id) {
                        for (iii in 0..MyUser.depts.size - 1) {
                            if (MyUser.depts[iii] == ii.dept) {
                                jjj[iii]++;
                                break;
                            }
                        }
                        break
                    }
                }
            }

            var list = ArrayList<PieEntry>();

            for (iii in 0..MyUser.depts.size - 1) {
                list.add(
                    PieEntry(
                        ((1.0f * jjj[iii] / stops.size) * 100).toInt().toFloat(),
                        MyUser.depts[iii]
                    )
                )
            }

//            val pieEntry1 = PieEntry((a * 1.0f / stops.size * 100).toInt().toFloat(), "仍在停机")
//            val pieEntry2 = PieEntry((b * 1.0f / stops.size * 100).toInt().toFloat(), "大于500分钟")
//            val pieEntry3 = PieEntry((c * 1.0f / stops.size * 100).toInt().toFloat(), "小于100分钟")
//            val pieEntry4 = PieEntry((d * 1.0f / stops.size * 100).toInt().toFloat(), "大于100分钟")
//
//            val list = mutableListOf(pieEntry1, pieEntry2, pieEntry3, pieEntry4)

            val list1 = mutableListOf<PieEntry>()
            for (i in list) {
                if (i.value != 0.0f) {
                    list1.add(i)
                }
            }

            val pieDataSet = PieDataSet(list1, "")

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
