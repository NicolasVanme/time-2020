package org.timetracking.persistence;

import org.timetracking.persistence.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InitialLoadService {

    private final PersistenceService persistenceService;

    public InitialLoadService(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public void load() {
        List<Object> initialData = new ArrayList<>();
        initialData.addAll(getEmployees());
        initialData.addAll(getCostCenters());
        initialData.addAll(getTaskCodes());
        persistenceService.saveAll(initialData);

        List<Project> projects = getProjects();
        persistenceService.saveAll(projects);

        List<Task> tasks = getTasks();
        persistenceService.saveAll(tasks);
    }

    private List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();

        Employee employee = new Employee();
        employee.setFirstName("Pierre");
        employee.setLastName("Nkurunziza");
        employee.setStart(LocalDateTime.now());
        employees.add(employee);

        employee = new Employee();
        employee.setFirstName("Melchior");
        employee.setLastName("Ndadaye");
        employee.setStart(LocalDateTime.now());
        employees.add(employee);

        employee = new Employee();
        employee.setFirstName("Paul");
        employee.setLastName("Kagame");
        employee.setStart(LocalDateTime.now());
        employees.add(employee);

        return employees;
    }

    private List<CostCenter> getCostCenters() {
        List<CostCenter> costCenters = new ArrayList<>();

        CostCenter costCenter = new CostCenter();
        costCenter.setName("Marketing department");
        costCenters.add(costCenter);

        costCenter = new CostCenter();
        costCenter.setName("Human resources");
        costCenters.add(costCenter);

        costCenter = new CostCenter();
        costCenter.setName("Research and development");
        costCenters.add(costCenter);

        costCenter = new CostCenter();
        costCenter.setName("Work office");
        costCenters.add(costCenter);

        costCenter = new CostCenter();
        costCenter.setName("Logistics");
        costCenters.add(costCenter);

        return costCenters;
    }

    private List<TaskCode> getTaskCodes() {
        List<TaskCode> taskCodes = new ArrayList<>();

        TaskCode taskCode = new TaskCode();
        taskCode.setName("Analyse");
        taskCodes.add(taskCode);

        taskCode = new TaskCode();
        taskCode.setName("Development");
        taskCodes.add(taskCode);

        taskCode = new TaskCode();
        taskCode.setName("Testing");
        taskCodes.add(taskCode);

        return taskCodes;
    }

    private List<Project> getProjects() {
        List<Project> projects = new ArrayList<>();

        persistenceService.findCostCenterByName("Research and development")
                .map(costCenter -> {
                    Project project = new Project();
                    project.setCostCenter(costCenter);
                    project.setName("Time Tracking Application");
                    project.setComment("Development of a Time Tracking application");
                    return project;
                }).ifPresent(projects::add);

        return projects;
    }

    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();

        persistenceService.findProjectByName("Time Tracking Application")
                .map(project -> {
                    Task task = new Task();
                    task.setProject(project);
                    task.setClosed(false);
                    // TODO - Set link to a task code
                    return task;
                }).ifPresent(tasks::add);

        return tasks;
    }
}
