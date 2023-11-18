package com.company.nemademo.view.recommendation

import com.company.nemademo.entity.Recommendation
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

@Route(value = "recommendations", layout = MainView::class)
@ViewController("Recommendation.list")
@ViewDescriptor("recommendation-list-view.xml")
@LookupComponent("recommendationsDataGrid")
@DialogMode(width = "64em")
class RecommendationListView : StandardListView<Recommendation>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var recommendationsDc: CollectionContainer<Recommendation>

    @ViewComponent
    private lateinit var recommendationDc: InstanceContainer<Recommendation>

    @ViewComponent
    private lateinit var recommendationDl: InstanceLoader<Recommendation>

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

    @Subscribe("recommendationsDataGrid.create")
    fun onRecommendationsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: Recommendation = dataContext.create(Recommendation::class.java)
        recommendationDc.item = entity
        updateControls(true)
    }

    @Subscribe("recommendationsDataGrid.edit")
    fun onRecommendationsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        recommendationsDc.replaceItem(recommendationDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        recommendationDl.load()
        updateControls(false)
    }

    @Subscribe(id = "recommendationsDc", target = Target.DATA_CONTAINER)
    fun onRecommendationsDcItemChange(event: InstanceContainer.ItemChangeEvent<Recommendation>) {
        val entity: Recommendation? = event.item
        dataContext.clear()
        if (entity != null) {
            recommendationDl.entityId = entity.id
            recommendationDl.load()
        } else {
            recommendationDl.entityId = null
            recommendationDc.setItem(null)
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