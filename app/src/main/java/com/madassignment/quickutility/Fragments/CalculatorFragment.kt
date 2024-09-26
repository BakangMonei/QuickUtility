package com.madassignment.quickutility.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.madassignment.quickutility.R
import kotlin.math.*

class CalculatorFragment : Fragment() {

    private lateinit var display: EditText
    private var currentInput = ""
    private var operator = ""
    private var firstValue = 0.0
    private var isScientific = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)
        display = view.findViewById(R.id.display)

        val toggleButton: Button = view.findViewById(R.id.btnToggleMode)
        val scientificPanel: GridLayout = view.findViewById(R.id.scientificPanel)

        toggleButton.setOnClickListener {
            isScientific = !isScientific
            scientificPanel.visibility = if (isScientific) View.VISIBLE else View.GONE
            toggleButton.text = if (isScientific) "Simple Mode" else "Scientific Mode"
        }

        // Number Buttons
        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        buttons.forEach { id ->
            view.findViewById<Button>(id).setOnClickListener {
                currentInput += (it as Button).text
                display.setText(currentInput)
            }
        }

        // Operation Buttons
        view.findViewById<Button>(R.id.btnAdd).setOnClickListener { performOperation("+") }
        view.findViewById<Button>(R.id.btnSubtract).setOnClickListener { performOperation("-") }
        view.findViewById<Button>(R.id.btnMultiply).setOnClickListener { performOperation("*") }
        view.findViewById<Button>(R.id.btnDivide).setOnClickListener { performOperation("/") }

        // Equals Button
        view.findViewById<Button>(R.id.btnEquals).setOnClickListener { calculateResult() }

        // Clear Button
        view.findViewById<Button>(R.id.btnClear).setOnClickListener {
            currentInput = ""
            operator = ""
            firstValue = 0.0
            display.setText("")
            Toast.makeText(requireContext(), "Calculator reset!", Toast.LENGTH_SHORT).show()
        }

        // Dot Button
        view.findViewById<Button>(R.id.btnDot).setOnClickListener {
            if (!currentInput.contains(".")) {
                currentInput += "."
                display.setText(currentInput)
            }
        }

        // Scientific Buttons
        view.findViewById<Button>(R.id.btnSin).setOnClickListener { performScientificOperation("sin") }
        view.findViewById<Button>(R.id.btnCos).setOnClickListener { performScientificOperation("cos") }
        view.findViewById<Button>(R.id.btnTan).setOnClickListener { performScientificOperation("tan") }
        view.findViewById<Button>(R.id.btnLog).setOnClickListener { performScientificOperation("log") }
        view.findViewById<Button>(R.id.btnSqrt).setOnClickListener { performScientificOperation("sqrt") }
        view.findViewById<Button>(R.id.btnPower).setOnClickListener { performScientificOperation("^") }
        view.findViewById<Button>(R.id.btnPi).setOnClickListener {
            currentInput += PI.toString()
            display.setText(currentInput)
        }
        view.findViewById<Button>(R.id.btnFactorial).setOnClickListener { performScientificOperation("!") }

        return view
    }

    private fun performOperation(op: String) {
        if (currentInput.isNotEmpty()) {
            firstValue = currentInput.toDouble()
            currentInput = ""
            operator = op
        }
    }

    private fun calculateResult() {
        if (currentInput.isNotEmpty() && operator.isNotEmpty()) {
            val secondValue = currentInput.toDouble()
            val result = when (operator) {
                "+" -> firstValue + secondValue
                "-" -> firstValue - secondValue
                "*" -> firstValue * secondValue
                "/" -> firstValue / secondValue
                else -> 0.0
            }
            display.setText(result.toString())
            currentInput = result.toString()
            operator = ""
            Toast.makeText(requireContext(), "Calculation complete!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun performScientificOperation(op: String) {
        if (currentInput.isNotEmpty()) {
            val value = currentInput.toDouble()
            val result = when (op) {
                "sin" -> sin(Math.toRadians(value))
                "cos" -> cos(Math.toRadians(value))
                "tan" -> tan(Math.toRadians(value))
                "log" -> log10(value)
                "sqrt" -> sqrt(value)
                "^" -> value.pow(2)
                "!" -> factorial(value.toInt())
                else -> value
            }
            display.setText(result.toString())
            currentInput = result.toString()
        }
    }

    private fun factorial(n: Int): Double {
        return if (n <= 1) 1.0 else n * factorial(n - 1)
    }
}
