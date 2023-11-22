package com.company.nemademo.view.casepartiesinvolved

import com.company.nemademo.entity.CasePartiesInvolved
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

@Route(value = "casePartiesInvolveds", layout = MainView::class)
@ViewController("CasePartiesInvolved.list")
@ViewDescriptor("case-parties-involved-list-view.xml")
@LookupComponent("casePartiesInvolvedsDataGrid")
@DialogMode(width = "64em")
class CasePartiesInvolvedListView : StandardListView<CasePartiesInvolved>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var casePartiesInvolvedsDc: CollectionContainer<CasePartiesInvolved>

    @ViewComponent
    private lateinit var casePartiesInvolvedDc: InstanceContainer<CasePartiesInvolved>

    @ViewComponent
    private lateinit var casePartiesInvolvedDl: InstanceLoader<CasePartiesInvolved>

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

    @Subscribe("casePartiesInvolvedsDataGrid.create")
    fun onCasePartiesInvolvedsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: CasePartiesInvolved = dataContext.create(CasePartiesInvolved::class.java)
        casePartiesInvolvedDc.item = entity
        updateControls(true)
    }

    @Subscribe("casePartiesInvolvedsDataGrid.edit")
    fun onCasePartiesInvolvedsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        casePartiesInvolvedsDc.replaceItem(casePartiesInvolvedDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        casePartiesInvolvedDl.load()
        updateControls(false)
    }

    @Subscribe(id = "casePartiesInvolvedsDc", target = Target.DATA_CONTAINER)
    fun onCasePartiesInvolvedsDcItemChange(event: InstanceContainer.ItemChangeEvent<CasePartiesInvolved>) {
        val entity: CasePartiesInvolved? = event.item
        dataContext.clear()
        if (entity != null) {
            casePartiesInvolvedDl.entityId = entity.id
            casePartiesInvolvedDl.load()
        } else {
            casePartiesInvolvedDl.entityId = null
            casePartiesInvolvedDc.setItem(null)
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