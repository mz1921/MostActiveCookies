import org.example.*
import org.junit.jupiter.api.Test
import org.opentest4j.AssertionFailedError
import kotlin.test.assertEquals
import kotlin.test.assertFails

class MainTest {
    @Test
    fun canParseDate() {
        val dateString1 = "2023-05-09"
        val dateString2 = "2017-12-23"
        val dateString3 = "2012-03-16"
        assertEquals(Date(2023, 5, 9), parseDate(dateString1))
        assertEquals(Date(2017, 12, 23), parseDate(dateString2))
        assertEquals(Date(2012, 3, 16), parseDate(dateString3))
    }

    @Test
    fun throwsExceptionIfCannotParseDate() {
        val dateString1 = "20230509"
        val dateString2 = "207c-ab-23"

        assertFails { parseDate(dateString1) }
        assertFails { parseDate(dateString2) }
    }

    @Test
    fun canParseLog() {
        val input1 = "AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00"
        val input2 = "SAZuXPGUrfbcn5UA,2017-05-14T10:13:00+00:00"
        assertEquals(Log("AtY0laUfhglK3lC7", Date(2018, 12, 9), Time(14, 19, 0)), parseToLog(input1))
        assertEquals(Log("SAZuXPGUrfbcn5UA", Date(2017, 5, 14), Time(10, 13, 0)), parseToLog(input2))
    }

    @Test
    fun canParseFile() {
        val actualLogs = parseFile("src/test/resources/test1.csv")
        val expectedLogs = listOf(
            Log("AtY0lapgkrlK3lC7", Date(2004, 11, 1), Time(12, 23, 0)),
            Log("S42gmq3fjsirl24j", Date(2015, 2, 9), Time(17, 19, 10)),
            Log("PgnrA24Nqr2kfn5A", Date(2023, 6, 19), Time(10, 14, 0))
        )
        assertEquals(expectedLogs, actualLogs)
    }

    @Test
    fun canParseFileAndFindMostActiveCookie() {
        val logs = parseFile("src/test/resources/test2.csv")
        assertEquals(listOf("AtY0laUfhglK3lC7", "SAZuXPGUrfbcn5UA"), mostActiveCookies(logs, Date(2018, 12, 9)))
        assertEquals(
            listOf("SAZuXPGUrfbcn5UA", "4sMM2LxV07bPJzwf", "fbcn5UAVanZf6UtG"),
            mostActiveCookies(logs, Date(2018, 12, 8)),
        )
        assertEquals(listOf("4sMM2LxV07bPJzwf"), mostActiveCookies(logs, Date(2018, 12, 7)))
    }

    @Test
    fun canParseCommandLineInputOrThrowsException() {
        val input1 = arrayOf("-f", "cookie_log.csv", "-d", "2018-12-09")
        val input2 = arrayOf("-f", "hello_world.txt", "-d", "2019-11-19")
        val input3 = arrayOf("-x", "hello_world.txt", "-d", "2019-11-19")
        val input4 = arrayOf("-f", "-d", "2019-11-19\n")
        assertEquals(Pair("cookie_log.csv", Date(2018, 12, 9)), parseCommandLine(input1))
        assertEquals(Pair("hello_world.txt", Date(2019, 11, 19)), parseCommandLine(input2))
        assertFails { parseCommandLine(input3) }
        assertFails { parseCommandLine(input4) }
    }
}