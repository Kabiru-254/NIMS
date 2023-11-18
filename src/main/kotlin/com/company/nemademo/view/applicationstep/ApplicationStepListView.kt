package com.company.nemademo.view.applicationstep

import com.company.nemademo.entity.LicenceApplicationStep
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

@Route(value = "applicationSteps", layout = MainView::class)
@ViewController("LicenceApplicationStep.list")
@ViewDescriptor("application-step-list-view.xml")
@LookupComponent("applicationStepsDataGrid")
@DialogMode(width = "64em")
class ApplicationStepListView : StandardListView<LicenceApplicationStep>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var licenceApplicationStepsDc: CollectionContainer<LicenceApplicationStep>

    @ViewComponent
    private lateinit var licenceApplicationStepDc: InstanceContainer<LicenceApplicationStep>

    @ViewComponent
    private lateinit var licenceApplicationStepDl: InstanceLoader<LicenceApplicationStep>

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

    @Subscribe("applicationStepsDataGrid.create")
    fun onApplicationStepsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: LicenceApplicationStep = dataContext.create(LicenceApplicationStep::class.java)
        licenceApplicationStepDc.item = entity
        updateControls(true)
    }

    @Subscribe("applicationStepsDataGrid.edit")
    fun onApplicationStepsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        licenceApplicationStepsDc.replaceItem(licenceApplicationStepDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        licenceApplicationStepDl.load()
        updateControls(false)
    }

    @Subscribe(id = "licenceApplicationStepsDc", target = Target.DATA_CONTAINER)
    fun onApplicationStepsDcItemChange(event: InstanceContainer.ItemChangeEvent<LicenceApplicationStep>) {
        val entity: LicenceApplicationStep? = event.item
        dataContext.clear()
        if (entity != null) {
            licenceApplicationStepDl.entityId = entity.id
            licenceApplicationStepDl.load()
        } else {
            licenceApplicationStepDl.entityId = null
            licenceApplicationStepDc.setItem(null)
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