package org.multiground.mulgbot.meta

class NewsLists {
    private val newsList: List<String> = listOf(
        "업데이트",
    )

    fun findNews(name: String): Boolean{
        return newsList.contains(name)
    }
}