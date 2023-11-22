package com.company.nemademo.view.permitozonedepletingapplication

import com.company.nemademo.entity.PermitOzoneDepletingApplication
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

@Route(value = "permitOzoneDepletingApplications", layout = MainView::class)
@ViewController("PermitOzoneDepletingApplication.list")
@ViewDescriptor("permit-ozone-depleting-application-list-view.xml")
@LookupComponent("permitOzoneDepletingApplicationsDataGrid")
@DialogMode(width = "64em")
class PermitOzoneDepletingApplicationListView : StandardListView<PermitOzoneDepletingApplication>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var permitOzoneDepletingApplicationsDc: CollectionContainer<PermitOzoneDepletingApplication>

    @ViewComponent
    private lateinit var permitOzoneDepletingApplicationDc: InstanceContainer<PermitOzoneDepletingApplication>

    @ViewComponent
    private lateinit var permitOzoneDepletingApplicationDl: InstanceLoader<PermitOzoneDepletingApplication>

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

    @Subscribe("permitOzoneDepletingApplicationsDataGrid.create")
    fun onPermitOzoneDepletingApplicationsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: PermitOzoneDepletingApplication = dataContext.create(PermitOzoneDepletingApplication::class.java)
        permitOzoneDepletingApplicationDc.item = entity
        updateControls(true)
    }

    @Subscribe("permitOzoneDepletingApplicationsDataGrid.edit")
    fun onPermitOzoneDepletingApplicationsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        permitOzoneDepletingApplicationsDc.replaceItem(permitOzoneDepletingApplicationDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        permitOzoneDepletingApplicationDl.load()
        updateControls(false)
    }

    @Subscribe(id = "permitOzoneDepletingApplicationsDc", target = Target.DATA_CONTAINER)
    fun onPermitOzoneDepletingApplicationsDcItemChange(event: InstanceContainer.ItemChangeEvent<PermitOzoneDepletingApplication>) {
        val entity: PermitOzoneDepletingApplication? = event.item
        dataContext.clear()
        if (entity != null) {
            permitOzoneDepletingApplicationDl.entityId = entity.id
            permitOzoneDepletingApplicationDl.load()
        } else {
            permitOzoneDepletingApplicationDl.entityId = null
            permitOzoneDepletingApplicationDc.setItem(null)
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