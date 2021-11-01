package org.multiground.mulgbot.extensions

import arrow.core.Either
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import dev.kord.common.entity.Snowflake
import dev.kord.rest.builder.message.create.embed
import org.multiground.mulgbot.module.ExtType
import org.multiground.mulgbot.utils.CfLoader

class CoreExtension: Extension() {

    override val name: String
        get() = "core"

    override suspend fun setup() {
        publicSlashCommand {
            name = "ping"
            description = "pong!"
            guild(Snowflake("661514065679482900"))

            action {
                val pingMessage = CfLoader().message(ExtType.CORE, "ping")
                respond { when(pingMessage) {
                    is Either.Left -> embed {
                        title = "에러가 발생하였습니다!"
                        description = "`${pingMessage.value.message}`"
                    }
                    is Either.Right -> {
                        content = pingMessage.value
                    }
                } }
            }
        }
    }
}