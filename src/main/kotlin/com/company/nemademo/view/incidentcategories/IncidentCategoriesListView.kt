package com.company.nemademo.view.incidentcategories

import com.company.nemademo.entity.IncidentCategories
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

@Route(value = "incidentCategorieses", layout = MainView::class)
@ViewController("IncidentCategories.list")
@ViewDescriptor("incident-categories-list-view.xml")
@LookupComponent("incidentCategoriesesDataGrid")
@DialogMode(width = "64em")
class IncidentCategoriesListView : StandardListView<IncidentCategories>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var incidentCategoriesesDc: CollectionContainer<IncidentCategories>

    @ViewComponent
    private lateinit var incidentCategoriesDc: InstanceContainer<IncidentCategories>

    @ViewComponent
    private lateinit var incidentCategoriesDl: InstanceLoader<IncidentCategories>

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

    @Subscribe("incidentCategoriesesDataGrid.create")
    fun onIncidentCategoriesesDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: IncidentCategories = dataContext.create(IncidentCategories::class.java)
        incidentCategoriesDc.item = entity
        updateControls(true)
    }

    @Subscribe("incidentCategoriesesDataGrid.edit")
    fun onIncidentCategoriesesDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        incidentCategoriesesDc.replaceItem(incidentCategoriesDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        incidentCategoriesDl.load()
        updateControls(false)
    }

    @Subscribe(id = "incidentCategoriesesDc", target = Target.DATA_CONTAINER)
    fun onIncidentCategoriesesDcItemChange(event: InstanceContainer.ItemChangeEvent<IncidentCategories>) {
        val entity: IncidentCategories? = event.item
        dataContext.clear()
        if (entity != null) {
            incidentCategoriesDl.entityId = entity.id
            incidentCategoriesDl.load()
        } else {
            incidentCategoriesDl.entityId = null
            incidentCategoriesDc.setItem(null)
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