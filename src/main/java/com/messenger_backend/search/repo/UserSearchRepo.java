package com.messenger_backend.search.repo;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.messenger_backend.model.UserSearchEntity;

@Repository
public interface UserSearchRepo extends ElasticsearchRepository<UserSearchEntity, Long> {

	//@Query("{ \"fuzzy\": { \"name\": { \"value\": \"?0\" } } }")
	//@Query("{ \"match\": { \"name\": { \"query\": \"?0\", \"fuzziness\": 2, \"prefix_length\": 1 } } }")
    @Query("""
            {
              "bool": {
                "should": [
                  {
                    "multi_match": {
                      "query": "?0",
                      "fields": ["name", "email"],
                      "type": "bool_prefix",
                      "operator": "or"
                    }
                  },
                  {
                    "multi_match": {
                      "query": "?0",
                      "fields": ["name", "email"],
                      "fuzziness": "AUTO",
                      "prefix_length": 1
                    }
                  }
                ]
              }
            }
        """)
	List<UserSearchEntity> fuzzySearchByNameorEmail(String name);

	//List<UserSearchEntity> findByNameStartingWith(String prefix);
}
