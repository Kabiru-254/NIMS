package com.company.nemademo.view.incidentstatuses

import com.company.nemademo.entity.IncidentStatuses
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

@Route(value = "incidentStatuseses", layout = MainView::class)
@ViewController("IncidentStatuses.list")
@ViewDescriptor("incident-statuses-list-view.xml")
@LookupComponent("incidentStatusesesDataGrid")
@DialogMode(width = "64em")
class IncidentStatusesListView : StandardListView<IncidentStatuses>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var incidentStatusesesDc: CollectionContainer<IncidentStatuses>

    @ViewComponent
    private lateinit var incidentStatusesDc: InstanceContainer<IncidentStatuses>

    @ViewComponent
    private lateinit var incidentStatusesDl: InstanceLoader<IncidentStatuses>

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

    @Subscribe("incidentStatusesesDataGrid.create")
    fun onIncidentStatusesesDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: IncidentStatuses = dataContext.create(IncidentStatuses::class.java)
        incidentStatusesDc.item = entity
        updateControls(true)
    }

    @Subscribe("incidentStatusesesDataGrid.edit")
    fun onIncidentStatusesesDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        incidentStatusesesDc.replaceItem(incidentStatusesDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        incidentStatusesDl.load()
        updateControls(false)
    }

    @Subscribe(id = "incidentStatusesesDc", target = Target.DATA_CONTAINER)
    fun onIncidentStatusesesDcItemChange(event: InstanceContainer.ItemChangeEvent<IncidentStatuses>) {
        val entity: IncidentStatuses? = event.item
        dataContext.clear()
        if (entity != null) {
            incidentStatusesDl.entityId = entity.id
            incidentStatusesDl.load()
        } else {
            incidentStatusesDl.entityId = null
            incidentStatusesDc.setItem(null)
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