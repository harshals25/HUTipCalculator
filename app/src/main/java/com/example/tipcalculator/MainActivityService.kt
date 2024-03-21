package com.example.tipcalculator

class MainActivityService{

    constructor()


    fun calculateTip(billAmount: Double, tipPercentage: Double): Double {
        return (tipPercentage * billAmount) / 100;
    }

    fun calculateTotal(billAmount: Double, tipAmount: Double): Double{
        return billAmount+tipAmount;
    }






}