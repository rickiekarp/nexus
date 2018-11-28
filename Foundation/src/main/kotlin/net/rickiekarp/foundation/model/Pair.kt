package net.rickiekarp.foundation.model

class Pair<L, R> {
    var first: L? = null
    var second: R? = null

    constructor() {
        this.first = null
        this.second = null
    }

    constructor(first: L, second: R) {
        this.first = first
        this.second = second
    }

    override fun hashCode(): Int {
        return when {
            first == null -> second!!.hashCode()
            second == null -> first!!.hashCode()
            else -> first!!.hashCode() xor second!!.hashCode()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other is Pair<*, *>) {
            val pair = other as Pair<*, *>?
            return first == pair!!.first //&& second.equals(pair.second);
        }
        return false
    }

    override fun toString(): String {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}'.toString()
    }
}