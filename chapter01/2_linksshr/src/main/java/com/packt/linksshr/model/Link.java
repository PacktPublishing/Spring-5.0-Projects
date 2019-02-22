package com.packt.linksshr.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Link {
	String id;
	String url;
	String title;
	String description;
	Integer viewCount = 0;
	Integer upVoteCount = 0;
	Integer downVoteCount = 0;
	public Integer getVoteCount() {
		return upVoteCount - downVoteCount;
	}
	
	String category;
	String postedBy;
	LocalDateTime postedOn;
	LocalDateTime updatedOn;
	Integer repliesCount = 0;
	
}
