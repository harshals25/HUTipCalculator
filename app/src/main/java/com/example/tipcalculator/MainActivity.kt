package com.example.tipcalculator

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.example.tipcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var calculateOnce: Boolean = false
    private var selectedTip: Double = 0.0
    private val mainActivityService = MainActivityService()
    private lateinit var context : Context
    private var billAmount: Double = 0.0
    private var tipAmount: Double = 0.0



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        context = this;


        binding.etBillAmount.doAfterTextChanged {
            if(calculateOnce){
                binding.btnCalculateTip.callOnClick()
            }
        }


        binding.rgTipValues.setOnCheckedChangeListener { _, checkedId ->

            if(checkedId!=-1) {
                binding.etCustomTip.setText("")
                binding.etCustomTip.clearFocus()
                val radioButton = findViewById<RadioButton>(checkedId)
                selectedTip = radioButton.text.toString().toDouble()
                if (calculateOnce)
                    binding.btnCalculateTip.callOnClick()
            }
        }

        binding.btnCalculateTip.setOnClickListener {
            if(binding.etBillAmount.text.toString() == "") {
                Toast.makeText(
                    this,
                    "Please enter bill amount before calculating",
                    Toast.LENGTH_SHORT
                ).show()
                binding.tvTipAmount.text = "Tip will be shown here"
            }
            else {
                calculateOnce = true;
                billAmount = binding.etBillAmount.text.toString().toDouble();
                tipAmount = mainActivityService.calculateTip(billAmount, selectedTip);

                binding.tvTipAmount.text = "$ $tipAmount"
            }
        }



        binding.etCustomTip.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                binding.rgTipValues.clearCheck()
            }
            false
        }


        binding.etCustomTip.doAfterTextChanged {
            if(binding.etCustomTip.text.toString() != "") {
                selectedTip = binding.etCustomTip.text.toString().toDouble();
                if (calculateOnce) {
                    binding.btnCalculateTip.callOnClick()
                }
            }
            else{
                if (calculateOnce)
                    binding.tvTipAmount.text = "Tip will be shown here"
            }
        }

        binding.btnTotalBill.setOnClickListener {
            if(calculateOnce && (binding.tvTipAmount.text != "" || binding.tvTipAmount.text != "Tip will be shown here")
                && binding.etBillAmount.text.toString() != ""){
                val total = mainActivityService.calculateTotal(tipAmount,billAmount)

                val intent =  Intent(this, ResultsActivity::class.java)
                intent.putExtra("totalBill",total)
                intent.putExtra("tipAmount",tipAmount)
                intent.putExtra("billAmount",billAmount)
                startActivity(intent)
            }
            else
                Toast.makeText(context,"Please calculate tip before calculating total",Toast.LENGTH_SHORT).show()

        }


    }

    fun setupDoAfterTextChangedListener(calculateOnce: Boolean) {
        this.calculateOnce = calculateOnce;

    }
}