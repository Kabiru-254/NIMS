package com.company.nemademo.view.licenceapplication

import com.company.nemademo.entity.LicenceApplication
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

@Route(value = "licenceApplications", layout = MainView::class)
@ViewController("LicenceApplication.list")
@ViewDescriptor("licence-application-list-view.xml")
@LookupComponent("licenceApplicationsDataGrid")
@DialogMode(width = "64em")
class LicenceApplicationListView : StandardListView<LicenceApplication>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var licenceApplicationsDc: CollectionContainer<LicenceApplication>

    @ViewComponent
    private lateinit var licenceApplicationDc: InstanceContainer<LicenceApplication>

    @ViewComponent
    private lateinit var licenceApplicationDl: InstanceLoader<LicenceApplication>

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

    @Subscribe("licenceApplicationsDataGrid.create")
    fun onLicenceApplicationsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: LicenceApplication = dataContext.create(LicenceApplication::class.java)
        licenceApplicationDc.item = entity
        updateControls(true)
    }

    @Subscribe("licenceApplicationsDataGrid.edit")
    fun onLicenceApplicationsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        licenceApplicationsDc.replaceItem(licenceApplicationDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        licenceApplicationDl.load()
        updateControls(false)
    }

    @Subscribe(id = "licenceApplicationsDc", target = Target.DATA_CONTAINER)
    fun onLicenceApplicationsDcItemChange(event: InstanceContainer.ItemChangeEvent<LicenceApplication>) {
        val entity: LicenceApplication? = event.item
        dataContext.clear()
        if (entity != null) {
            licenceApplicationDl.entityId = entity.id
            licenceApplicationDl.load()
        } else {
            licenceApplicationDl.entityId = null
            licenceApplicationDc.setItem(null)
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