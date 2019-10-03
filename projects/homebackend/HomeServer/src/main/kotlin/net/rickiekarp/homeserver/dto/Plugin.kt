package net.rickiekarp.homeserver.dto

class Plugin(val identifier: String, val version: String, val file: String, val type: String, val updateEnable: String) {

    override fun toString(): String {
        return "Plugin [identifier=$identifier, version=$version]"
    }
}