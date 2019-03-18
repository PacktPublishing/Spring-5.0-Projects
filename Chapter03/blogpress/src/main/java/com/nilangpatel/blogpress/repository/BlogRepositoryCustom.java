package com.nilangpatel.blogpress.repository;

import java.util.List;

import com.nilangpatel.blogpress.model.Blog;
import com.nilangpatel.blogpress.model.Comment;

public interface BlogRepositoryCustom {

	List<Comment> getAllComments(int from, int size);
	
	List<Comment> getCommentsForStatus(String status,int from, int size);
	
	int getCurrentChildSequence(String blogId, String parentCommentId);
	
	List<Blog> search(String searchTxt);
	
}
