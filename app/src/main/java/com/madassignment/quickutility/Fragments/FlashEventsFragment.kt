package com.madassignment.quickutility.Fragments


import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.madassignment.quickutility.R

class FlashEventsFragment : Fragment() {

    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var addEventFab: FloatingActionButton
    private val eventList = mutableListOf<FlashEvent>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flash_events, container, false)

        // Initialize views
        eventsRecyclerView = view.findViewById(R.id.flashEventsRecyclerView)
        addEventFab = view.findViewById(R.id.addEventFab)

        // Set up RecyclerView with adapter
        eventsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        eventsRecyclerView.adapter = FlashEventAdapter(eventList, ::onEditEvent, ::onDeleteEvent)

        // Handle FAB click to add a new event
        addEventFab.setOnClickListener {
            showAddEventDialog()
        }

        return view
    }

    private fun showAddEventDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Event")

        // Input fields for title and time
        val inputLayout = LayoutInflater.from(context).inflate(R.layout.dialog_add_event, null)
        val titleInput = inputLayout.findViewById<EditText>(R.id.titleInput)
        val timeInput = inputLayout.findViewById<EditText>(R.id.timeInput)

        builder.setView(inputLayout)

        builder.setPositiveButton("Add") { dialog, _ ->
            val title = titleInput.text.toString().trim()
            val time = timeInput.text.toString().trim()

            if (title.isNotEmpty() && time.isNotEmpty()) {
                val newEvent = FlashEvent(title, time)
                eventList.add(newEvent)
                eventsRecyclerView.adapter?.notifyDataSetChanged()
                Toast.makeText(requireContext(), "Event added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please enter both title and time", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun onEditEvent(position: Int) {
        val event = eventList[position]
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Edit Event")

        val inputLayout = LayoutInflater.from(context).inflate(R.layout.dialog_add_event, null)
        val titleInput = inputLayout.findViewById<EditText>(R.id.titleInput)
        val timeInput = inputLayout.findViewById<EditText>(R.id.timeInput)

        titleInput.setText(event.title)
        timeInput.setText(event.time)

        builder.setView(inputLayout)

        builder.setPositiveButton("Save") { dialog, _ ->
            val title = titleInput.text.toString().trim()
            val time = timeInput.text.toString().trim()

            if (title.isNotEmpty() && time.isNotEmpty()) {
                eventList[position] = FlashEvent(title, time)
                eventsRecyclerView.adapter?.notifyDataSetChanged()
                Toast.makeText(requireContext(), "Event updated", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun onDeleteEvent(position: Int) {
        eventList.removeAt(position)
        eventsRecyclerView.adapter?.notifyDataSetChanged()
        Toast.makeText(requireContext(), "Event deleted", Toast.LENGTH_SHORT).show()
    }

    data class FlashEvent(val title: String, val time: String)

    // RecyclerView Adapter for Flash Events
    class FlashEventAdapter(
        private val events: List<FlashEvent>,
        private val onEditEvent: (Int) -> Unit,
        private val onDeleteEvent: (Int) -> Unit
    ) : RecyclerView.Adapter<FlashEventAdapter.FlashEventViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashEventViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_flash_event, parent, false)
            return FlashEventViewHolder(view)
        }

        override fun onBindViewHolder(holder: FlashEventViewHolder, position: Int) {
            val event = events[position]
            holder.bind(event, onEditEvent, onDeleteEvent, position)
        }

        override fun getItemCount(): Int = events.size

        inner class FlashEventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val eventTitle: TextView = view.findViewById(R.id.eventTitle)
            private val eventTime: TextView = view.findViewById(R.id.eventTime)
            private val editButton: Button = view.findViewById(R.id.btnEdit)
            private val deleteButton: Button = view.findViewById(R.id.btnDelete)

            fun bind(event: FlashEvent, onEditEvent: (Int) -> Unit, onDeleteEvent: (Int) -> Unit, position: Int) {
                eventTitle.text = event.title
                eventTime.text = event.time

                editButton.setOnClickListener {
                    onEditEvent(position)
                }

                deleteButton.setOnClickListener {
                    onDeleteEvent(position)
                }
            }
        }
    }
}
