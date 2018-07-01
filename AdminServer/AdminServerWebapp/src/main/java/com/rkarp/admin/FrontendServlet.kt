package com.rkarp.admin

import com.vaadin.server.VaadinServlet
import javax.servlet.annotation.WebInitParam
import javax.servlet.annotation.WebServlet

/**
 * Servlet
 */
@WebServlet(
        asyncSupported = false,
        urlPatterns = arrayOf("/*", "/VAADIN/*"),
        initParams = arrayOf(WebInitParam(name = "ui", value = "com.rkarp.admin.FrontendUI"))
)
class FrontendServlet : VaadinServlet()
