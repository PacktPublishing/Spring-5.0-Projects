package com.bookstore.inventory.model;

//@Entity
//@Table(name="inventory",catalog="ob_inventory")
public class Inventory {

	/*@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")*/
	private Integer Id;
	
	//@Column(name="book_id")
	private Integer bookId;
	
	//@Column(name="instock")
	private Integer instock;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getInstock() {
		return instock;
	}

	public void setInstock(Integer instock) {
		this.instock = instock;
	}
	
}
