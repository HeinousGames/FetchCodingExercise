package net.heinousgames.app.fetch.api

import kotlinx.serialization.Serializable

@Serializable
data class HiringObject(val id: Int, val listId: Int, val name: String?)
