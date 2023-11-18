package com.company.nemademo.view.subsector

import com.company.nemademo.entity.SubSector
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

@Route(value = "subSectors", layout = MainView::class)
@ViewController("SubSector.list")
@ViewDescriptor("sub-sector-list-view.xml")
@LookupComponent("subSectorsDataGrid")
@DialogMode(width = "64em")
class SubSectorListView : StandardListView<SubSector>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var subSectorsDc: CollectionContainer<SubSector>

    @ViewComponent
    private lateinit var subSectorDc: InstanceContainer<SubSector>

    @ViewComponent
    private lateinit var subSectorDl: InstanceLoader<SubSector>

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

    @Subscribe("subSectorsDataGrid.create")
    fun onSubSectorsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: SubSector = dataContext.create(SubSector::class.java)
        subSectorDc.item = entity
        updateControls(true)
    }

    @Subscribe("subSectorsDataGrid.edit")
    fun onSubSectorsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        subSectorsDc.replaceItem(subSectorDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        subSectorDl.load()
        updateControls(false)
    }

    @Subscribe(id = "subSectorsDc", target = Target.DATA_CONTAINER)
    fun onSubSectorsDcItemChange(event: InstanceContainer.ItemChangeEvent<SubSector>) {
        val entity: SubSector? = event.item
        dataContext.clear()
        if (entity != null) {
            subSectorDl.entityId = entity.id
            subSectorDl.load()
        } else {
            subSectorDl.entityId = null
            subSectorDc.setItem(null)
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