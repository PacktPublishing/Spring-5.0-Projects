package com.nilangpatel.kotlin.operatoroverload

data class CoordinatePoint(var xPoint: Int, var yPoint: Int){
    // overloading + operator with plus function
    operator fun plus(anotherPoint: CoordinatePoint) : CoordinatePoint {
        return CoordinatePoint(xPoint + anotherPoint.xPoint, yPoint + anotherPoint.yPoint)
    }

    // overloading - operator with minus function
    operator fun minus(anotherPoint: CoordinatePoint) : CoordinatePoint {
        return CoordinatePoint(xPoint - anotherPoint.xPoint, yPoint - anotherPoint.yPoint)
    }
}

fun main(args: Array<String>) {
    var num1 = 10
    var num2 = 5
    println(num1+num2)
    println(num1.plus(num2))

    var point1 = CoordinatePoint(2,5)
    var point2 = CoordinatePoint(4,3)

    var point3 = point1 + point2
    var point4 = point1 - point2

    println(point3)
    println(point4)

}