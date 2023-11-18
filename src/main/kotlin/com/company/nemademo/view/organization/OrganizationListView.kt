package com.company.nemademo.view.organization

import com.company.nemademo.entity.Organization
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

@Route(value = "organizations", layout = MainView::class)
@ViewController("Organization.list")
@ViewDescriptor("organization-list-view.xml")
@LookupComponent("organizationsDataGrid")
@DialogMode(width = "64em")
class OrganizationListView : StandardListView<Organization>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var organizationsDc: CollectionContainer<Organization>

    @ViewComponent
    private lateinit var organizationDc: InstanceContainer<Organization>

    @ViewComponent
    private lateinit var organizationDl: InstanceLoader<Organization>

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

    @Subscribe("organizationsDataGrid.create")
    fun onOrganizationsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: Organization = dataContext.create(Organization::class.java)
        organizationDc.item = entity
        updateControls(true)
    }

    @Subscribe("organizationsDataGrid.edit")
    fun onOrganizationsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        organizationsDc.replaceItem(organizationDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        organizationDl.load()
        updateControls(false)
    }

    @Subscribe(id = "organizationsDc", target = Target.DATA_CONTAINER)
    fun onOrganizationsDcItemChange(event: InstanceContainer.ItemChangeEvent<Organization>) {
        val entity: Organization? = event.item
        dataContext.clear()
        if (entity != null) {
            organizationDl.entityId = entity.id
            organizationDl.load()
        } else {
            organizationDl.entityId = null
            organizationDc.setItem(null)
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