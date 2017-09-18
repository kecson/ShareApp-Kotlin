package demo.kecson.shareapp.module.list

import android.content.pm.PackageInfo
import android.os.Bundle
import android.support.v4.content.Loader
import demo.kecson.shareapp.data.Type

/**
 * To do
 * @author      chenKeSheng
 * @date        2017-09-13 18:19
 * @version     V1.0
 */
interface AppsContract {
    interface AppsView {
        fun showProgress()
        fun hideProgress()
        fun loadApps(list: List<PackageInfo>)
    }

    interface Presenter {
        fun start()
        fun destroy()


        fun onCreateLoader(id: Int, args: Bundle?): Loader<List<PackageInfo>>
        fun loadApps(type: Type)
    }
}