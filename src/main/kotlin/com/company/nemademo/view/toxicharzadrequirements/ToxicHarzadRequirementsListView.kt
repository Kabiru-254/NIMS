package com.company.nemademo.view.toxicharzadrequirements

import com.company.nemademo.entity.ToxicHarzadRequirements
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

@Route(value = "toxicHarzadRequirementses", layout = MainView::class)
@ViewController("ToxicHarzadRequirements.list")
@ViewDescriptor("toxic-harzad-requirements-list-view.xml")
@LookupComponent("toxicHarzadRequirementsesDataGrid")
@DialogMode(width = "64em")
class ToxicHarzadRequirementsListView : StandardListView<ToxicHarzadRequirements>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var toxicHarzadRequirementsesDc: CollectionContainer<ToxicHarzadRequirements>

    @ViewComponent
    private lateinit var toxicHarzadRequirementsDc: InstanceContainer<ToxicHarzadRequirements>

    @ViewComponent
    private lateinit var toxicHarzadRequirementsDl: InstanceLoader<ToxicHarzadRequirements>

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

    @Subscribe("toxicHarzadRequirementsesDataGrid.create")
    fun onToxicHarzadRequirementsesDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: ToxicHarzadRequirements = dataContext.create(ToxicHarzadRequirements::class.java)
        toxicHarzadRequirementsDc.item = entity
        updateControls(true)
    }

    @Subscribe("toxicHarzadRequirementsesDataGrid.edit")
    fun onToxicHarzadRequirementsesDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        toxicHarzadRequirementsesDc.replaceItem(toxicHarzadRequirementsDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        toxicHarzadRequirementsDl.load()
        updateControls(false)
    }

    @Subscribe(id = "toxicHarzadRequirementsesDc", target = Target.DATA_CONTAINER)
    fun onToxicHarzadRequirementsesDcItemChange(event: InstanceContainer.ItemChangeEvent<ToxicHarzadRequirements>) {
        val entity: ToxicHarzadRequirements? = event.item
        dataContext.clear()
        if (entity != null) {
            toxicHarzadRequirementsDl.entityId = entity.id
            toxicHarzadRequirementsDl.load()
        } else {
            toxicHarzadRequirementsDl.entityId = null
            toxicHarzadRequirementsDc.setItem(null)
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