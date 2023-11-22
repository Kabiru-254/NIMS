package com.company.nemademo.view.permitexporttransboundaryapplication

import com.company.nemademo.entity.PermitExportTransBoundaryApplication
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

@Route(value = "permitExportTransBoundaryApplications", layout = MainView::class)
@ViewController("PermitExportTransBoundaryApplication.list")
@ViewDescriptor("permit-export-trans-boundary-application-list-view.xml")
@LookupComponent("permitExportTransBoundaryApplicationsDataGrid")
@DialogMode(width = "64em")
class PermitExportTransBoundaryApplicationListView : StandardListView<PermitExportTransBoundaryApplication>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var permitExportTransBoundaryApplicationsDc: CollectionContainer<PermitExportTransBoundaryApplication>

    @ViewComponent
    private lateinit var permitExportTransBoundaryApplicationDc: InstanceContainer<PermitExportTransBoundaryApplication>

    @ViewComponent
    private lateinit var permitExportTransBoundaryApplicationDl: InstanceLoader<PermitExportTransBoundaryApplication>

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

    @Subscribe("permitExportTransBoundaryApplicationsDataGrid.create")
    fun onPermitExportTransBoundaryApplicationsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: PermitExportTransBoundaryApplication = dataContext.create(PermitExportTransBoundaryApplication::class.java)
        permitExportTransBoundaryApplicationDc.item = entity
        updateControls(true)
    }

    @Subscribe("permitExportTransBoundaryApplicationsDataGrid.edit")
    fun onPermitExportTransBoundaryApplicationsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        permitExportTransBoundaryApplicationsDc.replaceItem(permitExportTransBoundaryApplicationDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        permitExportTransBoundaryApplicationDl.load()
        updateControls(false)
    }

    @Subscribe(id = "permitExportTransBoundaryApplicationsDc", target = Target.DATA_CONTAINER)
    fun onPermitExportTransBoundaryApplicationsDcItemChange(event: InstanceContainer.ItemChangeEvent<PermitExportTransBoundaryApplication>) {
        val entity: PermitExportTransBoundaryApplication? = event.item
        dataContext.clear()
        if (entity != null) {
            permitExportTransBoundaryApplicationDl.entityId = entity.id
            permitExportTransBoundaryApplicationDl.load()
        } else {
            permitExportTransBoundaryApplicationDl.entityId = null
            permitExportTransBoundaryApplicationDc.setItem(null)
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