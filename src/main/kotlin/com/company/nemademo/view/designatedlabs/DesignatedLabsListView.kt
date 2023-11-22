package com.company.nemademo.view.designatedlabs

import com.company.nemademo.entity.DesignatedLabs
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

@Route(value = "designatedLabses", layout = MainView::class)
@ViewController("DesignatedLabs.list")
@ViewDescriptor("designated-labs-list-view.xml")
@LookupComponent("designatedLabsesDataGrid")
@DialogMode(width = "64em")
class DesignatedLabsListView : StandardListView<DesignatedLabs>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var designatedLabsesDc: CollectionContainer<DesignatedLabs>

    @ViewComponent
    private lateinit var designatedLabsDc: InstanceContainer<DesignatedLabs>

    @ViewComponent
    private lateinit var designatedLabsDl: InstanceLoader<DesignatedLabs>

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

    @Subscribe("designatedLabsesDataGrid.create")
    fun onDesignatedLabsesDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: DesignatedLabs = dataContext.create(DesignatedLabs::class.java)
        designatedLabsDc.item = entity
        updateControls(true)
    }

    @Subscribe("designatedLabsesDataGrid.edit")
    fun onDesignatedLabsesDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        designatedLabsesDc.replaceItem(designatedLabsDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        designatedLabsDl.load()
        updateControls(false)
    }

    @Subscribe(id = "designatedLabsesDc", target = Target.DATA_CONTAINER)
    fun onDesignatedLabsesDcItemChange(event: InstanceContainer.ItemChangeEvent<DesignatedLabs>) {
        val entity: DesignatedLabs? = event.item
        dataContext.clear()
        if (entity != null) {
            designatedLabsDl.entityId = entity.id
            designatedLabsDl.load()
        } else {
            designatedLabsDl.entityId = null
            designatedLabsDc.setItem(null)
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