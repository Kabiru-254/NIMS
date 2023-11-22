package com.company.nemademo.view.toxicharzardregistration

import com.company.nemademo.entity.ToxicHarzardRegistration
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

@Route(value = "toxicHarzardRegistrations", layout = MainView::class)
@ViewController("ToxicHarzardRegistration.list")
@ViewDescriptor("toxic-harzard-registration-list-view.xml")
@LookupComponent("toxicHarzardRegistrationsDataGrid")
@DialogMode(width = "64em")
class ToxicHarzardRegistrationListView : StandardListView<ToxicHarzardRegistration>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var toxicHarzardRegistrationsDc: CollectionContainer<ToxicHarzardRegistration>

    @ViewComponent
    private lateinit var toxicHarzardRegistrationDc: InstanceContainer<ToxicHarzardRegistration>

    @ViewComponent
    private lateinit var toxicHarzardRegistrationDl: InstanceLoader<ToxicHarzardRegistration>

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

    @Subscribe("toxicHarzardRegistrationsDataGrid.create")
    fun onToxicHarzardRegistrationsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: ToxicHarzardRegistration = dataContext.create(ToxicHarzardRegistration::class.java)
        toxicHarzardRegistrationDc.item = entity
        updateControls(true)
    }

    @Subscribe("toxicHarzardRegistrationsDataGrid.edit")
    fun onToxicHarzardRegistrationsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        toxicHarzardRegistrationsDc.replaceItem(toxicHarzardRegistrationDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        toxicHarzardRegistrationDl.load()
        updateControls(false)
    }

    @Subscribe(id = "toxicHarzardRegistrationsDc", target = Target.DATA_CONTAINER)
    fun onToxicHarzardRegistrationsDcItemChange(event: InstanceContainer.ItemChangeEvent<ToxicHarzardRegistration>) {
        val entity: ToxicHarzardRegistration? = event.item
        dataContext.clear()
        if (entity != null) {
            toxicHarzardRegistrationDl.entityId = entity.id
            toxicHarzardRegistrationDl.load()
        } else {
            toxicHarzardRegistrationDl.entityId = null
            toxicHarzardRegistrationDc.setItem(null)
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