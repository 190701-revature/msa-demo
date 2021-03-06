package com.revature.dto;

import java.time.LocalDate;

/**
 * DTO - Data Transfer Object - Represents a class definition that represents a model like definition
 * put that is not intended for persistance, and is rather used to communicate details between
 * application.
 *
 */
public class Book {
	private int id;
	private String name;
	private LocalDate releaseDate;
	private int pageCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + pageCount;
		result = prime * result + ((releaseDate == null) ? 0 : releaseDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pageCount != other.pageCount)
			return false;
		if (releaseDate == null) {
			if (other.releaseDate != null)
				return false;
		} else if (!releaseDate.equals(other.releaseDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", releaseDate=" + releaseDate + ", pageCount=" + pageCount + "]";
	}

	public Book(int id, String name, LocalDate releaseDate, int pageCount) {
		super();
		this.id = id;
		this.name = name;
		this.releaseDate = releaseDate;
		this.pageCount = pageCount;
	}

	public Book() {
		super();
	}

}
