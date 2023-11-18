package com.company.nemademo.view.review

import com.company.nemademo.entity.Review
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

@Route(value = "reviews", layout = MainView::class)
@ViewController("Review.list")
@ViewDescriptor("review-list-view.xml")
@LookupComponent("reviewsDataGrid")
@DialogMode(width = "64em")
class ReviewListView : StandardListView<Review>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var reviewsDc: CollectionContainer<Review>

    @ViewComponent
    private lateinit var reviewDc: InstanceContainer<Review>

    @ViewComponent
    private lateinit var reviewDl: InstanceLoader<Review>

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

    @Subscribe("reviewsDataGrid.create")
    fun onReviewsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: Review = dataContext.create(Review::class.java)
        reviewDc.item = entity
        updateControls(true)
    }

    @Subscribe("reviewsDataGrid.edit")
    fun onReviewsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        reviewsDc.replaceItem(reviewDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        reviewDl.load()
        updateControls(false)
    }

    @Subscribe(id = "reviewsDc", target = Target.DATA_CONTAINER)
    fun onReviewsDcItemChange(event: InstanceContainer.ItemChangeEvent<Review>) {
        val entity: Review? = event.item
        dataContext.clear()
        if (entity != null) {
            reviewDl.entityId = entity.id
            reviewDl.load()
        } else {
            reviewDl.entityId = null
            reviewDc.setItem(null)
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