package com.packt.linksshr.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Comment {

	private String id;
	private String linkId;
	private String content;
	private String postedBy;
	private LocalDateTime postedOn;
	private String parentId;
	private LocalDateTime updatedOn;
	
}
