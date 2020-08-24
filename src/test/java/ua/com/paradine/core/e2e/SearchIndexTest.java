package ua.com.paradine.core.e2e;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.hibernate.search.batchindexing.impl.SimpleIndexingProgressMonitor;
import org.hibernate.search.jpa.FullTextEntityManager;
import ua.com.paradine.domain.Restaurant;

//TODO this class was added because ua.com.paradine.core.dao.search.HibernateSearchIndexer
// did not index data ingested with @Sql
@Transactional
public class SearchIndexTest {

    @PersistenceContext
    private EntityManager entityManager;

    public void rebuildIndex() {
        FullTextEntityManager fullTextEntityManager =
            org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
        try {
            fullTextEntityManager
                .createIndexer(Restaurant.class)
                .batchSizeToLoadObjects(100)
                .startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
