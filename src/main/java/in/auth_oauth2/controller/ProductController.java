package in.auth_oauth2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.auth_oauth2.entity.Product;
import in.auth_oauth2.exception.InvalidInputException;
import in.auth_oauth2.exception.ProductAlreadyExistException;
import in.auth_oauth2.exception.ProductNotFoundException;
import in.auth_oauth2.model.request.ProductCreateRequest;
import in.auth_oauth2.model.request.ProductUpdateRequest;
import in.auth_oauth2.model.response.ProductCreateResponse;
import in.auth_oauth2.model.response.ProductUpdateResponse;
import in.auth_oauth2.service.ProductService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping("/persist")
	public ResponseEntity<?> persistProductInDB(@RequestBody ProductCreateRequest productCreateRequest)
			throws Exception {
		log.info("::::::::ProductController Class, persistProductInDB method::::::");
		try {
			ProductCreateResponse response = productService.createProduct(productCreateRequest);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ModelMap().addAttribute("response", response));
		} catch (final ProductAlreadyExistException | InvalidInputException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ModelMap().addAttribute("error_msg", ex.getMessage()));
		}
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<ModelMap> retrieveProductById(@PathVariable(value = "id", required = true) String id)
			throws Exception {
		log.info("::::::ProductController Class, retrieveProductById method::::::");
		try {
			Product product = productService.retrieveProduct(id);
			return ResponseEntity.status(HttpStatus.OK).body(new ModelMap().addAttribute("response", product));
		} catch (final ProductNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.OK).body(new ModelMap().addAttribute("error_msg", ex.getMessage()));
		} catch (final InvalidInputException ex) {
			return ResponseEntity.status(HttpStatus.OK).body(new ModelMap().addAttribute("error_msg", ex.getMessage()));
		}
	}

	@GetMapping("/getAll")
	public ResponseEntity<ModelMap> retrieveAllProductDetails() {
		List<Product> listOfProduct = productService.retrieveAllProducts();
		return ResponseEntity.status(HttpStatus.OK).body(new ModelMap().addAttribute("response", listOfProduct));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ModelMap> deleteProductById(@PathVariable(value = "id", required = true) String id)
			throws Exception {
		log.info("::::::ProductController Class, deleteProductById method::::::");
		try {
			productService.deleteProduct(id);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ModelMap().addAttribute("response", "Successfully deleted"));
		} catch (final ProductNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.OK).body(new ModelMap().addAttribute("error_msg", ex.getMessage()));
		} catch (final InvalidInputException ex) {
			return ResponseEntity.status(HttpStatus.OK).body(new ModelMap().addAttribute("error_msg", ex.getMessage()));
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ModelMap> updateProductById(@RequestBody ProductUpdateRequest request,
			@PathVariable(value = "id", required = true) String id) throws Exception {
		log.info("::::::ProductController Class, updateProductById method::::::");
		try {
			ProductUpdateResponse updateProduct = productService.updateProduct(request, id);
			return ResponseEntity.status(HttpStatus.OK).body(new ModelMap().addAttribute("response", updateProduct));
		} catch (final ProductNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.OK).body(new ModelMap().addAttribute("error_msg", ex.getMessage()));
		} catch (final InvalidInputException ex) {
			return ResponseEntity.status(HttpStatus.OK).body(new ModelMap().addAttribute("error_msg", ex.getMessage()));
		}
	}

}
