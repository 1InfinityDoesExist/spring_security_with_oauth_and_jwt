package in.auth_oauth2.service.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.auth_oauth2.entity.Product;
import in.auth_oauth2.exception.InvalidInputException;
import in.auth_oauth2.exception.ProductAlreadyExistException;
import in.auth_oauth2.exception.ProductNotFoundException;
import in.auth_oauth2.model.request.ProductCreateRequest;
import in.auth_oauth2.model.request.ProductUpdateRequest;
import in.auth_oauth2.model.response.ProductCreateResponse;
import in.auth_oauth2.model.response.ProductUpdateResponse;
import in.auth_oauth2.repository.ProductRepository;
import in.auth_oauth2.service.ProductService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public ProductCreateResponse createProduct(ProductCreateRequest request) throws Exception {
		Product product = null;
		if (!ObjectUtils.isEmpty(request) && !StringUtils.isEmpty(request.getName())) {
			product = productRepository.findProductByName(request.getName());
			if (!ObjectUtils.isEmpty(product)) {
				throw new ProductAlreadyExistException(String.format("Product %s already exist.", request.getName()));
			} else {
				product = new Product();
				product.setName(request.getName());
				product.setDomain(request.getDomain());
				product.setDescription(request.getDescription());
				product.setActive(true);
				productRepository.save(product);
				ProductCreateResponse response = new ProductCreateResponse();
				response.setId(product.getId());
				response.setName(product.getName());
				response.setMsg("Successfully created.");
				return response;
			}
		} else {
			throw new InvalidInputException("Invalid input.");
		}
	}

	@Override
	public Product retrieveProduct(String id) throws Exception {
		if (!StringUtils.isEmpty(id)) {
			Product product = productRepository.findProductById(id);
			if (!ObjectUtils.isEmpty(product)) {
				return product;
			} else {
				throw new ProductNotFoundException("Product not found. Please create the product first.");
			}
		} else {
			throw new InvalidInputException("Invalid input. Id must not be null or empty");
		}
	}

	@Override
	public List<Product> retrieveAllProducts() {
		List<Product> listOfProduct = productRepository.findAll();
		return listOfProduct;
	}

	@Override
	public void deleteProduct(String id) throws Exception {
		if (!StringUtils.isEmpty(id)) {
			Product product = productRepository.findProductById(id);
			if (!ObjectUtils.isEmpty(product)) {
				productRepository.delete(product);
			} else {
				throw new ProductNotFoundException("Product not found. Please create the product first.");
			}
		} else {
			throw new InvalidInputException("Invalid input. Id must not be null or empty");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductUpdateResponse updateProduct(ProductUpdateRequest request, String id) throws Exception {
		if (!StringUtils.isEmpty(id)) {
			Product product = productRepository.findProductById(id);
			log.info("::::Product {}", product);
			if (!ObjectUtils.isEmpty(product)) {
				JSONObject payloadJsonObject = (JSONObject) new JSONParser()
						.parse(new ObjectMapper().writeValueAsString(request));
				JSONObject dbJsonObject = (JSONObject) new JSONParser()
						.parse(new ObjectMapper().writeValueAsString(product));
				for (Object obj : payloadJsonObject.keySet()) {
					String param = (String) obj;
					dbJsonObject.put(param, payloadJsonObject.get(param));
				}
				productRepository.save(new ObjectMapper().readValue(dbJsonObject.toJSONString(), Product.class));
				ProductUpdateResponse response = new ProductUpdateResponse();
				response.setId(product.getId());
				response.setName(product.getName());
				response.setMsg("Successfully updated");
				return response;
			} else {
				throw new ProductNotFoundException("Product not found. Please create the product first.");
			}
		} else {
			throw new InvalidInputException("Invalid input. Id must not be null or empty");
		}
	}

}
