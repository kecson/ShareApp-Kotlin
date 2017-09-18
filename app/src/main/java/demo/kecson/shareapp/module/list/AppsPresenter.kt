package demo.kecson.shareapp.module.list

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import demo.kecson.shareapp.data.AppsLoader
import demo.kecson.shareapp.data.Type
import java.lang.ref.WeakReference

class AppsPresenter : AppsContract.Presenter, LoaderManager.LoaderCallbacks<List<PackageInfo>> {
    val mContext: Context
    val mRefView: WeakReference<AppsContract.AppsView>
    val mLoaderManager: LoaderManager
    val APP_LOADER_ID = 0


    constructor(context: Context, appsView: AppsContract.AppsView, loaderManager: LoaderManager) {
        mContext = context.applicationContext
        mRefView = WeakReference<AppsContract.AppsView>(appsView)
        mLoaderManager = loaderManager
    }


    override fun start() {
    }

    override fun destroy() {
        mRefView.clear()
    }

    override fun loadApps(type: Type) {
        val bd = Bundle()
        bd.putSerializable("type", type)
        val loader = mLoaderManager.getLoader<List<PackageInfo>>(APP_LOADER_ID)
        if (loader?.isStarted == true) {
            mLoaderManager.restartLoader(APP_LOADER_ID, bd, this)
        } else {
            mLoaderManager.initLoader(APP_LOADER_ID, bd, this)
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<PackageInfo>> {
        mRefView.get()?.showProgress()
        val type = args?.getSerializable("type") as Type
        return AppsLoader(mContext, type)
    }

    override fun onLoaderReset(loader: Loader<List<PackageInfo>>?) {
        loader?.forceLoad()
    }

    override fun onLoadFinished(loader: Loader<List<PackageInfo>>?, data: List<PackageInfo>?) {
        mRefView.get()?.loadApps(data ?: emptyList())
        mRefView.get()?.hideProgress()
    }

}