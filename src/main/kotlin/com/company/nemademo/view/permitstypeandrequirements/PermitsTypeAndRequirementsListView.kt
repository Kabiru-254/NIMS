package com.company.nemademo.view.permitstypeandrequirements

import com.company.nemademo.entity.PermitsTypeAndRequirements
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

@Route(value = "permitsTypeAndRequirementses", layout = MainView::class)
@ViewController("PermitsTypeAndRequirements.list")
@ViewDescriptor("permits-type-and-requirements-list-view.xml")
@LookupComponent("permitsTypeAndRequirementsesDataGrid")
@DialogMode(width = "64em")
class PermitsTypeAndRequirementsListView : StandardListView<PermitsTypeAndRequirements>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var permitsTypeAndRequirementsesDc: CollectionContainer<PermitsTypeAndRequirements>

    @ViewComponent
    private lateinit var permitsTypeAndRequirementsDc: InstanceContainer<PermitsTypeAndRequirements>

    @ViewComponent
    private lateinit var permitsTypeAndRequirementsDl: InstanceLoader<PermitsTypeAndRequirements>

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

    @Subscribe("permitsTypeAndRequirementsesDataGrid.create")
    fun onPermitsTypeAndRequirementsesDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: PermitsTypeAndRequirements = dataContext.create(PermitsTypeAndRequirements::class.java)
        permitsTypeAndRequirementsDc.item = entity
        updateControls(true)
    }

    @Subscribe("permitsTypeAndRequirementsesDataGrid.edit")
    fun onPermitsTypeAndRequirementsesDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        permitsTypeAndRequirementsesDc.replaceItem(permitsTypeAndRequirementsDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        permitsTypeAndRequirementsDl.load()
        updateControls(false)
    }

    @Subscribe(id = "permitsTypeAndRequirementsesDc", target = Target.DATA_CONTAINER)
    fun onPermitsTypeAndRequirementsesDcItemChange(event: InstanceContainer.ItemChangeEvent<PermitsTypeAndRequirements>) {
        val entity: PermitsTypeAndRequirements? = event.item
        dataContext.clear()
        if (entity != null) {
            permitsTypeAndRequirementsDl.entityId = entity.id
            permitsTypeAndRequirementsDl.load()
        } else {
            permitsTypeAndRequirementsDl.entityId = null
            permitsTypeAndRequirementsDc.setItem(null)
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