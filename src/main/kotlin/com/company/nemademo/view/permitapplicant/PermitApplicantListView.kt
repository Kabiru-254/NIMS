package com.company.nemademo.view.permitapplicant

import com.company.nemademo.entity.PermitApplicant
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

@Route(value = "permitApplicants", layout = MainView::class)
@ViewController("PermitApplicant.list")
@ViewDescriptor("permit-applicant-list-view.xml")
@LookupComponent("permitApplicantsDataGrid")
@DialogMode(width = "64em")
class PermitApplicantListView : StandardListView<PermitApplicant>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var permitApplicantsDc: CollectionContainer<PermitApplicant>

    @ViewComponent
    private lateinit var permitApplicantDc: InstanceContainer<PermitApplicant>

    @ViewComponent
    private lateinit var permitApplicantDl: InstanceLoader<PermitApplicant>

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

    @Subscribe("permitApplicantsDataGrid.create")
    fun onPermitApplicantsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: PermitApplicant = dataContext.create(PermitApplicant::class.java)
        permitApplicantDc.item = entity
        updateControls(true)
    }

    @Subscribe("permitApplicantsDataGrid.edit")
    fun onPermitApplicantsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        permitApplicantsDc.replaceItem(permitApplicantDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        permitApplicantDl.load()
        updateControls(false)
    }

    @Subscribe(id = "permitApplicantsDc", target = Target.DATA_CONTAINER)
    fun onPermitApplicantsDcItemChange(event: InstanceContainer.ItemChangeEvent<PermitApplicant>) {
        val entity: PermitApplicant? = event.item
        dataContext.clear()
        if (entity != null) {
            permitApplicantDl.entityId = entity.id
            permitApplicantDl.load()
        } else {
            permitApplicantDl.entityId = null
            permitApplicantDc.setItem(null)
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