package com.company.nemademo.view.permitsapplication

import com.company.nemademo.entity.PermitsApplication
import com.company.nemademo.view.main.MainView
import com.vaadin.flow.router.Route
import io.jmix.flowui.view.*

@Route(value = "permitsApplications", layout = MainView::class)
@ViewController("PermitsApplication.list")
@ViewDescriptor("permits-application-list-view.xml")
@LookupComponent("permitsApplicationsDataGrid")
@DialogMode(width = "64em")
class PermitsApplicationListView : StandardListView<PermitsApplication>() {
}