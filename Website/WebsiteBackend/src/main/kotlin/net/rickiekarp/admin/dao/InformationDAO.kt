package net.rickiekarp.admin.dao

import net.rickiekarp.admin.dto.ContactDTO

interface InformationDAO {
    fun getContactInformation(): ContactDTO?
}
