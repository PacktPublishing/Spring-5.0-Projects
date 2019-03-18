package com.nilangpatel.kotlin.function.lamda

data class Applicant(
                    var applicantId: Int,
                    var name: String,
                    var age: Int,
                    var gender: String)

fun isEligibleForLoan (mobileNo:String, eligibilityScore:(applicantId:Int)->Double) : Boolean{
    //Business logic to fetch applicant details from given mobileNo
    var applicant = Applicant(12,"Nilang",38,"M");
    var score = eligibilityScore(applicant.applicantId);
    return score >80
}

fun main(args: Array<String>) {

   var isEligible = isEligibleForLoan("9998789671",{
        applicantId ->
        //Write logic to calculate the eligibility of candidate and return the score
        85.23
    })

    var isEligible2 = isEligibleForLoan("9998789671"){
            applicantId ->
        //Write logic to calculate the eligibility of candidate and return the score
        75.23
    }

    println(" isEligibile: $isEligible ")
    println(" isEligibile2: $isEligible2 ")
}