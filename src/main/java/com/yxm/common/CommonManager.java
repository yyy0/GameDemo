package com.yxm.common;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


/**
 * 抽象数据持续化，增删改查
 * @author yuxianming
 * @date 2019/4/29 17:44
 */
@Component
public class CommonManager<K, T> {

    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private EntityTransaction transaction;


    @PostConstruct
    private final void init() {
        factory = Persistence.createEntityManagerFactory("PersistenceUnit");
        entityManager = entityManager = factory.createEntityManager();

        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    public void createEnt(T entity) {

        try {
            transBegin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void removeEnt(T entity) {
        transBegin();
        entityManager.remove(entity);
        transaction.commit();
    }

    public T getEnt(Class entClass, K id) {

        transBegin();
        //entClass = (Class<T>) getEntityClass();
        T entity = (T) entityManager.find(entClass, id);

        transaction.commit();
        return entity;
    }

    public void update(T entity) {
        transBegin();
        transaction.commit();
    }

    public void transBegin() {
        if (!transaction.isActive()) {
            transaction.begin();
        }
    }

}
