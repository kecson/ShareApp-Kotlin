package demo.kecson.shareapp.utils

import java.text.DecimalFormat

/**
 * Kotlin中庸object修饰类名，该类下的方法都是静态方法
 * @author      chenKeSheng
 * @date        2017-09-13 21:16
 * @version     V1.0
 */
object FileUtil {
    /**
     * 返回byte的数据大小对应的文本
     * @param size
     * @return
     */
    fun getDataSize(size: Long): String {
        val formater = DecimalFormat("####.00")
        var formatSize = ""

        formatSize = when {
            size < 1024 -> "$size bytes"
            size < 1024 * 1024 -> "${formater.format(size / 1024f)} KB"
            size < 1024 * 1024 * 1024 -> "${formater.format(size / 1024f / 1024f)} MB"
            size < 1024 * 1024 * 1024 * 1024 -> "${formater.format(size / 1024f / 1024f / 1024f)} GB"
            else -> return "size: error"
        }

        return formatSize

    }
}