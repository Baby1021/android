package com.laiyuanwen.android.baby.ui.pages.anniversary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laiyuanwen.android.baby.bean.Anniversary
import com.laiyuanwen.android.baby.bean.AnniversaryViewData
import com.laiyuanwen.android.baby.repository.AnniversaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*


/**
 * Created by laiyuanwen on 2019-06-06.
 */
class AnniversaryViewModel(val repository: AnniversaryRepository) : ViewModel() {

    val anniversaries: MutableLiveData<List<AnniversaryViewData>> = MutableLiveData()
    val showAddDialog: MutableLiveData<Boolean> = MutableLiveData()

    var year: Int = getInstance().get(YEAR)
    var month: Int = getInstance().get(MONTH)
    var dayOfMonth: Int = getInstance().get(DAY_OF_MONTH)
    var name: String = ""

    init {
        refresh()
    }

    fun updateDate(year: Int, month: Int, dayOfMonth: Int) {
        this.year = year
        this.month = month
        this.dayOfMonth = dayOfMonth
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun submit() {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val result = repository.addAnniversary(name, year, month, dayOfMonth)

                withContext(Dispatchers.Main) {

                    year = getInstance().get(YEAR)
                    month = getInstance().get(MONTH)
                    dayOfMonth = getInstance().get(DAY_OF_MONTH)
                    name = ""

                    refresh()
                    hideDialog()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun hideDialog() {
        showAddDialog.value = false
    }

    fun showDialog() {
        showAddDialog.value = true
    }

    fun refresh() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = repository.getAnniversary()
                withContext(Dispatchers.Main) {
                    anniversaries.value = data.map { item -> toAnniversaryViewData(item) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun toAnniversaryViewData(anniversary: Anniversary) = AnniversaryViewData(
            anniversary.id,
            anniversary.name,
            getBetweenDay(anniversary),
            getStartDay(anniversary),
            getNextTime(anniversary),
            getBetweenYear(anniversary)
    )

    private fun getBetweenYear(anniversary: Anniversary): Int {
        return 1
    }

    private fun getBetweenDay(anniversary: Anniversary): String {
        val distance = System.currentTimeMillis() - anniversary.time
        val day = distance / (1000 * 24 * 60 * 60)
        return day.toInt().toString()
    }

    private fun getStartDay(anniversary: Anniversary): String {
        val date = Date(anniversary.time)
        val format = SimpleDateFormat("YYYY年MM月dd日", Locale.CHINA)
        return format.format(date)
    }

    private fun getNextTime(anniversary: Anniversary): String {
        val calendar = getInstance()
        calendar.time = Date(anniversary.time)

        val nowCalendar = getInstance()
        nowCalendar.time = Date()

        // 今天
        if (calendar[YEAR] == nowCalendar[YEAR] && calendar[MONTH] == nowCalendar[MONTH] && calendar[DAY_OF_MONTH] == nowCalendar[DAY_OF_MONTH]) {
            return "今天"
        }

        while (nowCalendar > calendar) {
            calendar.set(YEAR, calendar[YEAR] + 1)
        }

        val beginTime = nowCalendar.time.time
        val endTime = calendar.time.time
        val betweenDays = ((endTime - beginTime) / (1000 * 60 * 60 * 24) + 0.5).toLong()

        return "还有${betweenDays}天"
    }
}