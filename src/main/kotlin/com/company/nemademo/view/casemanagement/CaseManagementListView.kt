package com.company.nemademo.view.casemanagement

import com.company.nemademo.entity.CaseManagement
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

@Route(value = "caseManagements", layout = MainView::class)
@ViewController("CaseManagement.list")
@ViewDescriptor("case-management-list-view.xml")
@LookupComponent("caseManagementsDataGrid")
@DialogMode(width = "64em")
class CaseManagementListView : StandardListView<CaseManagement>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var caseManagementsDc: CollectionContainer<CaseManagement>

    @ViewComponent
    private lateinit var caseManagementDc: InstanceContainer<CaseManagement>

    @ViewComponent
    private lateinit var caseManagementDl: InstanceLoader<CaseManagement>

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

    @Subscribe("caseManagementsDataGrid.create")
    fun onCaseManagementsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: CaseManagement = dataContext.create(CaseManagement::class.java)
        caseManagementDc.item = entity
        updateControls(true)
    }

    @Subscribe("caseManagementsDataGrid.edit")
    fun onCaseManagementsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        caseManagementsDc.replaceItem(caseManagementDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        caseManagementDl.load()
        updateControls(false)
    }

    @Subscribe(id = "caseManagementsDc", target = Target.DATA_CONTAINER)
    fun onCaseManagementsDcItemChange(event: InstanceContainer.ItemChangeEvent<CaseManagement>) {
        val entity: CaseManagement? = event.item
        dataContext.clear()
        if (entity != null) {
            caseManagementDl.entityId = entity.id
            caseManagementDl.load()
        } else {
            caseManagementDl.entityId = null
            caseManagementDc.setItem(null)
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