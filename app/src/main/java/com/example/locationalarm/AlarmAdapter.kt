package com.example.locationalarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.alarm_text_view.view.*


class AlarmAdapter(var updateListener: UpdateListener) :
    RecyclerView.Adapter<AlarmAdapter.MyViewHolder>() {

    interface UpdateListener {
        fun onUpdate(alarm: Alarm)
    }

    private var data: List<Alarm> = listOf()

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val NOTIFICATION_ID = 2000

        fun bindView(alarm: Alarm, updateListener: UpdateListener) {
            itemView.name.text = alarm.name
            itemView.location.text = alarm.location
            itemView.simpleSwitch.setChecked(alarm.active)
            itemView.simpleSwitch.setOnCheckedChangeListener{ _, isChecked ->
                val intent = Intent(itemView.context, ProximityNotificationsService::class.java)
                intent.putExtra("radius", alarm.radius)
                intent.putExtra("name", alarm.name)
                intent.putExtra("latitude", alarm.latitude)
                intent.putExtra("longitude", alarm.longitude)
                var notificationManager: NotificationManager

                // Create the NotificationChannel, but only on API 26+ because
                // the NotificationChannel class is new and not in the support library
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val name = Resources.getSystem().getString(R.string.channel_name)
                    val descriptionText = Resources.getSystem().getString(R.string.channel_description)
                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                    val channel = NotificationChannel(ProximityIntentReceiver.CHANNEL_ID, name, importance).apply {
                        description = descriptionText
                    }
                    // Register the channel with the system
                    notificationManager =
                        itemView.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(channel)
                } else {
                    notificationManager = itemView.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                }
                if (isChecked) {
                    itemView.context.startService(intent)
                    // persistent notification
                    val notification = NotificationCompat.Builder(itemView.context, ProximityIntentReceiver.CHANNEL_ID)
                        .setContentText("${alarm.name} is currently enabled")
                        .setOnlyAlertOnce(true)
                        .setOngoing(true)

                    with(NotificationManagerCompat.from(itemView.context)) {
                        notify(NOTIFICATION_ID, notification.build())
                    }
                    Log.i("AlarmList", "enabled")
                } else {
                    itemView.context.stopService(intent)
                    notificationManager.cancel(NOTIFICATION_ID)
                    Log.i("AlarmList","disabled")
                }
                updateListener.onUpdate(alarm)
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, CreateAlarmActivity::class.java)
                intent.putExtra("alarmName", alarm.name)
                intent.putExtra("radius", alarm.radius)
                intent.putExtra("alert", alarm.alert)
                intent.putExtra("location", alarm.location)

                itemView.context.startActivity(intent)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): AlarmAdapter.MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.alarm_text_view, parent, false)
        // set the view's size, margins, padding and layout parameters
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bindView(data[position], updateListener)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size

    fun setData(newData: List<Alarm>) {
        this.data = newData
        notifyDataSetChanged()
    }
}