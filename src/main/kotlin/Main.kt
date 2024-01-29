package org.example

import java.io.File

fun parseDate(str: String): Date {
    val stringSplit = str.split('-')
    if (stringSplit.size < 3) {
        throw NumberFormatException()
    }
    val year: Int = stringSplit[0].toInt()
    val month: Int = stringSplit[1].toInt()
    val day: Int = stringSplit[2].toInt()
    return Date(year, month, day)
}

fun parseTime(str: String): Time {
    val timeStringSplit = str.split(':')
    val hour: Int = timeStringSplit[0].toInt()
    val minute: Int = timeStringSplit[1].toInt()
    val second: Int = timeStringSplit[2].toInt()
    return Time(hour, minute, second)
}

fun parseToLog(str: String): Log {
    val cookie = str.substringBefore(',')

    val dateString: String = str.substringAfter(',').substringBefore('T')
    val date = parseDate(dateString)

    val timeString: String = str.substringAfter('T').substringBefore('+')
    val time = parseTime(timeString)

    return Log(cookie, date, time)
}

fun parseFile(fileName: String): List<Log> {
    val logs = mutableListOf<Log>()
    try {
        File(fileName).forEachLine {
            try {
                logs.add(parseToLog(it))
            } catch (_: NumberFormatException) {
                // ignore if line cannot be parsed
            }
        }
    } catch (e: Exception) {
        println("Failed to open file ${fileName}")
    }

    return logs
}

fun mostActiveCookies(logs: List<Log>, date: Date): List<Cookie> {
    val cookieFrequency: Map<Cookie, Int> = logs.filter { it.date == date }.groupingBy { it.cookie }.eachCount()
    try {
        val maxFrequency: Int = cookieFrequency.maxOf { it.value }
        val mostActiveCookies = cookieFrequency.filterValues { it == maxFrequency }.map { it.key }
        return mostActiveCookies
    } catch (e: NoSuchElementException) {
        println("No log on given date exists!")
        return emptyList()
    }
}

fun parseCommandLine(args: Array<String>): Pair<String, Date> {
    if (args.size < 4) {
        throw IllegalArgumentException("File name or date not specified")
    }
    val argMap = args.toList().chunked(2).associate { it[0] to it[1] }
    if (argMap.containsKey("-f") && argMap.containsKey("-d")) {
        return Pair(argMap["-f"]!!, parseDate(argMap["-d"]!!))
    } else throw IllegalArgumentException("File name or date not specified")
}

fun main(args: Array<String>) {
    try {
        val (filename, date) = parseCommandLine(args)
        val logs = parseFile("src/main/resources/${filename}")
        println(mostActiveCookies(logs, date).joinToString("\n"))
    } catch (e: NumberFormatException) {
        println("Cannot parse date entered!")
    } catch (e: IllegalArgumentException) {
        println("File name or date not specified")
    }
}