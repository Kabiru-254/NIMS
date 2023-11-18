package com.company.nemademo.view.expert

import com.company.nemademo.entity.Expert
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

@Route(value = "experts", layout = MainView::class)
@ViewController("Expert.list")
@ViewDescriptor("expert-list-view.xml")
@LookupComponent("expertsDataGrid")
@DialogMode(width = "64em")
class ExpertListView : StandardListView<Expert>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var expertsDc: CollectionContainer<Expert>

    @ViewComponent
    private lateinit var expertDc: InstanceContainer<Expert>

    @ViewComponent
    private lateinit var expertDl: InstanceLoader<Expert>

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

    @Subscribe("expertsDataGrid.create")
    fun onExpertsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: Expert = dataContext.create(Expert::class.java)
        expertDc.item = entity
        updateControls(true)
    }

    @Subscribe("expertsDataGrid.edit")
    fun onExpertsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        expertsDc.replaceItem(expertDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        expertDl.load()
        updateControls(false)
    }

    @Subscribe(id = "expertsDc", target = Target.DATA_CONTAINER)
    fun onExpertsDcItemChange(event: InstanceContainer.ItemChangeEvent<Expert>) {
        val entity: Expert? = event.item
        dataContext.clear()
        if (entity != null) {
            expertDl.entityId = entity.id
            expertDl.load()
        } else {
            expertDl.entityId = null
            expertDc.setItem(null)
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