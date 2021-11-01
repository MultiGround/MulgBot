package org.multiground.mulgbot.module

import kotlinx.serialization.Serializable


@Serializable
data class Message (
    val core: Map<String, String>
)

@Serializable
data class Roles(
    val patchNote: String
)