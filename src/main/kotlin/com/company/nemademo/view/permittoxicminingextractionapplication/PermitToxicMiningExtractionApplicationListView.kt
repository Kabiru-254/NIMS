package com.company.nemademo.view.permittoxicminingextractionapplication

import com.company.nemademo.entity.PermitToxicMiningExtractionApplication
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

@Route(value = "permitToxicMiningExtractionApplications", layout = MainView::class)
@ViewController("PermitToxicMiningExtractionApplication.list")
@ViewDescriptor("permit-toxic-mining-extraction-application-list-view.xml")
@LookupComponent("permitToxicMiningExtractionApplicationsDataGrid")
@DialogMode(width = "64em")
class PermitToxicMiningExtractionApplicationListView : StandardListView<PermitToxicMiningExtractionApplication>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var permitToxicMiningExtractionApplicationsDc: CollectionContainer<PermitToxicMiningExtractionApplication>

    @ViewComponent
    private lateinit var permitToxicMiningExtractionApplicationDc: InstanceContainer<PermitToxicMiningExtractionApplication>

    @ViewComponent
    private lateinit var permitToxicMiningExtractionApplicationDl: InstanceLoader<PermitToxicMiningExtractionApplication>

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

    @Subscribe("permitToxicMiningExtractionApplicationsDataGrid.create")
    fun onPermitToxicMiningExtractionApplicationsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: PermitToxicMiningExtractionApplication = dataContext.create(PermitToxicMiningExtractionApplication::class.java)
        permitToxicMiningExtractionApplicationDc.item = entity
        updateControls(true)
    }

    @Subscribe("permitToxicMiningExtractionApplicationsDataGrid.edit")
    fun onPermitToxicMiningExtractionApplicationsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        permitToxicMiningExtractionApplicationsDc.replaceItem(permitToxicMiningExtractionApplicationDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        permitToxicMiningExtractionApplicationDl.load()
        updateControls(false)
    }

    @Subscribe(id = "permitToxicMiningExtractionApplicationsDc", target = Target.DATA_CONTAINER)
    fun onPermitToxicMiningExtractionApplicationsDcItemChange(event: InstanceContainer.ItemChangeEvent<PermitToxicMiningExtractionApplication>) {
        val entity: PermitToxicMiningExtractionApplication? = event.item
        dataContext.clear()
        if (entity != null) {
            permitToxicMiningExtractionApplicationDl.entityId = entity.id
            permitToxicMiningExtractionApplicationDl.load()
        } else {
            permitToxicMiningExtractionApplicationDl.entityId = null
            permitToxicMiningExtractionApplicationDc.setItem(null)
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