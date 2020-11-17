package org.timetracking.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.timetracking.persistence.model.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;

public class PersistenceService implements AutoCloseable {

    private final SessionFactory sessionFactory;
    private final Session session;

    public PersistenceService() {
        // A SessionFactory is set up once for an application!
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();

        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            session = sessionFactory.openSession();
        } catch (Exception e) {
            e.printStackTrace();

            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually
            StandardServiceRegistryBuilder.destroy(registry);

            throw e;
        }
    }

    @Override
    public void close() {
        session.close();
        sessionFactory.close();
    }

    public void save(Object object) {
        saveAll(singletonList(object));
    }

    public void saveAll(Collection<? extends Object> objects) {
        Transaction transaction = session.beginTransaction();
        objects.forEach(session::persist);
        transaction.commit();
    }

    /************************************************************
     * Project queries
     ************************************************************/

    public List<Project> findAllProjects() {
        return this.session.createQuery("from Project").getResultList();
    }

    public Optional<Project> findProjectById(int projectId) {
        return session.createNamedQuery("findProjectById", Project.class)
                .setParameter("id", projectId)
                .getResultStream()
                .findFirst();
    }

    public Optional<Project> findProjectByName(String projectName) {
        return session.createNamedQuery("findProjectByName", Project.class)
                .setParameter("name", projectName)
                .getResultStream()
                .findFirst();
    }

    /************************************************************
     * Employee queries
     ************************************************************/

    public List<Employee> findAllEmployees() {
        return this.session.createQuery("from Employee").getResultList();
    }

    public Optional<Employee> findEmployeeById(int employeeId) {
        return session.createNamedQuery("findEmployeeById", Employee.class)
                .setParameter("id", employeeId)
                .getResultStream()
                .findFirst();
    }

    /************************************************************
     * TaskCode queries
     ************************************************************/

    public List<TaskCode> findAllTaskCode() {
        return this.session.createQuery("from TaskCode").getResultList();
    }

    public Optional<TaskCode> findTaskCodeById(int taskId) {
        return session.createNamedQuery("findTaskCodeById", TaskCode.class)
                .setParameter("id", taskId)
                .getResultStream()
                .findFirst();
    }

    /************************************************************
     * Task queries
     ************************************************************/

    public List<Task> findAllTasks() {
        return this.session.createQuery("from Task").getResultList();
    }

    public Optional<Task> findTaskById(int taskId) {
        return session.createNamedQuery("findTaskById", Task.class)
                .setParameter("id", taskId)
                .getResultStream()
                .findFirst();
    }

    /************************************************************
     * Costs center queries
     ************************************************************/

    public List<CostCenter> findAllCostCenters() {
        return this.session.createQuery("from CostCenter").getResultList();
    }

    public Optional<CostCenter> findCostCenterById(int costCenterId) {
        return session.createNamedQuery("findCostCenterById", CostCenter.class)
                .setParameter("id", costCenterId)
                .getResultStream()
                .findFirst();
    }

    public Optional<CostCenter> findCostCenterByName(String costCenterName) {
        return session.createNamedQuery("findCostCenterByName", CostCenter.class)
                .setParameter("name", costCenterName)
                .getResultStream()
                .findFirst();
    }

    /************************************************************
     * Time Tracking queries
     ************************************************************/

    public List<TimeTracking> findAllTimeTracking() {
        return this.session.createQuery("from TimeTracking").getResultList();
    }

    public List<TimeTracking> findStartedTimeTrackingByEmployeeId(int employeeId) {
        return this.session.createNamedQuery("findStartedTimeTrackingByEmployeeId", TimeTracking.class)
                .setParameter("employeeId",employeeId)
                .getResultList();
    }
}
