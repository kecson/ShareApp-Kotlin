package demo.kecson.shareapp.module.detail

import android.content.pm.PackageInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import demo.kecson.shareapp.R
import demo.kecson.shareapp.utils.TimeUtil

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)

        val app = intent.getParcelableExtra<PackageInfo>("app")

        val ivIcon = findViewById(R.id.iv_icon) as ImageView
        val tvAppName = findViewById(R.id.tv_name) as TextView
        val tvVersion = findViewById(R.id.tv_version) as TextView
        val tvPackage = findViewById(R.id.tv_package_name) as TextView
        val tvInstallTime = findViewById(R.id.tv_first_install_time) as TextView
        val tvUpdateTime = findViewById(R.id.tv_last_update_time) as TextView

        ivIcon.setImageDrawable(app.applicationInfo.loadIcon(packageManager))
        tvAppName.text = app.applicationInfo.loadLabel(packageManager)
        tvVersion.text = app.versionName
        tvPackage.text = app.packageName
        tvInstallTime.text = TimeUtil.formatTime(app.firstInstallTime, "yyyy-MM-dd HH:mm:ss")
        tvUpdateTime.text = TimeUtil.formatTime(app.lastUpdateTime, "yyyy-MM-dd HH:mm:ss")

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
