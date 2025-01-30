package com.messenger_backend.repo;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import com.messenger_backend.model.UserSearchEntity;

@Repository
public interface UserSearchRepository extends ElasticsearchRepository<UserSearchEntity, Long>{


}
