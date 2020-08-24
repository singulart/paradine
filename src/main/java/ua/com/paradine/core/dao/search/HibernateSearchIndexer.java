package ua.com.paradine.core.dao.search;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.search.batchindexing.impl.SimpleIndexingProgressMonitor;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

public class HibernateSearchIndexer implements ApplicationListener<ApplicationReadyEvent> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        FullTextEntityManager fullTextEntityManager =
            org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
        try {
            fullTextEntityManager
                .createIndexer()
                .batchSizeToLoadObjects(100)
                .threadsToLoadObjects(12)
                .progressMonitor(new SimpleIndexingProgressMonitor())
                .startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
