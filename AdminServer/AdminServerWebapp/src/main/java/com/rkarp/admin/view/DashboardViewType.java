package com.rkarp.admin.view;

import com.rkarp.admin.view.dashboard.DashboardView;
import com.rkarp.admin.view.transactions.TransactionsView;
import com.vaadin.icons.VaadinIcons;
import com.rkarp.admin.view.reports.ReportsView;
import com.rkarp.admin.view.sales.SalesView;
import com.rkarp.admin.view.schedule.ScheduleView;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;

public enum DashboardViewType {
    DASHBOARD("dashboard", DashboardView.class, VaadinIcons.HOME, true),
    SALES("sales", SalesView.class, VaadinIcons.BAR_CHART_H, false),
    TRANSACTIONS("transactions", TransactionsView.class, VaadinIcons.TABLE, false),
    REPORTS("reports", ReportsView.class, VaadinIcons.FILE_TEXT_O, true),
    SCHEDULE("schedule", ScheduleView.class, VaadinIcons.CALENDAR_O, false);

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    DashboardViewType(final String viewName, final Class<? extends View> viewClass, final Resource icon, final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }
}
