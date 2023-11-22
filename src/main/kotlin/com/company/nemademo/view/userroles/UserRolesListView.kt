package com.company.nemademo.view.userroles

import com.company.nemademo.entity.UserRoles
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

@Route(value = "userRoleses", layout = MainView::class)
@ViewController("UserRoles.list")
@ViewDescriptor("user-roles-list-view.xml")
@LookupComponent("userRolesesDataGrid")
@DialogMode(width = "64em")
class UserRolesListView : StandardListView<UserRoles>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var userRolesesDc: CollectionContainer<UserRoles>

    @ViewComponent
    private lateinit var userRolesDc: InstanceContainer<UserRoles>

    @ViewComponent
    private lateinit var userRolesDl: InstanceLoader<UserRoles>

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

    @Subscribe("userRolesesDataGrid.create")
    fun onUserRolesesDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: UserRoles = dataContext.create(UserRoles::class.java)
        userRolesDc.item = entity
        updateControls(true)
    }

    @Subscribe("userRolesesDataGrid.edit")
    fun onUserRolesesDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        userRolesesDc.replaceItem(userRolesDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        userRolesDl.load()
        updateControls(false)
    }

    @Subscribe(id = "userRolesesDc", target = Target.DATA_CONTAINER)
    fun onUserRolesesDcItemChange(event: InstanceContainer.ItemChangeEvent<UserRoles>) {
        val entity: UserRoles? = event.item
        dataContext.clear()
        if (entity != null) {
            userRolesDl.entityId = entity.id
            userRolesDl.load()
        } else {
            userRolesDl.entityId = null
            userRolesDc.setItem(null)
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