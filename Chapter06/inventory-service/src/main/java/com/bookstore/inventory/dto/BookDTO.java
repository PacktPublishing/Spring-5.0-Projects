package com.bookstore.inventory.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BookDTO {

	private Integer bookId;
	
	@NotNull(message="Title can not be empty")
	private String title;
	
	@NotNull(message="Small Description can not be empty")
	private String smallDesc;
	
	@NotNull(message="Long Description can not be empty")
	private String longDesc;
	
	@NotNull(message="Category can not be empty")
	private Integer category;
	
	@NotNull(message="Publisher can not be empty")
	private Integer publisherId;
	
	@NotNull(message="Price can not be Zero")
	private Double price;
	
	private String port;
	
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSmallDesc() {
		return smallDesc;
	}
	public void setSmallDesc(String smallDesc) {
		this.smallDesc = smallDesc;
	}
	public String getLongDesc() {
		return longDesc;
	}
	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public Integer getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
}
