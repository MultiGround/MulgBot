package org.multiground.mulgbot.extensions

import arrow.core.Either
import com.kotlindiscord.kord.extensions.DiscordRelayedException
import com.kotlindiscord.kord.extensions.commands.Argument
import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.application.slash.*
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingString
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import com.kotlindiscord.kord.extensions.types.respondPublic
import dev.kord.common.entity.Snowflake
import dev.kord.rest.builder.message.create.embed
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.multiground.mulgbot.error.errorOccur
import org.multiground.mulgbot.meta.NewsLists
import org.multiground.mulgbot.utils.CfLoader

class NewsExtension: Extension() {
    override val name: String
        get() = "news"

    override suspend fun setup() {
        publicSlashCommand {
            name = "newsletter"
            description = "서버 알림 관련"
            guild(Snowflake("661514065679482900"))

            publicSubCommand {
                name = "list"
                description = "구독 가능 목록 확인하기"


                action{
                    respond {
                        embed {
                            title = "구독 가능 목록"
                            description = "업데이트"
                        }
                    }
                }
            }


            publicSubCommand(::NewsArgs) {
                name = "do"
                description = "Do something"

                action {
                    val expect = when(arguments.news) {
                        "업데이트" -> "patchNote"
                        else -> "NaN"
                    }
                    when(val result = CfLoader().role(expect)) {
                        is Either.Left -> {
                            respond {
                                embeds.add(errorOccur(result.value, Clock.System.now(), "`/newsletter list`를 통해 이용가능한 목록을 확인해 주세요!"))
                            }
                        }
                        is Either.Right -> {
                            val targetUser = user.fetchMember(guild!!.id)
                            if(!targetUser.roleIds.contains(result.value)){
                                targetUser.addRole(result.value)
                                respond {
                                    content = "${arguments.news} 멘션을 받습니다!"
                                }
                            } else {
                                targetUser.removeRole(result.value)
                                respond {
                                    content = "${arguments.news} 멘션을 더 이상 받지 않습니다!"
                                }
                            }
                        }
                    }
                }
            }
        }
   }

    inner class NewsArgs : Arguments() {
        val news by defaultingString(
            "종류",
            "받을 알림의 이름",

            defaultValue = "FAILED"
        ) { _, value ->
            if(!NewsLists().findNews(value)) {
                throw DiscordRelayedException(
                    "존재하지 않는 이름입니다! `/newsletter list`를 사용해 목록을 확인해 주세요."
                )
            }
        }
    }
}