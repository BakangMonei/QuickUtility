package com.madassignment.quickutility.Fragments


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.madassignment.quickutility.R
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var addEventFab: FloatingActionButton
    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var noEventsText: TextView
    private val eventMap: MutableMap<String, MutableList<String>> = mutableMapOf()
    private lateinit var selectedDate: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        // Initialize Views
        calendarView = view.findViewById(R.id.calendarView)
        addEventFab = view.findViewById(R.id.addEventFab)
        eventsRecyclerView = view.findViewById(R.id.eventsRecyclerView)
        noEventsText = view.findViewById(R.id.noEventsText)

        // Set up RecyclerView
        eventsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        eventsRecyclerView.adapter = EventAdapter(mutableListOf())

        // Set up CalendarView to handle date selection
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        selectedDate = dateFormat.format(calendarView.date)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = String.format("%02d-%02d-%d", dayOfMonth, month + 1, year)
            loadEventsForSelectedDate()
        }

        // Handle FAB click to add new events
        addEventFab.setOnClickListener {
            showAddEventDialog()
        }

        return view
    }

    private fun loadEventsForSelectedDate() {
        val eventsForDate = eventMap[selectedDate] ?: mutableListOf()
        if (eventsForDate.isNotEmpty()) {
            noEventsText.visibility = View.GONE
            eventsRecyclerView.visibility = View.VISIBLE
            eventsRecyclerView.adapter = EventAdapter(eventsForDate)
        } else {
            noEventsText.visibility = View.VISIBLE
            eventsRecyclerView.visibility = View.GONE
        }
    }

    private fun showAddEventDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Event")

        val input = EditText(requireContext())
        input.hint = "Event description"
        builder.setView(input)

        builder.setPositiveButton("Add") { dialog, _ ->
            val eventDescription = input.text.toString().trim()
            if (eventDescription.isNotEmpty()) {
                addEventToSelectedDate(eventDescription)
                Toast.makeText(requireContext(), "Event added for $selectedDate", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun addEventToSelectedDate(eventDescription: String) {
        val eventsForDate = eventMap[selectedDate] ?: mutableListOf()
        eventsForDate.add(eventDescription)
        eventMap[selectedDate] = eventsForDate
        loadEventsForSelectedDate()
    }

    // Adapter for displaying events in RecyclerView
    inner class EventAdapter(private val events: List<String>) :
        RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
            return EventViewHolder(view)
        }

        override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
            holder.eventText.text = events[position]
        }

        override fun getItemCount(): Int = events.size

        inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val eventText: TextView = view.findViewById(android.R.id.text1)
        }
    }
}
