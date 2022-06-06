package com.axians.virtuallibrary.api.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.axians.virtuallibrary.api.model.dto.BookDTO;
import com.axians.virtuallibrary.commons.utils.enums.StatusBookEnum;

@Repository
public class BookRepositoryCustomImpl{

	@PersistenceContext
	private EntityManager entityManager;
	
	public List<BookDTO> listBooksGrouped(){
		String sql = "SELECT count(id) AS inventory, category, title, status, available  FROM book "
				+ "WHERE status != 'DISABLED' AND deleted = 0 "
				+ "GROUP BY title, category, status ORDER BY status ASC";
		
		Query<?> hibernateQuery = entityManager.createNativeQuery(sql).unwrap(Query.class);
		hibernateQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return convertToListDTO(Optional.ofNullable(hibernateQuery.list()));
	}
	
	private List<BookDTO> convertToListDTO(Optional<List<?>> sqlResult) {
		List<BookDTO> bookList = new ArrayList<>();

		sqlResult.ifPresent(resultList -> {
			for (Object result : resultList) {
				Map<?, ?> obj = (Map<?, ?>) result;
				BookDTO book = new BookDTO();
				BigInteger invetory = (BigInteger) obj.get("inventory");
				book.setInventory(invetory.intValue());
				book.setCategory((String) obj.get("category"));
				book.setTitle((String) obj.get("title"));

				String status = (String) obj.get("status");
				book.setStatus(StatusBookEnum.getStatusBookEnum(status));

				Byte available = (Byte) obj.get("available");
				if (available != null) {
					book.setAvailable(available == 0 ? false : true);
				}
				bookList.add(book);
			}
		});
		return bookList;
	}
}
