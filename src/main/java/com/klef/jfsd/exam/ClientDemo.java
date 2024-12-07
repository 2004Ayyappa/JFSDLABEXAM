package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        // Initialize Hibernate
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        // Insert Records
        Customer customer1 = new Customer();
        customer1.setName("John Doe");
        customer1.setEmail("john.doe@example.com");
        customer1.setAge(30);
        customer1.setLocation("New York");

        Customer customer2 = new Customer();
        customer2.setName("Jane Smith");
        customer2.setEmail("jane.smith@example.com");
        customer2.setAge(25);
        customer2.setLocation("California");

        session.save(customer1);
        session.save(customer2);
        tx.commit();

        // Use JPA Criteria API
        System.out.println("\n--- Equal Restriction ---");
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);

        // Equal Restriction
        query.select(root).where(builder.equal(root.get("location"), "New York"));
        List<Customer> customers1 = session.createQuery(query).getResultList();
        customers1.forEach(c -> System.out.println(c.getName()));

        System.out.println("\n--- Between Restriction ---");
        query.select(root).where(builder.between(root.get("age"), 20, 30));
        List<Customer> customers2 = session.createQuery(query).getResultList();
        customers2.forEach(c -> System.out.println(c.getName()));

        System.out.println("\n--- Like Restriction ---");
        query.select(root).where(builder.like(root.get("name"), "Jane%"));
        List<Customer> customers3 = session.createQuery(query).getResultList();
        customers3.forEach(c -> System.out.println(c.getName()));

        session.close();
        factory.close();
    }
}
