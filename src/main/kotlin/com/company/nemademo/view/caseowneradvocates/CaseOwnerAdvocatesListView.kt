package com.company.nemademo.view.caseowneradvocates

import com.company.nemademo.entity.CaseOwnerAdvocates
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

@Route(value = "caseOwnerAdvocateses", layout = MainView::class)
@ViewController("CaseOwnerAdvocates.list")
@ViewDescriptor("case-owner-advocates-list-view.xml")
@LookupComponent("caseOwnerAdvocatesesDataGrid")
@DialogMode(width = "64em")
class CaseOwnerAdvocatesListView : StandardListView<CaseOwnerAdvocates>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var caseOwnerAdvocatesesDc: CollectionContainer<CaseOwnerAdvocates>

    @ViewComponent
    private lateinit var caseOwnerAdvocatesDc: InstanceContainer<CaseOwnerAdvocates>

    @ViewComponent
    private lateinit var caseOwnerAdvocatesDl: InstanceLoader<CaseOwnerAdvocates>

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

    @Subscribe("caseOwnerAdvocatesesDataGrid.create")
    fun onCaseOwnerAdvocatesesDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: CaseOwnerAdvocates = dataContext.create(CaseOwnerAdvocates::class.java)
        caseOwnerAdvocatesDc.item = entity
        updateControls(true)
    }

    @Subscribe("caseOwnerAdvocatesesDataGrid.edit")
    fun onCaseOwnerAdvocatesesDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        caseOwnerAdvocatesesDc.replaceItem(caseOwnerAdvocatesDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        caseOwnerAdvocatesDl.load()
        updateControls(false)
    }

    @Subscribe(id = "caseOwnerAdvocatesesDc", target = Target.DATA_CONTAINER)
    fun onCaseOwnerAdvocatesesDcItemChange(event: InstanceContainer.ItemChangeEvent<CaseOwnerAdvocates>) {
        val entity: CaseOwnerAdvocates? = event.item
        dataContext.clear()
        if (entity != null) {
            caseOwnerAdvocatesDl.entityId = entity.id
            caseOwnerAdvocatesDl.load()
        } else {
            caseOwnerAdvocatesDl.entityId = null
            caseOwnerAdvocatesDc.setItem(null)
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