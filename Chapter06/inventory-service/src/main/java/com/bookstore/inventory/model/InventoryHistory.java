package com.bookstore.inventory.model;

import java.util.Date;

//@Entity
//@Table(name="inventory_history",catalog="ob_inventory")
public class InventoryHistory {

	//@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@Column(name="id")
	private Integer id;
	
	//@Column(name="book_id")
	private Integer bookId;
	
	//@Column(name="inward")
	private Integer inward;
	
	//@Column(name="addedOn")
	private Date addedOn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getInward() {
		return inward;
	}

	public void setInward(Integer inward) {
		this.inward = inward;
	}

	public Date getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}
	
}
