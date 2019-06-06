package com.example.locationalarm

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CreateAlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_alarm)

        val extras = intent.extras
        val name = extras.getString("name")
        val address = extras.getString("address")
        val latitude = extras.getDouble("latitude")
        val longitude = extras.getDouble("longitude")

        val locationName = findViewById<TextView>(R.id.txt_location_name)
        //if there is no name, use the address
        if (name.equals("")) {
            locationName.text = intent.extras.getString("name")
        } else {
            locationName.text = intent.extras.getString("address")
        }

        val btnEditLocation = findViewById<Button>(R.id.btn_edit_location)
        btnEditLocation.setOnClickListener {
            val intent = Intent(this@CreateAlarmActivity, SearchActivity::class.java)
            startActivity(intent)
        }

        val btnDelete = findViewById<Button>(R.id.btn_delete)

        val alarmName = findViewById<EditText>(R.id.edit_txt_alarmName)
        if (intent.hasExtra("alarmName")) {
            alarmName.text = intent.extras.getString("alarmName") as Editable
            btnDelete.visibility = View.VISIBLE
        } else {
            btnDelete.visibility = View.GONE
        }

        val txtRadius = findViewById<TextView>(R.id.txt_radius)

        var currentRadius = 0

        // TODO finish implementing the slider logic
        val radiusSlider = findViewById<SeekBar>(R.id.slider)
        radiusSlider.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                txtRadius.text = "$progress mile radius"
                currentRadius = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

        var alert = ""
        if (soundCheckBox.isChecked) {
            alert = "sound"
        } else if (vibrateCheckBox.isChecked) {
            alert = "vibrate"
        }

        val btnSave = findViewById<Button>(R.id.btn_save)
        btnSave.isEnabled = (currentRadius > 0) &&
                (soundCheckBox.isChecked || vibrateCheckBox.isChecked) &&
                !alarmName.text.isNullOrEmpty()

        btnSave.setOnClickListener {
            // TODO uncomment when latlng have been implemented in Alarm class
//            Alarm(name, address, currentRadius.toDouble(), alert, latitude, longitude)
        }
    }
}
