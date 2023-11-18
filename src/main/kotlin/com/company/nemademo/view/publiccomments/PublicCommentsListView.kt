package com.company.nemademo.view.publiccomments

import com.company.nemademo.entity.PublicComments
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

@Route(value = "publicCommentses", layout = MainView::class)
@ViewController("PublicComments.list")
@ViewDescriptor("public-comments-list-view.xml")
@LookupComponent("publicCommentsesDataGrid")
@DialogMode(width = "64em")
class PublicCommentsListView : StandardListView<PublicComments>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var publicCommentsesDc: CollectionContainer<PublicComments>

    @ViewComponent
    private lateinit var publicCommentsDc: InstanceContainer<PublicComments>

    @ViewComponent
    private lateinit var publicCommentsDl: InstanceLoader<PublicComments>

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

    @Subscribe("publicCommentsesDataGrid.create")
    fun onPublicCommentsesDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: PublicComments = dataContext.create(PublicComments::class.java)
        publicCommentsDc.item = entity
        updateControls(true)
    }

    @Subscribe("publicCommentsesDataGrid.edit")
    fun onPublicCommentsesDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        publicCommentsesDc.replaceItem(publicCommentsDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        publicCommentsDl.load()
        updateControls(false)
    }

    @Subscribe(id = "publicCommentsesDc", target = Target.DATA_CONTAINER)
    fun onPublicCommentsesDcItemChange(event: InstanceContainer.ItemChangeEvent<PublicComments>) {
        val entity: PublicComments? = event.item
        dataContext.clear()
        if (entity != null) {
            publicCommentsDl.entityId = entity.id
            publicCommentsDl.load()
        } else {
            publicCommentsDl.entityId = null
            publicCommentsDc.setItem(null)
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