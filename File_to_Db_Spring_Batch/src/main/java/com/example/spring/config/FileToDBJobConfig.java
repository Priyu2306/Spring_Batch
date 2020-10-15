package com.example.spring.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.spring.model.Movie;
import com.example.spring.processor.MovieItemProcessor;

@Configuration
@EnableBatchProcessing
public class FileToDBJobConfig {

	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	DataSource datasource;
	
	@Autowired
	BatchConfiguration config;
	
	@Bean
	public JsonItemReader<Movie> itemReader()
	{
		return new JsonItemReaderBuilder<Movie>()
				.jsonObjectReader(new JacksonJsonObjectReader<>(Movie.class))
				.resource(new ClassPathResource("movie.json"))
				.name("jsonItemReader")
				.build();
	}
	
	@Bean
	public MovieItemProcessor itemProcessor()
	{
		return new MovieItemProcessor();
	}
	
	@Bean
	public JdbcBatchItemWriter<Movie> itemWriter()
	{
		return new JdbcBatchItemWriterBuilder<Movie>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO MOVIE (TITLE,YEAR,CAST,GENRE) VALUES (:title,:year,:cast,:genre)")
				.dataSource(datasource)
				.build();
	}
	
	@Bean
	public Step step1()
	{
		return stepBuilderFactory.get("Step1")
				.<Movie,Movie>chunk(config.getChunksize())
				.reader(itemReader())
				.processor(itemProcessor())
				.writer(itemWriter())
				.build();
		
	}
	
	@Bean
	public Job fileToDbJob()
	{
		return jobBuilderFactory.get("fileToDbJob")
				.incrementer(new RunIdIncrementer())
				.flow(step1())
				.end()
				.build();
	}
}
