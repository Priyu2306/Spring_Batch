package com.example.spring.processor;


import org.springframework.batch.item.ItemProcessor;

import com.example.spring.model.Movie;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MovieItemProcessor implements ItemProcessor<Movie, Movie>{

	@Override
	public Movie process(Movie item) throws Exception {
		Movie movie= new Movie();
		String cast="";
		String genre="";
		for(int i=0;i<item.getCasts().size();i++)
		{
			cast=cast.concat(item.getCasts().get(i));
		}
		genre=item.getGenres().toString();
				
		movie.setTitle(item.getTitle());
		movie.setYear(item.getYear());
		movie.setCast(cast);
		movie.setGenre(genre);
		log.info("Cast -----" +cast+" Genre -----"+genre);
		return movie;
	}


}
