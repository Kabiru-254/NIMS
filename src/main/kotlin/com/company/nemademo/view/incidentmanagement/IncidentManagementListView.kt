package com.company.nemademo.view.incidentmanagement

import com.company.nemademo.entity.IncidentManagement
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

@Route(value = "incidentManagements", layout = MainView::class)
@ViewController("IncidentManagement.list")
@ViewDescriptor("incident-management-list-view.xml")
@LookupComponent("incidentManagementsDataGrid")
@DialogMode(width = "64em")
class IncidentManagementListView : StandardListView<IncidentManagement>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var incidentManagementsDc: CollectionContainer<IncidentManagement>

    @ViewComponent
    private lateinit var incidentManagementDc: InstanceContainer<IncidentManagement>

    @ViewComponent
    private lateinit var incidentManagementDl: InstanceLoader<IncidentManagement>

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

    @Subscribe("incidentManagementsDataGrid.create")
    fun onIncidentManagementsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: IncidentManagement = dataContext.create(IncidentManagement::class.java)
        incidentManagementDc.item = entity
        updateControls(true)
    }

    @Subscribe("incidentManagementsDataGrid.edit")
    fun onIncidentManagementsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        incidentManagementsDc.replaceItem(incidentManagementDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        incidentManagementDl.load()
        updateControls(false)
    }

    @Subscribe(id = "incidentManagementsDc", target = Target.DATA_CONTAINER)
    fun onIncidentManagementsDcItemChange(event: InstanceContainer.ItemChangeEvent<IncidentManagement>) {
        val entity: IncidentManagement? = event.item
        dataContext.clear()
        if (entity != null) {
            incidentManagementDl.entityId = entity.id
            incidentManagementDl.load()
        } else {
            incidentManagementDl.entityId = null
            incidentManagementDc.setItem(null)
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