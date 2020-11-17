package org.timetracking.console;

import org.timetracking.persistence.PersistenceService;
import org.timetracking.persistence.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class TimeTrackingConsole {

    private final PersistenceService persistenceService;

    public TimeTrackingConsole(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public void firstMenu() {
        while (true) {
            clearConsole();

            System.out.println("*** TIME  TRACKING ***\n");
            System.out.println("*** Principal Menu ***");
            System.out.println("");
            System.out.println("1. Display Project");
            System.out.println("2. Display Employee");
            System.out.println("3. Display Task");
            System.out.println("4. Display Cost Center");
            System.out.println("5. Display TaskCode");
            System.out.println("6. Display Time Sheet");
            System.out.println("\n9. Close Programme");

            String input = getChar();
            switch (input) {
                case "1":
                    displayProject();
                    break;
                case "2":
                    displayEmployee();
                    break;
                case "3":
                    displayTask();
                    break;
                case "4":
                    displayCostCenter();
                    break;
                case "5":
                    displayTaskCode();
                    break;
                case "6":
                    displayTimeTracking();
                    break;
                case "9":
                    return;
                default:
                    break;
            }
        }
    }

    private void displayProject() {
        while (true) {
            clearConsole();

            System.out.println("*** PROJECT ***\n");

            List<Project> projects = persistenceService.findAllProjects();
            projects.forEach(System.out::println);

            System.out.println("");
            System.out.println("1. Add a project");
            System.out.println("2. Exit");
            System.out.println("");

            String input = getChar();
            switch (input) {
                case "1":
                    addProject();
                    break;
                case "2":
                    return;
                default:
                    break;
            }
        }
    }

    private void addProject() {
        Project newProject = new Project();

        clearConsole();
        System.out.println("*** ADD NEW PROJECT ***\n");
        System.out.println("Project name:");
        newProject.setName(getChar());

        List<CostCenter> costcenters = persistenceService.findAllCostCenters();
        costcenters.forEach(System.out::println);

        System.out.println("\nCost center id:");
        CostCenter costCenter = persistenceService.findCostCenterById(Integer.parseInt(getChar()))
                .orElseThrow(IllegalArgumentException::new);

        newProject.setCostCenter(costCenter);
        System.out.println(newProject.getCostCenter().getName());

        System.out.println("Comment:");
        newProject.setComment(getChar());

        persistenceService.save(newProject);
    }

    private void displayEmployee() {
        while (true) {
            clearConsole();
            System.out.println("*** EMPLOYEE ***\n");

            List<Employee> employees = persistenceService.findAllEmployees();

            employees.forEach(System.out::println);

            System.out.println("");
            System.out.println("1. Add a employee");
            System.out.println("2. Exit");
            System.out.println("");

            String input = getChar();
            switch (input) {
                case "1":
                    addEmployee();
                    break;
                case "2":
                    return;
                default:
                    break;
            }
        }
    }

    private void addEmployee() {
        Employee newEmployee = new Employee();

        clearConsole();
        System.out.println("*** ADD NEW EMPLOYEE ***\n");
        System.out.println("Employee first name:");
        newEmployee.setFirstName(getChar());

        System.out.println("Employee last name:");
        newEmployee.setLastName(getChar());

        newEmployee.setStart(LocalDateTime.now());

        persistenceService.save(newEmployee);
    }

    private void displayTaskCode(){
        while (true) {
            clearConsole();
            System.out.println("*** Task Code ***\n");

            List<TaskCode> tasksCode = persistenceService.findAllTaskCode();
            tasksCode.forEach(System.out::println);

            System.out.println("");
            System.out.println("1. Add a TaskCode");
            System.out.println("2. Exit");
            System.out.println("");

            String input = getChar();
            switch (input) {
                case "1":
                    addTaskCode();
                    break;
                case "2":
                    return;
                default:
                    break;
            }
        }
    }

    private void addTaskCode(){
        TaskCode newTaskCode = new TaskCode();

        clearConsole();
        System.out.println("*** ADD NEW TaskCode ***\n");
        System.out.println("Name:");
        newTaskCode.setName(getChar());

        System.out.println("Comment:");
        newTaskCode.setComment(getChar());

        persistenceService.save(newTaskCode);
    }

    private void displayTask() {
        while (true) {
            clearConsole();
            System.out.println("*** Tasks ***\n");

            List<Task> tasks = persistenceService.findAllTasks();

            tasks.forEach(System.out::println);

            System.out.println("");
            System.out.println("1. Add a Task");
            System.out.println("2. Exit");
            System.out.println("");

            String input = getChar();
            switch (input) {
                case "1":
                    addTask();
                    break;
                case "2":
                    return;
                default:
                    break;
            }
        }
    }

    private void addTask() {
        Task newTask = new Task();

        clearConsole();
        System.out.println("*** ADD NEW TASK ***\n");

        List<Project> projects = persistenceService.findAllProjects();
        projects.forEach(System.out::println);
        System.out.println("Project ID:");

        Project project = persistenceService.findProjectById(Integer.parseInt(getChar()))
                .orElseThrow(IllegalArgumentException::new);
        newTask.setProject(project);

        List<TaskCode> tasksCode = persistenceService.findAllTaskCode();
        tasksCode.forEach(System.out::println);
        System.out.println("Task code ID:");

        TaskCode taskCode = persistenceService.findTaskCodeById(Integer.parseInt(getChar()))
                .orElseThrow(IllegalArgumentException::new);
        newTask.setTaskCode(taskCode);


        System.out.println("Comment:");
        newTask.setComment(getChar());

        persistenceService.save(newTask);
    }

    private void displayTimeTracking(){
        while (true) {
            clearConsole();
            System.out.println("*** Time Sheet ***\n");

            List<TimeTracking> timeSheets = persistenceService.findAllTimeTracking();

            timeSheets.forEach(System.out::println);

            System.out.println("");
            System.out.println("1. Exit");
            System.out.println("");

            String input = getChar();
            switch (input) {
                case "1":
                    return;
                default:
                    break;
            }
        }
    }

    private void displayCostCenter() {
        while (true) {

            clearConsole();
            System.out.println("*** Costs Centers ***\n");

            List<CostCenter> costCenters = persistenceService.findAllCostCenters();

            costCenters.forEach(System.out::println);

            System.out.println("");
            System.out.println("1. Add a Cost Center");
            System.out.println("2. Exit");
            System.out.println("");


            String input = getChar();
            switch (input) {
                case "1":
                    addCostCenter();
                    break;
                case "2":
                    return;
                default:
                    break;
            }
        }
    }

    private void addCostCenter() {
        CostCenter newCostCenter = new CostCenter();

        clearConsole();
        System.out.println("*** ADD NEW COST CENTER ***\n");
        System.out.println("Name:");
        newCostCenter.setName(getChar());

        System.out.println("Comment:");
        newCostCenter.setComment(getChar());

        persistenceService.save(newCostCenter);
    }

    private String getChar() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        /*try {
            Runtime.getRuntime().exec("clear");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            // e.printStackTrace();
        }*/
    }
}
