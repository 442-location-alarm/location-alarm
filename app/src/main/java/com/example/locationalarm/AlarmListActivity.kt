package com.example.locationalarm

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AlarmListActivity : AppCompatActivity(), AlarmAdapter.UpdateListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: AlarmAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var alarmViewModel: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_list)

//        val db = AlarmDatabase.getInstance(this)
//        val alarm = Alarm("UW", "University of Washington", 2.0, "vibrate")
//        val alarm2 = Alarm("Home", "123 Test Ln NE", 2.0, "vibrate")
//        alarm2.disable()
//
//        AsyncTask.execute {
//            db.alarmDao().insert(alarm)
//            db.alarmDao().insert(alarm2)
//        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = AlarmAdapter(this)

        alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel::class.java)
        alarmViewModel.getAll().observe(this, Observer<List<Alarm>>{ alarms -> viewAdapter.setData(alarms) })

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val search = Intent(this@AlarmListActivity, SearchActivity::class.java)
            startActivity(search)
        }
    }

    override fun onUpdate(alarm: Alarm) {
        alarmViewModel.updateAlarm(alarm)
    }

}
