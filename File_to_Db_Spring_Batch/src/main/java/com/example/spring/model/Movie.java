package com.example.spring.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Movie {
	
	private String title;
	private long year;
	private String cast;
	private String genre;
	private List<String> casts;
	private List<String> genres;

}
