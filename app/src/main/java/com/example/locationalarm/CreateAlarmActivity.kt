package com.example.locationalarm

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CreateAlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_alarm)

        val btnSave = findViewById<Button>(R.id.btn_save)
        var alert = ""
        var currentRadius = 0

        val extras = intent.extras
        val name = extras.getString("name")
        val address = extras.getString("address")
        val latitude = extras.getDouble("latitude")
        val longitude = extras.getDouble("longitude")

        val locationName = findViewById<TextView>(R.id.txt_location_name)
        //if there is no name, use the address
        if (name == "") {
            locationName.text = address
        } else {
            locationName.text = name
        }

        val btnEditLocation = findViewById<Button>(R.id.btn_edit_location)
        btnEditLocation.setOnClickListener {
            val intent = Intent(this@CreateAlarmActivity, SearchActivity::class.java)
            startActivity(intent)
        }

        val btnDelete = findViewById<Button>(R.id.btn_delete)

        val alarmName = findViewById<EditText>(R.id.edit_txt_alarmName)
        if (intent.hasExtra("alarmName")) {
            alarmName.text = SpannableStringBuilder(intent.extras.getString("alarmName"))
            btnDelete.visibility = View.VISIBLE
            Log.d("DeleteVis", btnDelete.visibility.toString())
        } else {
            btnDelete.visibility = View.GONE
        }

        if (!alert.isBlank() && currentRadius > 0) {
            btnSave.isEnabled = true
        }

        val txtRadius = findViewById<TextView>(R.id.txt_radius)

        val radiusSlider = findViewById<SeekBar>(R.id.slider)
        if (intent.hasExtra("radius")) {
            val radius = intent.extras.getDouble("radius").toInt()
            radiusSlider.progress = radius
            txtRadius.text = "$radius mile radius"
        }
        radiusSlider.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                txtRadius.text = "$progress mile radius"
                currentRadius = progress

                if (!alert.isBlank() && !alarmName.text.isNullOrBlank()) {
                    btnSave.isEnabled = true
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Does nothing, is a prefab function that has to be here I think
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Does nothing, is a prefab function that has to be here I think
            }
        })

        val soundCheckBox = findViewById<CheckBox>(R.id.sound_checkbox)

        val vibrateCheckBox = findViewById<CheckBox>(R.id.vibrate_checkbox)

        if (intent.hasExtra("alert")) {
            val _alert = intent.extras.getString("alert")
            if (_alert.equals("sound")) {
                soundCheckBox.isChecked = true
            } else if (_alert.equals("vibrate")) {
                vibrateCheckBox.isChecked = true
            }
        }

        soundCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                alert = "sound"

                if (currentRadius > 0 && !alarmName.text.isNullOrBlank()) {
                    btnSave.isEnabled = true
                }
            }
        }

        vibrateCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                alert = "vibrate"

                if (currentRadius > 0 && !alarmName.text.isNullOrBlank()) {
                    btnSave.isEnabled = true
                }
            }
        }

        Log.d("SaveEnable", btnSave.isEnabled.toString())

        val db = AlarmDatabase.getInstance(this)
        btnSave.setOnClickListener {
            if (intent.hasExtra("alarmId")) {
                if (!alarmName.text.toString().equals(intent.extras.getString("alarmName"))) {
                    AsyncTask.execute {
                        db.alarmDao()
                            .updateName(alarmID = intent.extras.getString("alarmId"), name = alarmName.text.toString())
                    }
                }

                if (!currentRadius.toDouble().equals(intent.extras.getDouble("radius"))) {
                    AsyncTask.execute {
                        db.alarmDao()
                            .updateRadius(alarmID = intent.extras.getString("alarmId"), radius = currentRadius.toDouble())
                    }
                }

                if (!alert.equals(intent.extras.getString("alert"))) {
                    AsyncTask.execute {
                        db.alarmDao()
                            .updateAlert(alarmID = intent.extras.getString("alarmId"), alert = alert)
                    }
                }
                Toast.makeText(this, "Alarm Updated!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@CreateAlarmActivity, AlarmListActivity::class.java)
                startActivity(intent)
            } else {
                val alarm = Alarm(alarmName.text.toString(), address, currentRadius.toDouble(), alert, latitude, longitude)
                Log.d("AlarmCreate", alarm.name)
                AsyncTask.execute {
                    db.alarmDao().insert(alarm)
                }
                Toast.makeText(this, "Alarm Saved!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@CreateAlarmActivity, AlarmListActivity::class.java)
                startActivity(intent)
            }
        }

        btnDelete.setOnClickListener {
            var alarmId = ""
            if (intent.hasExtra("alarmId")) {
                alarmId = intent.extras.getString("alarmId")
            }
            AsyncTask.execute {
                db.alarmDao().delete(alarmId)
            }
            Toast.makeText(this, "Alarm Deleted!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@CreateAlarmActivity, AlarmListActivity::class.java)
            startActivity(intent)
        }
    }
}
