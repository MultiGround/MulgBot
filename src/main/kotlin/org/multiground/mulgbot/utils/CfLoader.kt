package org.multiground.mulgbot.utils

import arrow.core.Either
import arrow.core.left
import com.charleskorn.kaml.Yaml
import dev.kord.common.entity.Snowflake
import org.multiground.mulgbot.meta.Response
import org.multiground.mulgbot.module.ExtType
import org.multiground.mulgbot.module.Message
import org.multiground.mulgbot.module.Roles
import java.io.InputStream

class CfLoader {
    private val msgYml = try {
        ClassLoader.getSystemResourceAsStream("messages.yaml")
    } catch(error: Exception) {
        throw RuntimeException()
    }

    private val roleYml = try {
        ClassLoader.getSystemResourceAsStream("roleIDs.yaml")
    } catch(e: Exception){
        throw RuntimeException()
    }

    fun message(module: ExtType, expect: String): Either<Error, String> {
        val result = Yaml.default.decodeFromStream(Message.serializer(), msgYml)
        when(module) {
            ExtType.CORE -> {
                return if(!result.core.containsKey(expect)) {
                    Either.Left(org.multiground.mulgbot.error.Error.NoValueFound)
                } else Either.Right(result.core[expect]!!)
            }
            ExtType.NEWS -> {

            }
        }
        return Either.Left(org.multiground.mulgbot.error.Error.NoTypeFound)
    }

    fun role(expect: String): Either<Error, Snowflake> {
        val result = Yaml.default.decodeFromStream(Roles.serializer(), roleYml)
        return if(expect == "patchNote") Either.Right(Snowflake(result.patchNote))
        else Either.Left(org.multiground.mulgbot.error.Error.NoValueFound)
    }
}