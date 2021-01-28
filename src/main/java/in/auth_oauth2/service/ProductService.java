package in.auth_oauth2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.auth_oauth2.entity.Product;
import in.auth_oauth2.model.request.ProductCreateRequest;
import in.auth_oauth2.model.request.ProductUpdateRequest;
import in.auth_oauth2.model.response.ProductCreateResponse;
import in.auth_oauth2.model.response.ProductUpdateResponse;

@Service
public interface ProductService {

	public ProductCreateResponse createProduct(ProductCreateRequest request) throws Exception;

	public Product retrieveProduct(String id) throws Exception;

	public List<Product> retrieveAllProducts();

	public void deleteProduct(String id) throws Exception;

	public ProductUpdateResponse updateProduct(ProductUpdateRequest request, String id) throws Exception;

}
