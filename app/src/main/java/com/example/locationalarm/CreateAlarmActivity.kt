package com.example.locationalarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*

class CreateAlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_alarm)

        val locationName = findViewById<TextView>(R.id.txt_location_name)
        locationName.text = intent.extras.getString("name")

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
            val alert = intent.extras.getString("alert")
            if (alert.equals("sound")) {
                soundCheckBox.isChecked = true
            } else if (alert.equals("vibrate")) {
                vibrateCheckBox.isChecked = true
            }
        }

        val btnSave = findViewById<Button>(R.id.btn_save)
        btnSave.isEnabled = (currentRadius > 0) &&
                (soundCheckBox.isChecked || vibrateCheckBox.isChecked) &&
                !alarmName.text.isNullOrEmpty()

        btnSave.setOnClickListener {
            // TODO Save all alarm data
        }
    }
}
