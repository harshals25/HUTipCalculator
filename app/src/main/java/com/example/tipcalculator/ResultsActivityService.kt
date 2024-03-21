package com.example.tipcalculator

class ResultsActivityService {

    fun splitValues(amount : Double, parts : Int) : Double{
        return amount/parts
    }

    fun checkValidParts(parts : Int): Boolean{
        return parts in 1..4
    }
}