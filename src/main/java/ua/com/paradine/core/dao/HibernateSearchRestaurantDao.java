package ua.com.paradine.core.dao;


import static java.util.Optional.ofNullable;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.TermTermination;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.paradine.core.business.ViewRestaurantsListCriteria;
import ua.com.paradine.domain.Restaurant;

@Service
public class HibernateSearchRestaurantDao extends RestaurantRelationsBuilder {

    @PersistenceContext
    private EntityManager em;

    @Value("${paradine.page.size:10}")
    private Integer pageSize;

    public HibernateSearchRestaurantDao(ExtendedPopularTimeRepository popularTimeRepository,
        ExtendedWorkingHoursRepository workingHoursRepository) {
        super(popularTimeRepository, workingHoursRepository);
    }

    @Override
    public Page<Restaurant> loadRestaurants(ViewRestaurantsListCriteria searchCriteria) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
        QueryBuilder restaurantQB = fullTextEntityManager
            .getSearchFactory()
            .buildQueryBuilder()
            .forEntity(Restaurant.class)
            .get();


        TermTermination cityTerm = restaurantQB
            .keyword()
            .onFields("city.slug")
            .matching(searchCriteria.getCitySlug());

        TermTermination keywordTerm = restaurantQB
            .keyword()
            .onFields("name", "altName1", "altName2", "altName3")
            .matching(searchCriteria.getQuery());

        org.hibernate.search.jpa.FullTextQuery jpaQuery =
            fullTextEntityManager.createFullTextQuery(cityTerm.createQuery(), Restaurant.class);
        Integer page = ofNullable(searchCriteria.getPage()).orElse(0);
        jpaQuery.setFirstResult(page * pageSize);
        jpaQuery.setMaxResults(pageSize);

        List<Restaurant> resultList = jpaQuery.getResultList();
        super.buildRestaurantRelations(resultList);

        return new PageImpl<>(resultList, PageRequest.of(page, pageSize), jpaQuery.getResultSize());
    }
}
