package net.rickiekarp.homeserver.dao

import net.rickiekarp.homeserver.dto.ContactDTO

interface InformationDAO {
    fun getContactInformation(): ContactDTO?
}
