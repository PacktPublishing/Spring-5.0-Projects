package com.nilangpatel.blogpress.repository;

import java.util.List;

import com.nilangpatel.blogpress.model.Comment;

public interface BlogRepositoryCustom {

	List<Comment> getAllComments(int from, int size);
	
	List<Comment> getCommentsForStatus(String status,int from, int size);
	
	int getCurrentChildSequence(String blogId, String parentCommentId);
	
}
