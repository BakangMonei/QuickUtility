package com.madassignment.quickutility.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.madassignment.quickutility.R

class ConverterFragment : Fragment() {

    private lateinit var inputValue: EditText
    private lateinit var fromUnitSpinner: Spinner
    private lateinit var toUnitSpinner: Spinner
    private lateinit var resultTextView: TextView

    private val lengthUnits = mapOf("Meters" to 1.0, "Kilometers" to 1000.0, "Miles" to 1609.34)
    private val weightUnits = mapOf("Grams" to 1.0, "Kilograms" to 1000.0, "Pounds" to 453.592)
    private val temperatureUnits = mapOf("Celsius" to 1.0, "Fahrenheit" to 33.8) // Simplified

    private var currentUnitType: Map<String, Double> = lengthUnits

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_converter, container, false)

        inputValue = view.findViewById(R.id.inputValue)
        fromUnitSpinner = view.findViewById(R.id.fromUnitSpinner)
        toUnitSpinner = view.findViewById(R.id.toUnitSpinner)
        resultTextView = view.findViewById(R.id.resultTextView)

        // Setup dropdown menus (spinners) for conversion types
        setupSpinners()

        // Listen for input changes and trigger real-time conversion
        inputValue.addTextChangedListener {
            performConversion()
        }

        return view
    }

    private fun setupSpinners() {
        // Array adapter for dropdown menu (Spinner)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            currentUnitType.keys.toList()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set adapter for both spinners
        fromUnitSpinner.adapter = adapter
        toUnitSpinner.adapter = adapter

        // Perform conversion when unit selections change
        fromUnitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                performConversion()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        toUnitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                performConversion()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun performConversion() {
        val input = inputValue.text.toString()

        if (input.isNotEmpty()) {
            val fromUnit = fromUnitSpinner.selectedItem.toString()
            val toUnit = toUnitSpinner.selectedItem.toString()

            val fromFactor = currentUnitType[fromUnit] ?: 1.0
            val toFactor = currentUnitType[toUnit] ?: 1.0

            val value = input.toDouble()
            val result = value * fromFactor / toFactor

            // Display the result
            resultTextView.text = result.toString()

            // Show confirmation toast
            Toast.makeText(requireContext(), "Conversion complete!", Toast.LENGTH_SHORT).show()
        } else {
            resultTextView.text = "Please enter a value."
        }
    }
}
