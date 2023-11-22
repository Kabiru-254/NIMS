package com.company.nemademo.view.labdesignation

import com.company.nemademo.entity.LabDesignation
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

@Route(value = "labDesignations", layout = MainView::class)
@ViewController("LabDesignation.list")
@ViewDescriptor("lab-designation-list-view.xml")
@LookupComponent("labDesignationsDataGrid")
@DialogMode(width = "64em")
class LabDesignationListView : StandardListView<LabDesignation>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var labDesignationsDc: CollectionContainer<LabDesignation>

    @ViewComponent
    private lateinit var labDesignationDc: InstanceContainer<LabDesignation>

    @ViewComponent
    private lateinit var labDesignationDl: InstanceLoader<LabDesignation>

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

    @Subscribe("labDesignationsDataGrid.create")
    fun onLabDesignationsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: LabDesignation = dataContext.create(LabDesignation::class.java)
        labDesignationDc.item = entity
        updateControls(true)
    }

    @Subscribe("labDesignationsDataGrid.edit")
    fun onLabDesignationsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        labDesignationsDc.replaceItem(labDesignationDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        labDesignationDl.load()
        updateControls(false)
    }

    @Subscribe(id = "labDesignationsDc", target = Target.DATA_CONTAINER)
    fun onLabDesignationsDcItemChange(event: InstanceContainer.ItemChangeEvent<LabDesignation>) {
        val entity: LabDesignation? = event.item
        dataContext.clear()
        if (entity != null) {
            labDesignationDl.entityId = entity.id
            labDesignationDl.load()
        } else {
            labDesignationDl.entityId = null
            labDesignationDc.setItem(null)
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