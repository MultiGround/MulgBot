package org.multiground.mulgbot.core

import com.kotlindiscord.kord.extensions.DISCORD_BLURPLE
import com.kotlindiscord.kord.extensions.ExtensibleBot
import dev.kord.common.entity.PresenceStatus
import dev.kord.core.supplier.EntitySupplyStrategy
import org.multiground.mulgbot.extensions.CoreExtension
import org.multiground.mulgbot.extensions.NewsExtension

suspend fun main() {
    val client = ExtensibleBot(System.getenv("dicoBot")) {
        cache {
            cachedMessages = 10_000
            defaultStrategy =  EntitySupplyStrategy.cacheWithCachingRestFallback
        }
        extensions {
            add(::CoreExtension)
            add(::NewsExtension)
            help {
                pingInReply = false

                color { DISCORD_BLURPLE }
            }
        }

        presence  {
            status = PresenceStatus.Online
            playing("멀그봇")
        }

    }
    client.start()
}