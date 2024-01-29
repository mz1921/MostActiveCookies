package org.example

import kotlin.math.min

typealias Cookie = String

class Log(val cookie: Cookie, val date: Date, val time: Time) {
    override fun equals(other: Any?): Boolean {
        if (other is Log && other.cookie == cookie && other.date == date && other.time == time) {
            return true
        } else return false
    }

    override fun toString(): String {
        return "${cookie}, ${date}, $time"
    }

    override fun hashCode(): Int {
        return date.day + time.hour
    }
}

class Date(val year: Int, val month: Int, val day: Int) {
    override fun equals(other: Any?): Boolean {
        if (other is Date && other.year == year && other.month == month && other.day == day) {
            return true
        } else return false
    }

    override fun toString(): String {
        return "${year}-${month}-${day}"
    }

    override fun hashCode(): Int {
        return year + month + day
    }
}

class Time(val hour: Int, val minute: Int, val second: Int) {
    override fun equals(other: Any?): Boolean {
        if (other is Time && other.hour == hour && other.minute == minute && other.second == second) {
            return true
        } else return false
    }

    override fun toString(): String {
        return "${hour}:${minute}:${second}"
    }

    override fun hashCode(): Int {
        return hour + minute + second
    }
}