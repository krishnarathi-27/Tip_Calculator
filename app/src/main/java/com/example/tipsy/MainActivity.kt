package com.example.tipsy

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
private const val INITIAL_TIP_VALUE = 15

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FF6464")))

        skSeekBar.progress = INITIAL_TIP_VALUE
        tvPercentLabel.text = "$INITIAL_TIP_VALUE%"
        updateTipDescription(INITIAL_TIP_VALUE)

        skSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvPercentLabel.text = "$progress%"
                updateTipDescription(progress)
                computeTianTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        etBaseLabel.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {
                computeTianTotal()
            }
        })
    }

    private fun updateTipDescription(tipPercent: Int) {
        val tipDescription : String

        tipDescription = when (tipPercent) {
            in 0..9 -> "Poor"
            in 10..14 -> "Acceptable"
            in 15..19 -> "Good"
            in 20..24 -> "Great"
            else -> "Amazing"
        }
        tvTipDescription.text = tipDescription
        val color = ArgbEvaluator().evaluate(tipPercent.toFloat()/skSeekBar.max,
            ContextCompat.getColor(this,R.color.colorWorstTip),
            ContextCompat.getColor(this,R.color.colorBestTip)
        ) as Int

        tvTipDescription.setTextColor(color)
    }

    private fun computeTianTotal() {

        if(etBaseLabel.text.isEmpty())
        {
            tvTipAmount.text = ""
            tvTotalAmount.text = ""
            return

        }
        val baseAmount = etBaseLabel.text.toString().toDouble()
        val tipPercent = skSeekBar.progress
        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotalAmount.text = "%.2f".format(totalAmount)
    }
}