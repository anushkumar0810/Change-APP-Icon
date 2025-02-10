package com.anush_projects.changeappicon.Adapters

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.anush_projects.changeappicon.MainActivity
import com.anush_projects.changeappicon.Models.AppIconModel
import com.anush_projects.changeappicon.R
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

class AppIconAdapter(private val model: List<AppIconModel>, private val context: Context) :
    RecyclerView.Adapter<AppIconAdapter.ViewHolder>() {

    companion object {
        private const val DEFAULT_LAUNCHER = "com.demo.dynamiclaunchericon.alias.DefaultLauncher"
        private const val RED_LAUNCHER = "com.demo.dynamiclaunchericon.alias.RedLauncher"
        private const val BLUE_LAUNCHER = "com.demo.dynamiclaunchericon.alias.BlueLauncher"
        private const val GLAZE_LAUNCHER = "com.demo.dynamiclaunchericon.alias.GlazeLauncher"
        private const val BLACK_LAUNCHER = "com.demo.dynamiclaunchericon.alias.BlackLauncher"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.icons_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = model[position]
        Glide.with(context)
            .load(item.appIcon)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(holder.iconImg)

        holder.iconName.text = item.name

        holder.itemView.setOnClickListener {
            val launcherAlias = when (position) {
                0 -> DEFAULT_LAUNCHER
                1 -> RED_LAUNCHER
                2 -> BLUE_LAUNCHER
                3 -> GLAZE_LAUNCHER
                4 -> BLACK_LAUNCHER
                else -> DEFAULT_LAUNCHER
            }
            showRestartDialog(launcherAlias)
        }
    }

    override fun getItemCount(): Int = model.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImg: ShapeableImageView = itemView.findViewById(R.id.iconImg)
        val iconName: TextView = itemView.findViewById(R.id.iconName)
    }

    private fun saveThemeColor(color: Int) {
        val prefs: SharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        prefs.edit().putInt("selectedColor", color).apply()
    }

    private fun showRestartDialog(launcherAlias: String) {
        AlertDialog.Builder(context)
            .setTitle("Finish App")
            .setMessage("App needs to be closed to apply the icon change.")
            .setPositiveButton("OK") { _, _ ->
                switchIcon(launcherAlias)
                restartApp()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun switchIcon(newLauncher: String) {
        val pm = context.packageManager
        listOf(DEFAULT_LAUNCHER, RED_LAUNCHER, BLUE_LAUNCHER, GLAZE_LAUNCHER, BLACK_LAUNCHER).forEach {
            pm.setComponentEnabledSetting(
                ComponentName(context, it),
                if (it == newLauncher) PackageManager.COMPONENT_ENABLED_STATE_ENABLED else PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }
        Toast.makeText(context, "Closing The APP...", Toast.LENGTH_SHORT).show()
    }

    private fun restartApp() {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        Runtime.getRuntime().exit(0)
    }
}