import entity.Address;
import entity.People;
import utils.HibernateUtilFactory;

import javax.persistence.EntityManager;

public class App {

    public static void main(String[] args) {

        People people1 = People.builder()
                .name("Roman")
                .surname("Roman")
                .middlename("Roman")
                .build();


        Address address1 = Address.builder()
                .city("Paris")
                .street("Montaigne")
                .house("14")
                .build();


        EntityManager em = HibernateUtilFactory.getEntityManager();

        save(address1, em);
        findId(Address.class, em, 1);
        delete(Address.class, em, 1);


        save(people1, em);
        findId(People.class, em, 1);
        delete(People.class, em, 1);

        em.getTransaction().begin();
        em.persist(people1);
        people1.setName("Bogdan");
        people1.setSurname("Alibabaev");
        people1.setMiddlename("Alivavaevich");
        em.merge(people1);
        em.getTransaction().commit();
        em.close();


        HibernateUtilFactory.close();


    }

    public static void delete(Class aClazz, EntityManager em, int id) {
        em.getTransaction().begin();
        Object entity = em.find(aClazz, id);
        em.remove(entity);
        em.getTransaction().commit();
        em.close();
    }

    public static Object findId(Class aClazz, EntityManager em, int id) {
        em.getTransaction().begin();
        Object entity = em.find(aClazz, id);
        System.out.println(entity.toString());
        em.close();
        return entity;
    }

    public static void save(Object entity, EntityManager em) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
    }


}
