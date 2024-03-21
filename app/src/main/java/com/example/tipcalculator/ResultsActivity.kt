package com.example.tipcalculator

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.tipcalculator.databinding.ActivityResultsBinding
import kotlin.system.exitProcess

class ResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding
    private lateinit var context : Context
    private val resultActivityService = ResultsActivityService()
    private var totalBill:Double = 0.0
    private var onlyTip:Double = 0.0
    private var onlyBill:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        context = this;

        totalBill = intent.getDoubleExtra("totalBill",0.0)
        onlyTip = intent.getDoubleExtra("tipAmount",0.0)
        onlyBill= intent.getDoubleExtra("billAmount",0.0)

        binding.tvTotalBill.text = "Total Bill is $$totalBill"
        binding.tvTotalTip.text = "Only Tip is $$onlyTip"
        binding.tvBillAmount.text = "Only bill is $$onlyBill"



        binding.etTipSplit.doAfterTextChanged {
            if(binding.etTipSplit.text.toString() != "") {
                val parts = binding.etTipSplit.text.toString().toInt()
                if (resultActivityService.checkValidParts(parts)) {
                    val result = resultActivityService.splitValues(onlyTip, parts)
                    binding.tvSplitTipAmount.text = "$$result per person"
                } else {
                    Toast.makeText(
                        context,
                        "Invalid partition, please select b/w 1 to 4",
                        Toast.LENGTH_SHORT
                    ).show();
                    binding.tvSplitTipAmount.text = "Invalid"
                }
            }
            else{
                binding.tvSplitTipAmount.text = "$$"
            }
        }

        binding.etBillSplit.doAfterTextChanged {
            if(binding.etBillSplit.text.toString() != "") {
                val parts = binding.etBillSplit.text.toString().toInt()
                if (resultActivityService.checkValidParts(parts)) {
                    val result = resultActivityService.splitValues(onlyBill, parts)
                    binding.tvSplitBillAmount.text = "$$result per person"
                } else {
                    Toast.makeText(
                        context,
                        "Invalid partition, please select b/w 1 to 4",
                        Toast.LENGTH_SHORT
                    ).show();
                    binding.tvSplitBillAmount.text = "Invalid"
                }
            }
            else{
                binding.tvSplitBillAmount.text = "$$"
            }
        }

        binding.etTotalSplit.doAfterTextChanged {
            if(binding.etTotalSplit.text.toString() != "") {
                val parts = binding.etTotalSplit.text.toString().toInt()
                if (resultActivityService.checkValidParts(parts)) {
                    val result = resultActivityService.splitValues(totalBill, parts)
                    binding.tvSplitTotalAmount.text = "$$result per person"
                } else {
                    Toast.makeText(
                        context,
                        "Invalid partition, please select b/w 1 to 4",
                        Toast.LENGTH_SHORT
                    ).show();
                    binding.tvSplitTotalAmount.text = "Invalid"
                }
            }
            else{
                binding.tvSplitTotalAmount.text = "$$"
            }
        }

        binding.tvEditTipBill.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnEnterNew.setOnClickListener {
            val intent =  Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



    }
}