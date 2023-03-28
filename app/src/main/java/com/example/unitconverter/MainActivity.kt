package com.example.unitconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val format = "%.3f"

    private lateinit var unitInput: EditText
    private lateinit var metersValue: TextView
    private lateinit var kilometersValue: TextView
    private lateinit var centimetersValue: TextView
    private lateinit var feetsValue: TextView
    private lateinit var milesValue: TextView
    private lateinit var inchesValue: TextView
    private lateinit var unitSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        unitInput = findViewById(R.id.unit_input)
        metersValue = findViewById(R.id.meters_value)
        kilometersValue = findViewById(R.id.kilometers_value)
        centimetersValue = findViewById(R.id.centimeters_value)
        feetsValue = findViewById(R.id.feets_value)
        milesValue = findViewById(R.id.miles_value)
        inchesValue = findViewById(R.id.inches_value)

        unitSpinner = findViewById(R.id.unit_spinner)
        unitSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.unit_array,
            android.R.layout.simple_spinner_item
        )

        unitInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                updateConvertedValues()
            }
        })

        unitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                updateConvertedValues()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.locale_en -> {
                println("en")
                resources.configuration.setLocale(Locale.ENGLISH)
                recreate()
                true
            }
            R.id.locale_ru -> {
                println("ru")
                resources.configuration.setLocale(Locale("ru"))
                recreate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun convertValue(value: Double, fromUnit: String, toUnit: String): Double {
        // Define conversion factors for each unit
        val factors = mapOf(
            "kilometers" to 1000.0,
            "meters" to 1.0,
            "centimeters" to 0.01,
            "miles" to 1609.34,
            "feet" to 0.3048,
            "inches" to 0.0254
        )

        // Convert input value to meters
        val metersValue = value * factors[fromUnit]!!

        // Convert meters to output unit

        return metersValue / factors[toUnit]!!
    }

    private fun updateConvertedValues() {
        val inputValue = unitInput.text.toString().toDoubleOrNull() ?: 0.0
        val inputUnit = when (unitSpinner.selectedItemPosition) {
            0 -> "kilometers"
            1 -> "meters"
            2 -> "centimeters"
            3 -> "miles"
            4 -> "feet"
            5 -> "inches"
            else -> "meters" // Default to meters if no unit is selected
        }

        // Update text views with converted values
        kilometersValue.text = format.format(convertValue(inputValue, inputUnit, "kilometers"))
        metersValue.text = format.format(convertValue(inputValue, inputUnit, "meters"))
        centimetersValue.text = format.format(convertValue(inputValue, inputUnit, "centimeters"))
        milesValue.text = format.format(convertValue(inputValue, inputUnit, "miles"))
        feetsValue.text = format.format(convertValue(inputValue, inputUnit, "feet"))
        inchesValue.text = format.format(convertValue(inputValue, inputUnit, "inches"))
    }
}