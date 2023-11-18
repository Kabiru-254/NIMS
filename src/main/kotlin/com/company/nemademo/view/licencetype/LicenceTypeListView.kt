package com.company.nemademo.view.licencetype

import com.company.nemademo.entity.LicenceType
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

@Route(value = "licenceTypes", layout = MainView::class)
@ViewController("LicenceType.list")
@ViewDescriptor("licence-type-list-view.xml")
@LookupComponent("licenceTypesDataGrid")
@DialogMode(width = "64em")
class LicenceTypeListView : StandardListView<LicenceType>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var licenceTypesDc: CollectionContainer<LicenceType>

    @ViewComponent
    private lateinit var licenceTypeDc: InstanceContainer<LicenceType>

    @ViewComponent
    private lateinit var licenceTypeDl: InstanceLoader<LicenceType>

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

    @Subscribe("licenceTypesDataGrid.create")
    fun onLicenceTypesDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: LicenceType = dataContext.create(LicenceType::class.java)
        licenceTypeDc.item = entity
        updateControls(true)
    }

    @Subscribe("licenceTypesDataGrid.edit")
    fun onLicenceTypesDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        licenceTypesDc.replaceItem(licenceTypeDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        licenceTypeDl.load()
        updateControls(false)
    }

    @Subscribe(id = "licenceTypesDc", target = Target.DATA_CONTAINER)
    fun onLicenceTypesDcItemChange(event: InstanceContainer.ItemChangeEvent<LicenceType>) {
        val entity: LicenceType? = event.item
        dataContext.clear()
        if (entity != null) {
            licenceTypeDl.entityId = entity.id
            licenceTypeDl.load()
        } else {
            licenceTypeDl.entityId = null
            licenceTypeDc.setItem(null)
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