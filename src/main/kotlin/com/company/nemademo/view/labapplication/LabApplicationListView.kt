package com.company.nemademo.view.labapplication

import com.company.nemademo.entity.LabApplication
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

@Route(value = "labApplications", layout = MainView::class)
@ViewController("LabApplication.list")
@ViewDescriptor("lab-application-list-view.xml")
@LookupComponent("labApplicationsDataGrid")
@DialogMode(width = "64em")
class LabApplicationListView : StandardListView<LabApplication>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var labApplicationsDc: CollectionContainer<LabApplication>

    @ViewComponent
    private lateinit var labApplicationDc: InstanceContainer<LabApplication>

    @ViewComponent
    private lateinit var labApplicationDl: InstanceLoader<LabApplication>

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

    @Subscribe("labApplicationsDataGrid.create")
    fun onLabApplicationsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: LabApplication = dataContext.create(LabApplication::class.java)
        labApplicationDc.item = entity
        updateControls(true)
    }

    @Subscribe("labApplicationsDataGrid.edit")
    fun onLabApplicationsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        labApplicationsDc.replaceItem(labApplicationDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        labApplicationDl.load()
        updateControls(false)
    }

    @Subscribe(id = "labApplicationsDc", target = Target.DATA_CONTAINER)
    fun onLabApplicationsDcItemChange(event: InstanceContainer.ItemChangeEvent<LabApplication>) {
        val entity: LabApplication? = event.item
        dataContext.clear()
        if (entity != null) {
            labApplicationDl.entityId = entity.id
            labApplicationDl.load()
        } else {
            labApplicationDl.entityId = null
            labApplicationDc.setItem(null)
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