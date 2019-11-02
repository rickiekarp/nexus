package net.rickiekarp.snakefx.net

import net.rickiekarp.core.net.NetworkAction
import net.rickiekarp.core.net.NetworkApi

class SnakeNetworkApi : NetworkApi() {
    companion object {
        private val GAME_API_CONTEXT = "gamedata"
        private val RANKING_DOMAIN = "ranking"
        private val RANKING_GET_ACTION = "highscore"
        private val RANKING_ADD_ACTION = "addHighscore"

        fun requestRanking(): NetworkAction {
            return NetworkAction.Builder.create().setHost(GAME_API_CONTEXT).setDomain(RANKING_DOMAIN).setAction(RANKING_GET_ACTION).setMethod("GET").build()
        }

        fun requestAddHighscore(): NetworkAction {
            return NetworkAction.Builder.create().setHost(GAME_API_CONTEXT).setDomain(RANKING_DOMAIN).setAction(RANKING_ADD_ACTION).setMethod("POST").build()
        }
    }
}
