package com.company.nemademo.view.permitsapplication

import com.company.nemademo.entity.PermitsApplication
import com.company.nemademo.view.main.MainView
import com.vaadin.flow.router.Route
import io.jmix.flowui.view.*

@Route(value = "permitsApplications/:id", layout = MainView::class)
@ViewController("PermitsApplication.detail")
@ViewDescriptor("permits-application-detail-view.xml")
@EditedEntityContainer("permitsApplicationDc")
class PermitsApplicationDetailView : StandardDetailView<PermitsApplication>() {
}