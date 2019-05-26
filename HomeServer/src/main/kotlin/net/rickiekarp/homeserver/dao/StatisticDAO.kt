package net.rickiekarp.homeserver.dao

interface StatisticDAO {
    fun getShoppingStatistic(userId: Int, days: Int): HashMap<String, Double>
}