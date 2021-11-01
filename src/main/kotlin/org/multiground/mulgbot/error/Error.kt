package org.multiground.mulgbot.error


import com.kotlindiscord.kord.extensions.DISCORD_RED
import dev.kord.rest.builder.message.EmbedBuilder
import kotlinx.datetime.Instant
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.Error


sealed class Error {
    object NoValueFound: Error("값을 발견할 수 없습니다!")
    object NoTypeFound: Error("존재하지 않는 타입입니다!")
}

fun errorOccur(e: Error, time: Instant, additional: String? = null): EmbedBuilder {
    val res =EmbedBuilder()
    res.title = "에러가 발생하였습니다!"
    res.description = "**${e.message}**\n" +
            "${shortenedStackTrace(e, 6)}\n" +
            "그리고 더..." +
            if(additional != null) "\n"+additional else ""
    res.timestamp = time
    res.color = DISCORD_RED

    e.printStackTrace()
    return res
}


private fun shortenedStackTrace(e: Error,  maxLines: Int): String {
    val writer = StringWriter()
    e.printStackTrace(PrintWriter(writer))
    val lines = writer.toString().split("\n".toRegex()).toTypedArray()
    val sb = StringBuilder()
    for (i in 0 until lines.size.coerceAtMost(maxLines)) {
        sb.append("> ").append(lines[i]).append("\n")
    }
    return sb.toString()
}