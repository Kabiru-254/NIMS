package com.company.nemademo.view.clearancecertificatereview

import com.company.nemademo.entity.ClearanceCertificateReview
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

@Route(value = "clearanceCertificateReviews", layout = MainView::class)
@ViewController("ClearanceCertificateReview.list")
@ViewDescriptor("clearance-certificate-review-list-view.xml")
@LookupComponent("clearanceCertificateReviewsDataGrid")
@DialogMode(width = "64em")
class ClearanceCertificateReviewListView : StandardListView<ClearanceCertificateReview>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var clearanceCertificateReviewsDc: CollectionContainer<ClearanceCertificateReview>

    @ViewComponent
    private lateinit var clearanceCertificateReviewDc: InstanceContainer<ClearanceCertificateReview>

    @ViewComponent
    private lateinit var clearanceCertificateReviewDl: InstanceLoader<ClearanceCertificateReview>

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

    @Subscribe("clearanceCertificateReviewsDataGrid.create")
    fun onClearanceCertificateReviewsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: ClearanceCertificateReview = dataContext.create(ClearanceCertificateReview::class.java)
        clearanceCertificateReviewDc.item = entity
        updateControls(true)
    }

    @Subscribe("clearanceCertificateReviewsDataGrid.edit")
    fun onClearanceCertificateReviewsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        clearanceCertificateReviewsDc.replaceItem(clearanceCertificateReviewDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        clearanceCertificateReviewDl.load()
        updateControls(false)
    }

    @Subscribe(id = "clearanceCertificateReviewsDc", target = Target.DATA_CONTAINER)
    fun onClearanceCertificateReviewsDcItemChange(event: InstanceContainer.ItemChangeEvent<ClearanceCertificateReview>) {
        val entity: ClearanceCertificateReview? = event.item
        dataContext.clear()
        if (entity != null) {
            clearanceCertificateReviewDl.entityId = entity.id
            clearanceCertificateReviewDl.load()
        } else {
            clearanceCertificateReviewDl.entityId = null
            clearanceCertificateReviewDc.setItem(null)
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