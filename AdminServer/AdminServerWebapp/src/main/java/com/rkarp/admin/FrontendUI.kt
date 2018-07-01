package com.rkarp.admin

import com.google.common.eventbus.Subscribe
import com.vaadin.server.VaadinRequest
import com.vaadin.annotations.Theme
import com.vaadin.annotations.Widgetset
import com.vaadin.server.Page
import com.vaadin.server.Responsive
import com.vaadin.server.VaadinSession
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.ui.*
import com.vaadin.ui.themes.ValoTheme
import com.rkarp.admin.data.DataProvider
import com.rkarp.admin.data.dummy.DummyDataProvider
import com.rkarp.admin.domain.User
import com.rkarp.admin.event.DashboardEvent
import com.rkarp.admin.event.DashboardEventBus
import com.rkarp.admin.view.LoginView
import com.rkarp.admin.view.MainView
import java.util.*

@SpringUI
@Theme("Frontend")
@Widgetset("AppWidgetset")
class FrontendUI : UI() {

    /*
     * This field stores an access to the dummy backend layer. In real
     * applications you most likely gain access to your beans trough lookup or
     * injection; and not in the UI but somewhere closer to where they're
     * actually accessed.
     */
    private val dataProvider = DummyDataProvider()
    private val dashboardEventbus = DashboardEventBus()

    override fun init(request: VaadinRequest) {
        locale = Locale.US

        DashboardEventBus.register(this)
        Responsive.makeResponsive(this)
        addStyleName(ValoTheme.UI_WITH_MENU)

        updateContent()

        // Some views need to be aware of browser resize events so a
        // BrowserResizeEvent gets fired to the event bus on every occasion.
        Page.getCurrent().addBrowserWindowResizeListener { DashboardEventBus.post(DashboardEvent.BrowserResizeEvent()) }
    }

    /**
     * Updates the correct content for this UI based on the current user status.
     * If the user is logged in with appropriate privileges, main view is shown.
     * Otherwise login view is shown.
     */
    private fun updateContent() {
        val user = VaadinSession.getCurrent().getAttribute(User::class.java.name) as User?
        if (user != null && "admin" == user.role) {
            // Authenticated user
            content = MainView()
            removeStyleName("loginview")
            navigator.navigateTo(navigator.state)
        } else {
            content = LoginView()
            addStyleName("loginview")
        }
    }

    @Subscribe
    fun userLoginRequested(event: DashboardEvent.UserLoginRequestedEvent) {
        val user = getDataProvider().authenticate(event.userName,
                event.password)
        VaadinSession.getCurrent().setAttribute(User::class.java.name, user)
        updateContent()
    }

    @Subscribe
    fun userLoggedOut(event: DashboardEvent.UserLoggedOutEvent) {
        // When the user logs out, current VaadinSession gets closed and the
        // page gets reloaded on the login screen. Do notice the this doesn't
        // invalidate the current HttpSession.
        VaadinSession.getCurrent().close()
        Page.getCurrent().reload()
    }

    @Subscribe
    fun closeOpenWindows(event: DashboardEvent.CloseOpenWindowsEvent) {
        for (window in windows) {
            window.close()
        }
    }

    companion object {
        /**
         * @return An instance for accessing the (dummy) services layer.
         */
        @JvmStatic
        fun getDataProvider(): DataProvider {
            return (UI.getCurrent() as FrontendUI).dataProvider
        }

        @JvmStatic
        fun getDashboardEventbus(): DashboardEventBus {
            return (UI.getCurrent() as FrontendUI).dashboardEventbus
        }
    }


}
