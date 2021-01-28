package in.auth_oauth2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import in.auth_oauth2.entity.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

	public Product findProductByName(String name);

	public Product findProductById(String id);

}
