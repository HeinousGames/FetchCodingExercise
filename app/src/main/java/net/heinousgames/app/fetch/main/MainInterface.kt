package net.heinousgames.app.fetch.main

import net.heinousgames.app.fetch.api.HiringObject

interface MainInterface {
    fun getHiringList(filteredMap: (Map<Int, List<HiringObject>>) -> Unit)
}