package demo.kecson.shareapp.module.list

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import demo.kecson.shareapp.BuildConfig
import demo.kecson.shareapp.R
import demo.kecson.shareapp.adapter.AppAdapter
import demo.kecson.shareapp.data.Type
import demo.kecson.shareapp.module.detail.DetailActivity
import java.io.File


class AppsActivity : AppCompatActivity(), AppsContract.AppsView {
    var mAppType = Type.USER_APP
    var mProgressDialog: ProgressDialog? = null
    var mLvApps: ListView? = null
    private var mPresenter: AppsPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps)
        mLvApps = findViewById(R.id.lv_apps) as ListView
        mLvApps?.setOnItemClickListener { parent, view, position, id ->
            val app = parent.getItemAtPosition(position) as PackageInfo
            val builder = AlertDialog.Builder(this@AppsActivity)
            val itemMenu = resources.getStringArray(R.array.item_menu)
            val listener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    0 -> shareApp(app)
                    1 -> viewAppDetail(app)
                }
                dialog.dismiss()

            }
            builder.setItems(itemMenu, listener)
            builder.show()
        }
        mPresenter = AppsPresenter(baseContext, this, supportLoaderManager)
        mPresenter?.start()
        mPresenter?.loadApps(mAppType)
        supportActionBar?.title = resources.getStringArray(R.array.app_types)[mAppType.ordinal]

    }

    private fun viewAppDetail(app: PackageInfo) {
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra("app", app)
        startActivity(intent)

    }

    private fun shareApp(app: PackageInfo) {
        val sourceDir = app.applicationInfo.sourceDir
        val apk = File(sourceDir)
        val intent = Intent(Intent.ACTION_SEND)

        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //申请权限
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            //getUriForFile的第二个参数就是Manifest中的authorities
            FileProvider.getUriForFile(baseContext, "${BuildConfig.APPLICATION_ID}.FileProvider", apk)
        } else {
            Uri.fromFile(apk)
        }

//        val type = contentResolver.getType(uri)
        val type = "application/vnd.android.package-archive"
        intent.type = type
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        val componentName = intent.resolveActivity(packageManager)
        if (componentName != null) {
            startActivity(Intent.createChooser(intent, null))
        }
    }

    override fun onDestroy() {
        mPresenter?.destroy()
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_apps, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu?.add(getString(R.string.detail_info))
        super.onCreateContextMenu(menu, v, menuInfo)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.all_app -> mAppType = Type.ALL_APP
            R.id.system_app -> mAppType = Type.SYSTEM_APP
            R.id.user_app -> mAppType = Type.USER_APP
        }
        supportActionBar?.title = resources.getStringArray(R.array.app_types)[mAppType.ordinal]
        mPresenter?.loadApps(mAppType)
        return super.onOptionsItemSelected(item)
    }

    override fun showProgress() {
        mProgressDialog = ProgressDialog(this@AppsActivity)
        mProgressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mProgressDialog!!.show()
    }

    override fun hideProgress() {
        mProgressDialog?.dismiss()
    }

    override fun loadApps(list: List<PackageInfo>) {
        val adapter = AppAdapter(this@AppsActivity, objects = list)
        mLvApps?.adapter = adapter
    }

}
