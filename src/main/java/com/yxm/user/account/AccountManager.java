package com.yxm.user.account;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuxianming
 * @date 2019/4/25 22:05
 */
@Component
public class AccountManager {

    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private EntityTransaction transaction;




    /**
     * 所有账号集合
     */
    private ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();


    @PostConstruct
    private void init() {
        factory = Persistence.createEntityManagerFactory("PersistenceUnit");
        entityManager = factory.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    /**
     * 插入对象 持久化
     */
    public void createAccount(Account account) {

        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            entityManager.persist(account);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }


    /**
     * 查询数据
     */
    public Account getAccount(String accountid) {
        // 执行查询sql  根据主键
        //Account account = entityManager.find(Account.class,"111");

        // find和getReference的区别----与hibernate中get和load的区别一样---懒加载--id没有对应值时报异常
        //getReference会抛异常  find不会
        Account account = entityManager.find(Account.class, accountid);
        if (account == null) {
            return null;
        }
        System.out.println(account);
        //transaction.commit();
        return account;
    }

    /**
     * 修改信息
     */
    public void update(Account account) {
        // 执行查询sql  根据主键
//        Account account = entityManager.find(Account.class,"111");
//        account.setPwd("765");
        //entityManager.refresh(account);
        if (!transaction.isActive()) {
            transaction.begin();
        }
        transaction.commit();

    }

    /**
     * 删除数据 remove 相当于hibernate delete方法
     * remove方法只能移除执久化对象，不能删除游离对象
     */
    public void remove(Account account) {
        if (!transaction.isActive()) {
            transaction.begin();
        }
        entityManager.remove(account);
        transaction.commit();
    }
}
