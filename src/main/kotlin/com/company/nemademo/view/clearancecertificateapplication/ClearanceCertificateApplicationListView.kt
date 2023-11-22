package com.company.nemademo.view.clearancecertificateapplication

import com.company.nemademo.entity.ClearanceCertificateApplication
import com.company.nemademo.view.main.MainView
import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.HasValueAndElement
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import io.jmix.flowui.kit.action.ActionPerformedEvent
import io.jmix.flowui.kit.component.button.JmixButton
import io.jmix.flowui.model.*
import io.jmix.flowui.view.*
import io.jmix.flowui.view.Target

@Route(value = "clearanceCertificateApplications", layout = MainView::class)
@ViewController("ClearanceCertificateApplication.list")
@ViewDescriptor("clearance-certificate-application-list-view.xml")
@LookupComponent("clearanceCertificateApplicationsDataGrid")
@DialogMode(width = "64em")
class ClearanceCertificateApplicationListView : StandardListView<ClearanceCertificateApplication>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var clearanceCertificateApplicationsDc: CollectionContainer<ClearanceCertificateApplication>

    @ViewComponent
    private lateinit var clearanceCertificateApplicationDc: InstanceContainer<ClearanceCertificateApplication>

    @ViewComponent
    private lateinit var clearanceCertificateApplicationDl: InstanceLoader<ClearanceCertificateApplication>

    @ViewComponent
    private lateinit var listLayout: VerticalLayout

    @ViewComponent
    private lateinit var form: FormLayout

    @ViewComponent
    private lateinit var detailActions: HorizontalLayout

    @Subscribe
    fun onInit(event: InitEvent) {
        updateControls(false)
    }

    @Subscribe("clearanceCertificateApplicationsDataGrid.create")
    fun onClearanceCertificateApplicationsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: ClearanceCertificateApplication = dataContext.create(ClearanceCertificateApplication::class.java)
        clearanceCertificateApplicationDc.item = entity
        updateControls(true)
    }

    @Subscribe("clearanceCertificateApplicationsDataGrid.edit")
    fun onClearanceCertificateApplicationsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        clearanceCertificateApplicationsDc.replaceItem(clearanceCertificateApplicationDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        clearanceCertificateApplicationDl.load()
        updateControls(false)
    }

    @Subscribe(id = "clearanceCertificateApplicationsDc", target = Target.DATA_CONTAINER)
    fun onClearanceCertificateApplicationsDcItemChange(event: InstanceContainer.ItemChangeEvent<ClearanceCertificateApplication>) {
        val entity: ClearanceCertificateApplication? = event.item
        dataContext.clear()
        if (entity != null) {
            clearanceCertificateApplicationDl.entityId = entity.id
            clearanceCertificateApplicationDl.load()
        } else {
            clearanceCertificateApplicationDl.entityId = null
            clearanceCertificateApplicationDc.setItem(null)
        }
    }

    private fun updateControls(editing: Boolean) {
        form.children.forEach { component ->
            if (component is HasValueAndElement<*, *>) {
                component.isReadOnly = !editing
            }
        }
        detailActions.isVisible = editing
        listLayout.isEnabled = !editing
    }
}