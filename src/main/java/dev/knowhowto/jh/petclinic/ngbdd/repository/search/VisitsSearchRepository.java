package dev.knowhowto.jh.petclinic.ngbdd.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.List;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import dev.knowhowto.jh.petclinic.ngbdd.domain.Visits;
import dev.knowhowto.jh.petclinic.ngbdd.repository.VisitsRepository;
import reactor.core.publisher.Flux;

/**
 * Spring Data Elasticsearch repository for the {@link Visits} entity.
 */
public interface VisitsSearchRepository extends ReactiveElasticsearchRepository<Visits, Long>, VisitsSearchRepositoryInternal {}

interface VisitsSearchRepositoryInternal {
    Flux<Visits> search(String query, Pageable pageable);

    Flux<Visits> search(Query query);
}

class VisitsSearchRepositoryInternalImpl implements VisitsSearchRepositoryInternal {

    private final ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;

    VisitsSearchRepositoryInternalImpl(ReactiveElasticsearchTemplate reactiveElasticsearchTemplate) {
        this.reactiveElasticsearchTemplate = reactiveElasticsearchTemplate;
    }

    @Override
    public Flux<Visits> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        return search(nativeSearchQuery);
    }

    @Override
    public Flux<Visits> search(Query query) {
        return reactiveElasticsearchTemplate.search(query, Visits.class).map(SearchHit::getContent);
    }
}
