package com.example.locationalarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AlarmListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_list)
        //val db = AlarmDatabase.getInstance(this)
        val list = mutableListOf<Alarm>()
        val alarm = Alarm("UW", "University of Washington", 2.0, "vibrate")
        val alarm2 = Alarm("Home", "123 Test Ln NE", 2.0, "vibrate")
        alarm2.disable()
        list.add(alarm)
        list.add(alarm2)

        viewManager = LinearLayoutManager(this)
        viewAdapter = AlarmAdapter(list)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }
    // ...
}
