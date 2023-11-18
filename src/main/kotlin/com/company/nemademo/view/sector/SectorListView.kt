package com.company.nemademo.view.sector

import com.company.nemademo.entity.Sector
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

@Route(value = "sectors", layout = MainView::class)
@ViewController("Sector.list")
@ViewDescriptor("sector-list-view.xml")
@LookupComponent("sectorsDataGrid")
@DialogMode(width = "64em")
class SectorListView : StandardListView<Sector>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var sectorsDc: CollectionContainer<Sector>

    @ViewComponent
    private lateinit var sectorDc: InstanceContainer<Sector>

    @ViewComponent
    private lateinit var sectorDl: InstanceLoader<Sector>

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

    @Subscribe("sectorsDataGrid.create")
    fun onSectorsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: Sector = dataContext.create(Sector::class.java)
        sectorDc.item = entity
        updateControls(true)
    }

    @Subscribe("sectorsDataGrid.edit")
    fun onSectorsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        sectorsDc.replaceItem(sectorDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        sectorDl.load()
        updateControls(false)
    }

    @Subscribe(id = "sectorsDc", target = Target.DATA_CONTAINER)
    fun onSectorsDcItemChange(event: InstanceContainer.ItemChangeEvent<Sector>) {
        val entity: Sector? = event.item
        dataContext.clear()
        if (entity != null) {
            sectorDl.entityId = entity.id
            sectorDl.load()
        } else {
            sectorDl.entityId = null
            sectorDc.setItem(null)
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