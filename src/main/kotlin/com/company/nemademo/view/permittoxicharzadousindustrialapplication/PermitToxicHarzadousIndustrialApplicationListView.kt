package com.company.nemademo.view.permittoxicharzadousindustrialapplication

import com.company.nemademo.entity.PermitToxicHarzadousIndustrialApplication
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

@Route(value = "permitToxicHarzadousIndustrialApplications", layout = MainView::class)
@ViewController("PermitToxicHarzadousIndustrialApplication.list")
@ViewDescriptor("permit-toxic-harzadous-industrial-application-list-view.xml")
@LookupComponent("permitToxicHarzadousIndustrialApplicationsDataGrid")
@DialogMode(width = "64em")
class PermitToxicHarzadousIndustrialApplicationListView : StandardListView<PermitToxicHarzadousIndustrialApplication>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var permitToxicHarzadousIndustrialApplicationsDc: CollectionContainer<PermitToxicHarzadousIndustrialApplication>

    @ViewComponent
    private lateinit var permitToxicHarzadousIndustrialApplicationDc: InstanceContainer<PermitToxicHarzadousIndustrialApplication>

    @ViewComponent
    private lateinit var permitToxicHarzadousIndustrialApplicationDl: InstanceLoader<PermitToxicHarzadousIndustrialApplication>

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

    @Subscribe("permitToxicHarzadousIndustrialApplicationsDataGrid.create")
    fun onPermitToxicHarzadousIndustrialApplicationsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: PermitToxicHarzadousIndustrialApplication = dataContext.create(PermitToxicHarzadousIndustrialApplication::class.java)
        permitToxicHarzadousIndustrialApplicationDc.item = entity
        updateControls(true)
    }

    @Subscribe("permitToxicHarzadousIndustrialApplicationsDataGrid.edit")
    fun onPermitToxicHarzadousIndustrialApplicationsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        permitToxicHarzadousIndustrialApplicationsDc.replaceItem(permitToxicHarzadousIndustrialApplicationDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        permitToxicHarzadousIndustrialApplicationDl.load()
        updateControls(false)
    }

    @Subscribe(id = "permitToxicHarzadousIndustrialApplicationsDc", target = Target.DATA_CONTAINER)
    fun onPermitToxicHarzadousIndustrialApplicationsDcItemChange(event: InstanceContainer.ItemChangeEvent<PermitToxicHarzadousIndustrialApplication>) {
        val entity: PermitToxicHarzadousIndustrialApplication? = event.item
        dataContext.clear()
        if (entity != null) {
            permitToxicHarzadousIndustrialApplicationDl.entityId = entity.id
            permitToxicHarzadousIndustrialApplicationDl.load()
        } else {
            permitToxicHarzadousIndustrialApplicationDl.entityId = null
            permitToxicHarzadousIndustrialApplicationDc.setItem(null)
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