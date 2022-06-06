package com.axians.virtuallibrary.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.axians.virtuallibrary.api.model.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

	public List<Book> findByTitleAndCategory(String title, String category);
	
	@Query(value = "SELECT COUNT(id) FROM book WHERE UPPER(title) = UPPER(:title) AND category =:category AND status != 'DISABLED'", 
			nativeQuery = true)
	public Integer findInventoryByTitleAndCategory(String title, String category);
	
	public Book findByResourceHyperIdentifier(String resourceHyperIdentifier);
}
