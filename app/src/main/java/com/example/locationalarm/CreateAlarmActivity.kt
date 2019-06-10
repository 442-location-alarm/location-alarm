package com.example.locationalarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.text.TextWatcher



class CreateAlarmActivity : AppCompatActivity() {

    val NOTIFICATION_ID = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_alarm)

        // Database
        val db = AlarmDatabase.getInstance(this)

        // Views
        val btnSave = findViewById<Button>(R.id.btn_save)
        val locationName = findViewById<TextView>(R.id.txt_location_name)
        val btnDelete = findViewById<Button>(R.id.btn_delete)
        val alarmName = findViewById<EditText>(R.id.edit_txt_alarmName)
        val txtRadius = findViewById<TextView>(R.id.txt_radius)
        val radiusSlider = findViewById<SeekBar>(R.id.slider)
        val soundCheckBox = findViewById<CheckBox>(R.id.sound_checkbox)
        val vibrateCheckBox = findViewById<CheckBox>(R.id.vibrate_checkbox)
        val btnEditLocation = findViewById<Button>(R.id.btn_edit_location)

        val extras = intent.extras

        var alert = ""

        var alarm: Alarm

        // Edit alarm
        if (intent.hasExtra("edit")) {
            // Can delete
            btnDelete.visibility = View.VISIBLE

            alarm = extras.getParcelable("alarm") as Alarm

        // Creating a new alarm
        } else {
            // Cannot delete
            btnDelete.visibility = View.GONE


            val address = extras.getString("address")
            val latitude = extras.getDouble("latitude")
            val longitude = extras.getDouble("longitude")

            alarm = Alarm(name = "", location = address, latitude = latitude, longitude = longitude, radius = 0.0, alert = "")
        }

        // Nickname
        alarmName.text = SpannableStringBuilder(alarm.name)

        // Location
        locationName.text = alarm.location

        // Radius (slider and text)
        val radius = alarm.radius.toInt()
        radiusSlider.progress = radius
        txtRadius.text = "$radius mile radius"

        // Alert checkboxes
        val _alert = alarm.alert
        if (_alert.equals("sound")) {
            soundCheckBox.isChecked = true
        } else if (_alert.equals("vibrate")) {
            vibrateCheckBox.isChecked = true
        }

        // Slider listener
        radiusSlider.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                txtRadius.text = "$progress mile radius"
                alarm.radius = progress.toDouble()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Does nothing, is a prefab function that has to be here I think
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Does nothing, is a prefab function that has to be here I think
            }
        })

        // Checkbox listeners
        soundCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                alarm.alert = "sound"
            }
        }
        vibrateCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                alarm.alert = "vibrate"
            }
        }

        // Nickname listener
        alarmName.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                alarm.name = s.toString()
            }
        })

        btnEditLocation.setOnClickListener {
            // send alarm data to search activity
            val intent = Intent(this@CreateAlarmActivity, SearchActivity::class.java)
            intent.putExtra("alarm", alarm)
            intent.putExtra("edit", 1)
            startActivity(intent)
        }

        btnSave.setOnClickListener {
            if (alarmName.text.toString().equals("") || radiusSlider.progress == 0 || (!soundCheckBox.isChecked && !vibrateCheckBox.isChecked)) {
                Toast.makeText(this,"One or more of the fields is not filled out!", Toast.LENGTH_LONG).show()
            } else {
                if (intent.hasExtra("edit")) {
                    AsyncTask.execute {
                        db.alarmDao().update(alarm)
                    }

                    Toast.makeText(this, "Alarm Updated!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@CreateAlarmActivity, AlarmListActivity::class.java)
                    startActivity(intent)
                } else {
                    AsyncTask.execute {
                        db.alarmDao().insert(alarm)
                    }
                    Toast.makeText(this, "Alarm Saved!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@CreateAlarmActivity, AlarmListActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        btnDelete.setOnClickListener {
            AsyncTask.execute {
                db.alarmDao().delete(alarm.uid)
            }
            var notificationManager: NotificationManager

            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "LocationAlarm"
                val descriptionText = "Proximity alert for when you are in the radius of a desired location."
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(ProximityIntentReceiver.CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                notificationManager =
                    this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            } else {
                notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            notificationManager.cancel(NOTIFICATION_ID)

            Toast.makeText(this, "Alarm Deleted!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@CreateAlarmActivity, AlarmListActivity::class.java)
            startActivity(intent)
        }
    }
}
