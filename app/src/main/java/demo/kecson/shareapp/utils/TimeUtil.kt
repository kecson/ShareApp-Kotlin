package demo.kecson.shareapp.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * To do
 * @author      chenKeSheng
 * @date        2017-09-14 00:22
 * @version     V1.0
 */
object TimeUtil {
    fun formatTime(time: Long, temp: String = "yyyy-MM-dd"): String = SimpleDateFormat(temp, Locale.getDefault()).format(Date(time)).toString()

}