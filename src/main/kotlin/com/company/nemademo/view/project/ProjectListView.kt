package com.company.nemademo.view.project

import com.company.nemademo.entity.Project
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

@Route(value = "projects", layout = MainView::class)
@ViewController("Project.list")
@ViewDescriptor("project-list-view.xml")
@LookupComponent("projectsDataGrid")
@DialogMode(width = "64em")
class ProjectListView : StandardListView<Project>() {

    @ViewComponent
    private lateinit var dataContext: DataContext

    @ViewComponent
    private lateinit var projectsDc: CollectionContainer<Project>

    @ViewComponent
    private lateinit var projectDc: InstanceContainer<Project>

    @ViewComponent
    private lateinit var projectDl: InstanceLoader<Project>

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

    @Subscribe("projectsDataGrid.create")
    fun onProjectsDataGridCreate(event: ActionPerformedEvent) {
        dataContext.clear()
        val entity: Project = dataContext.create(Project::class.java)
        projectDc.item = entity
        updateControls(true)
    }

    @Subscribe("projectsDataGrid.edit")
    fun onProjectsDataGridEdit(event: ActionPerformedEvent) {
        updateControls(true)
    }

    @Subscribe("saveBtn")
    fun onSaveButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.save()
        projectsDc.replaceItem(projectDc.item)
        updateControls(false)
    }

    @Subscribe("cancelBtn")
    fun onCancelButtonClick(event: ClickEvent<JmixButton>) {
        dataContext.clear()
        projectDl.load()
        updateControls(false)
    }

    @Subscribe(id = "projectsDc", target = Target.DATA_CONTAINER)
    fun onProjectsDcItemChange(event: InstanceContainer.ItemChangeEvent<Project>) {
        val entity: Project? = event.item
        dataContext.clear()
        if (entity != null) {
            projectDl.entityId = entity.id
            projectDl.load()
        } else {
            projectDl.entityId = null
            projectDc.setItem(null)
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