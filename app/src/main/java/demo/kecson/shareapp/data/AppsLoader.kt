package demo.kecson.shareapp.data

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.support.v4.content.AsyncTaskLoader


/**
 * To do
 * @author      chenKeSheng
 * @date        2017-09-14 20:15
 * @version     V1.0
 */
class AppsLoader : AsyncTaskLoader<List<PackageInfo>> {
    var mType: Type

    constructor(context: Context?, type: Type) : super(context) {
        mType = type
    }

    override fun loadInBackground(): List<PackageInfo> {
        val appList = context.packageManager.getInstalledPackages(0)
        val iterator = appList.iterator()

        when (mType) {
            Type.SYSTEM_APP -> {
                appList.removeAll {
                    (it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0
                }
            }

            Type.USER_APP -> {
                appList.removeAll {
                    (it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
                }
            }
        }

        appList.sortWith(Comparator { o1, o2 ->
            val label1 = o1.applicationInfo.loadLabel(context.packageManager).toString()
            val label2 = o2.applicationInfo.loadLabel(context.packageManager).toString()
            return@Comparator label1.compareTo(label2)
        })

        return appList
    }

    override fun deliverResult(data: List<PackageInfo>?) {
        super.deliverResult(data)
    }

    override fun forceLoad() {
        super.forceLoad()
    }

    override fun onStartLoading() {
        forceLoad()
        super.onStartLoading()
    }

    override fun stopLoading() {
        super.stopLoading()
    }

}