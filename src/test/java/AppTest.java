import entity.Address;
import entity.People;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import utils.HibernateUtilFactory;

import javax.persistence.EntityManager;

class AppTest {

    @Test
    void save() {
        Address address = Address.builder()
                .street("Baramzinoy")
                .city("Minsk")
                .house("22")
                .build();

        People people = People.builder()
                .name("Ivan")
                .middlename("Ivanovich")
                .surname("Ivanov")
                .build();

        EntityManager em = HibernateUtilFactory.getEntityManager();
        em.getTransaction().begin();
        em.persist(address);
        em.persist(people);
        People testPeople = em.find(People.class, people.getId());
        Address testAddress = em.find(Address.class, address.getId());
        Assert.assertEquals(address, testAddress);
        Assert.assertEquals(people, testPeople);
    }

    @Test
    void delete() {
        Address address = Address.builder()
                .street("Mendeleeva")
                .city("Moscow")
                .house("15")
                .build();

        People people = People.builder()
                .name("Ivan")
                .middlename("Ivanovich")
                .surname("Ivanov")
                .build();
        EntityManager em = HibernateUtilFactory.getEntityManager();
        em.getTransaction().begin();
        em.persist(address);
        em.persist(people);
        Address testAddress = em.find(Address.class, address.getId());
        People testPeople = em.find(People.class, people.getId());
        Assert.assertNotNull(testAddress);
        Assert.assertNotNull(testPeople);
        em.remove(testPeople);
        em.remove(testAddress);
        Assert.assertNull(em.find(Address.class, address.getId()));
        Assert.assertNull(em.find(People.class, people.getId()));
        em.close();
    }

    @Test
    void update() {
        Address address = Address.builder()
                .street("Baker")
                .city("London")
                .house("221b")
                .build();

        People people = People.builder()
                .name("Ivan")
                .middlename("Ivanovich")
                .surname("Ivanov")
                .build();

        EntityManager em = HibernateUtilFactory.getEntityManager();
        em.getTransaction().begin();
        em.persist(address);
        em.persist(people);
        Address testAddress = em.find(Address.class, address.getId());
        People testPeople = em.find(People.class, people.getId());
        Assert.assertEquals(address, testAddress);
        Assert.assertEquals(people, testPeople);

        address.setCity("Minsk");
        address.setHouse("14");
        address.setStreet("Lenina");

        people.setSurname("Malisheva");
        people.setName("Elena");
        people.setMiddlename("Igorevna");
        em.merge(address);
        em.merge(people);
        em.getTransaction().commit();
        Address testAddress1 = em.find(Address.class, address.getId());
        People testPeople2 = em.find(People.class, people.getId());
        Assert.assertEquals(address, testAddress1);
        Assert.assertEquals(people, testPeople2);
        em.close();
    }

    @AfterClass
    public static void cleanUp() {
        HibernateUtilFactory.close();
    }
}