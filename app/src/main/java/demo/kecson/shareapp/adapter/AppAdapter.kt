package demo.kecson.shareapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import demo.kecson.shareapp.R
import demo.kecson.shareapp.utils.FileUtil
import demo.kecson.shareapp.utils.TimeUtil
import java.io.File

@Suppress("ConvertSecondaryConstructorToPrimary")
/**
 * App列表adapter
 * @author      chenKeSheng
 * @date        2017-09-13 17:37
 * @version     V1.0
 */
class AppAdapter : ArrayAdapter<PackageInfo> {
    val mResource: Int = R.layout.item_app
    val mInflater: LayoutInflater

    constructor(context: Context?, resource: Int = R.layout.item_app, objects: List<PackageInfo>?) : super(context, R.layout.item_app, objects) {
        mInflater = LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView: View
        val holder: Holder
        if (convertView == null) {
            itemView = mInflater.inflate(mResource, parent, false) as View
            holder = Holder(itemView)
            itemView.tag = holder

        } else {
            itemView = convertView as View
            holder = itemView.tag as Holder
        }

        bindData(position, holder)
        return itemView
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(position: Int, holder: Holder) {
        val app = getItem(position)
        val loadIcon = app.applicationInfo.loadIcon(context.packageManager)
        holder.ivIcon.setImageDrawable(loadIcon)
        holder.tvName.text = app.applicationInfo.loadLabel(context.packageManager)
        val apk = File(app.applicationInfo.sourceDir)
        holder.tvSize.text = FileUtil.getDataSize(apk.length())
        holder.tvSize.append("(${app.versionName})")

        holder.tvInstallTime.text = context.resources.getString(R.string.install_time) + ":" + TimeUtil.formatTime(app.firstInstallTime)
        holder.tvUpdateTime.text = context.resources.getString(R.string.update_time) + ":" + TimeUtil.formatTime(app.lastUpdateTime)
        holder.tvInstallTime.visibility=View.GONE
        holder.tvUpdateTime.visibility=View.GONE
    }

    class Holder(itemView: View) {
        val ivIcon: ImageView = itemView.findViewById(R.id.iv_icon) as ImageView
        val tvName: TextView = itemView.findViewById(R.id.tv_name) as TextView
        val tvSize: TextView = itemView.findViewById(R.id.tv_size) as TextView

        val tvInstallTime: TextView = itemView.findViewById(R.id.tv_install_time) as TextView
        val tvUpdateTime: TextView = itemView.findViewById(R.id.tv_update_time) as TextView
    }
}