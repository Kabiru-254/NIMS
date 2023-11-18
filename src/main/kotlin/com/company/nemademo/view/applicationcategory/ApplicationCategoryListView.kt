package com.company.nemademo.view.applicationcategory

import com.company.nemademo.entity.ApplicationCategory
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

@Route(value = "applicationCategories", layout = MainView::class)
@ViewController("ApplicationCategory.list")
@ViewDescriptor("application-category-list-view.xml")
@LookupComponent("applicationCategoriesDataGrid")
@DialogMode(width = "64em")
class ApplicationCategoryListView : StandardListView<ApplicationCategory>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var applicationCategoriesDc: CollectionContainer<ApplicationCategory>

    @ViewComponent
    private lateinit var applicationCategoryDc: InstanceContainer<ApplicationCategory>

    @ViewComponent
    private lateinit var applicationCategoryDl: InstanceLoader<ApplicationCategory>

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

    @Subscribe("applicationCategoriesDataGrid.create")
    fun onApplicationCategoriesDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: ApplicationCategory = dataContext.create(ApplicationCategory::class.java)
        applicationCategoryDc.item = entity
        updateControls(true)
    }

    @Subscribe("applicationCategoriesDataGrid.edit")
    fun onApplicationCategoriesDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        applicationCategoriesDc.replaceItem(applicationCategoryDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        applicationCategoryDl.load()
        updateControls(false)
    }

    @Subscribe(id = "applicationCategoriesDc", target = Target.DATA_CONTAINER)
    fun onApplicationCategoriesDcItemChange(event: InstanceContainer.ItemChangeEvent<ApplicationCategory>) {
        val entity: ApplicationCategory? = event.item
        dataContext.clear()
        if (entity != null) {
            applicationCategoryDl.entityId = entity.id
            applicationCategoryDl.load()
        } else {
            applicationCategoryDl.entityId = null
            applicationCategoryDc.setItem(null)
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