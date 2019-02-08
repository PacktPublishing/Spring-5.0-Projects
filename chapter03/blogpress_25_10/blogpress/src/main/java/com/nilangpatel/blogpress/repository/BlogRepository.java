package com.nilangpatel.blogpress.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.nilangpatel.blogpress.model.Blog;

@Repository
public interface BlogRepository extends ElasticsearchRepository<Blog, String>,BlogRepositoryCustom{

	public List<Blog> findByTitleAndBody(String title, String body);
}
