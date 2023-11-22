package com.company.nemademo.view.permitsreview

import com.company.nemademo.entity.PermitsReview
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

@Route(value = "permitsReviews", layout = MainView::class)
@ViewController("PermitsReview.list")
@ViewDescriptor("permits-review-list-view.xml")
@LookupComponent("permitsReviewsDataGrid")
@DialogMode(width = "64em")
class PermitsReviewListView : StandardListView<PermitsReview>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var permitsReviewsDc: CollectionContainer<PermitsReview>

    @ViewComponent
    private lateinit var permitsReviewDc: InstanceContainer<PermitsReview>

    @ViewComponent
    private lateinit var permitsReviewDl: InstanceLoader<PermitsReview>

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

    @Subscribe("permitsReviewsDataGrid.create")
    fun onPermitsReviewsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: PermitsReview = dataContext.create(PermitsReview::class.java)
        permitsReviewDc.item = entity
        updateControls(true)
    }

    @Subscribe("permitsReviewsDataGrid.edit")
    fun onPermitsReviewsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        permitsReviewsDc.replaceItem(permitsReviewDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        permitsReviewDl.load()
        updateControls(false)
    }

    @Subscribe(id = "permitsReviewsDc", target = Target.DATA_CONTAINER)
    fun onPermitsReviewsDcItemChange(event: InstanceContainer.ItemChangeEvent<PermitsReview>) {
        val entity: PermitsReview? = event.item
        dataContext.clear()
        if (entity != null) {
            permitsReviewDl.entityId = entity.id
            permitsReviewDl.load()
        } else {
            permitsReviewDl.entityId = null
            permitsReviewDc.setItem(null)
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