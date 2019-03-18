package com.nilangpatel.blogpress.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nilangpatel.blogpress.util.BlogpressCommentComparator;
import com.nilangpatel.blogpress.util.BlogpressUtil;

@Document(indexName = "blog", type = "blog")
public class Blog {

	@Id
	private String _id;
	private String title;
	private String body;
	private String status;
	private String createdBy;
	
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy'T'HH:mm:ss")
	private Date createdDate;

	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy'T'HH:mm:ss")
	private Date publishDate;
	
	@Field(includeInParent=true, type = FieldType.Nested)
	private List<Comment> comments;
		
	/**
	 * @return the id
	 */
	public String getId() {
		return _id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this._id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}
	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the publishDate
	 */
	public Date getPublishDate() {
		return publishDate;
	}
	/**
	 * @param publishDate the publishDate to set
	 */
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	/**
	 * @return the comments
	 */
	public List<Comment> getComments() {
		if(comments !=null && !comments.isEmpty()) {
			Collections.sort(comments, new BlogpressCommentComparator());
		}
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public String getPublishDateForDisplay() {
		String returnDateStr="";
		if(this.getCreatedDate() !=null) {
			returnDateStr = BlogpressUtil.getFormattedDateForDisplayOnPage(createdDate);
		}
		return returnDateStr;
	}
	
	public int getCommentSize() {
		if(this.comments !=null) {
			return this.comments.size();
		}else {
			return 0;
		}
	}
	
	@Override
    public String toString() {
        return "blog {" +
                "\"title\":" + title+ "\"" +
                "\"body\":" + body+ "\"" +
                "\"status\":" + status+ "\"" +
                "\"createdBy\":" + createdBy+ "\"" +
                "\"createdDate\":" + BlogpressUtil.getFormattedDateForElasticSearch(createdDate)+ "\"" +
                "\"publishDate\":" + BlogpressUtil.getFormattedDateForElasticSearch(publishDate)+ "\"" +
                "\"comments\":"+getComments()+
                "})";
    }
	
}
