package com.company.nemademo.view.clearancecertificatesrequirements

import com.company.nemademo.entity.ClearanceCertificatesRequirements
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

@Route(value = "clearanceCertificatesRequirementses", layout = MainView::class)
@ViewController("ClearanceCertificatesRequirements.list")
@ViewDescriptor("clearance-certificates-requirements-list-view.xml")
@LookupComponent("clearanceCertificatesRequirementsesDataGrid")
@DialogMode(width = "64em")
class ClearanceCertificatesRequirementsListView : StandardListView<ClearanceCertificatesRequirements>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var clearanceCertificatesRequirementsesDc: CollectionContainer<ClearanceCertificatesRequirements>

    @ViewComponent
    private lateinit var clearanceCertificatesRequirementsDc: InstanceContainer<ClearanceCertificatesRequirements>

    @ViewComponent
    private lateinit var clearanceCertificatesRequirementsDl: InstanceLoader<ClearanceCertificatesRequirements>

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

    @Subscribe("clearanceCertificatesRequirementsesDataGrid.create")
    fun onClearanceCertificatesRequirementsesDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: ClearanceCertificatesRequirements = dataContext.create(ClearanceCertificatesRequirements::class.java)
        clearanceCertificatesRequirementsDc.item = entity
        updateControls(true)
    }

    @Subscribe("clearanceCertificatesRequirementsesDataGrid.edit")
    fun onClearanceCertificatesRequirementsesDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        clearanceCertificatesRequirementsesDc.replaceItem(clearanceCertificatesRequirementsDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        clearanceCertificatesRequirementsDl.load()
        updateControls(false)
    }

    @Subscribe(id = "clearanceCertificatesRequirementsesDc", target = Target.DATA_CONTAINER)
    fun onClearanceCertificatesRequirementsesDcItemChange(event: InstanceContainer.ItemChangeEvent<ClearanceCertificatesRequirements>) {
        val entity: ClearanceCertificatesRequirements? = event.item
        dataContext.clear()
        if (entity != null) {
            clearanceCertificatesRequirementsDl.entityId = entity.id
            clearanceCertificatesRequirementsDl.load()
        } else {
            clearanceCertificatesRequirementsDl.entityId = null
            clearanceCertificatesRequirementsDc.setItem(null)
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