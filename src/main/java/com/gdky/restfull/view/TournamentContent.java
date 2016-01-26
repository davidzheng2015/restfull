package com.gdky.restfull.view;

import java.util.Date;

public class TournamentContent {
	private String name;
	private Integer id;
	private String link;
	private String author;
	public Date publicationDate;
	private static int idCounter = 0;  

	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public Date getPublicationDate() {
		return publicationDate;
	}


	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}


	public  static TournamentContent  generateContent(String author,Date date,
			String name,String link) {
		TournamentContent content = new TournamentContent();  
        content.author = author;  
        content.publicationDate = date;  
        content.name = name;  
        content.link = link;  
        content.id = idCounter++;  
  
        return content;  
		
	};
}
