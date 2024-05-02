package com.example.retrofit_dust_prac

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.retrofit_dust_prac.databinding.ActivityMainBinding
import com.skydoves.powerspinner.IconSpinnerAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var items = mutableListOf<DustItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.spinnerViewSido.setOnSpinnerItemSelectedListener<String> { i: Int, s: String?, i1: Int, s1: String ->
            communicateNetWork(setUpDustParameter(s1))
        }

        binding.spinnerViewGoo.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            var selectedItem = items.filter{f -> f.stationName==newItem}

            binding.tvCityname.text = selectedItem[0].sidoName + selectedItem[0].stationName
            binding.tvDate.text = selectedItem[0].dataTime
            binding.tvP10value.text = selectedItem[0].pm10Value + "㎍/㎥"


            when(getGrade(selectedItem[0].pm10Value)){
                1 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#9ED2EC"))
                    binding.ivFace.setImageResource(R.drawable.mise1)
                    binding.tvP10grade.text = "좋음"
                }

                2 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#D6A478"))
                    binding.ivFace.setImageResource(R.drawable.mise2)
                    binding.tvP10grade.text = "보통"
                }

                3 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#DF7766"))
                    binding.ivFace.setImageResource(R.drawable.mise3)
                    binding.tvP10grade.text = "나쁨"
                }

                4 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#BB3320"))
                    binding.ivFace.setImageResource(R.drawable.mise4)
                    binding.tvP10grade.text = "매우나쁨"
                }
            }
        }
    }

    private fun communicateNetWork(param: HashMap<String, String>) = lifecycleScope.launch {
        val responseData = NetWorkClient.dustNetWork.getDust(param)
        Log.d("Parsing Dust ::", responseData.toString())

        val adapter = IconSpinnerAdapter(binding.spinnerViewGoo)
        items = responseData.response.dustBody.dustItem!!

        val goo = ArrayList<String>()
        items.forEach{
            Log.d("add Item: ", it.stationName)
            goo.add(it.stationName)
        }

        runOnUiThread {
            binding.spinnerViewGoo.setItems(goo)
        }
    }

    private fun setUpDustParameter(sido: String): HashMap<String, String> {
        val authKey =
            "q7XKXFpXwRaMHo8TbVYuPmkRYTvNmIc+qL4ruM/Q6ZnmR9vSdokCvgJISp1m7kOmvyBngXzMhsSz6ZWwJfqy8w=="

        return hashMapOf(
            "serviceKey" to authKey,
            "returnType" to "json",
            "numOfRows" to "100",
            "pageNo" to "1",
            "sidoName" to sido,
            "ver" to "1.0"
        )
    }

    fun getGrade(value: String): Int {
        val mValue = value.toInt()
        var grade = 1
        grade = if (mValue >= 0 && mValue <= 30) {
            1
        } else if (mValue >= 31 && mValue <= 80) {
            2
        } else if (mValue >= 81 && mValue <= 100) {
            3
        } else 4
        return grade
    }
}