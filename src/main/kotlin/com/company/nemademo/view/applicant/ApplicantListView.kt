package com.company.nemademo.view.applicant

import com.company.nemademo.entity.Applicant
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

@Route(value = "applicants", layout = MainView::class)
@ViewController("Applicant.list")
@ViewDescriptor("applicant-list-view.xml")
@LookupComponent("applicantsDataGrid")
@DialogMode(width = "64em")
class ApplicantListView : StandardListView<Applicant>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var applicantsDc: CollectionContainer<Applicant>

    @ViewComponent
    private lateinit var applicantDc: InstanceContainer<Applicant>

    @ViewComponent
    private lateinit var applicantDl: InstanceLoader<Applicant>

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

    @Subscribe("applicantsDataGrid.create")
    fun onApplicantsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: Applicant = dataContext.create(Applicant::class.java)
        applicantDc.item = entity
        updateControls(true)
    }

    @Subscribe("applicantsDataGrid.edit")
    fun onApplicantsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        applicantsDc.replaceItem(applicantDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        applicantDl.load()
        updateControls(false)
    }

    @Subscribe(id = "applicantsDc", target = Target.DATA_CONTAINER)
    fun onApplicantsDcItemChange(event: InstanceContainer.ItemChangeEvent<Applicant>) {
        val entity: Applicant? = event.item
        dataContext.clear()
        if (entity != null) {
            applicantDl.entityId = entity.id
            applicantDl.load()
        } else {
            applicantDl.entityId = null
            applicantDc.setItem(null)
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