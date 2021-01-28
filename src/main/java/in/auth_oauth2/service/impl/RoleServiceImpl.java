package in.auth_oauth2.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import in.auth_oauth2.entity.Product;
import in.auth_oauth2.entity.Role;
import in.auth_oauth2.exception.InvalidInputException;
import in.auth_oauth2.exception.ProductNotFoundException;
import in.auth_oauth2.exception.RoleAlreadyExistException;
import in.auth_oauth2.model.request.RoleCreateRequest;
import in.auth_oauth2.model.response.RoleCreateResponse;
import in.auth_oauth2.repository.ProductRepository;
import in.auth_oauth2.repository.RoleRepository;
import in.auth_oauth2.service.RoleService;
import jdk.internal.jline.internal.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

	private final RoleRepository userRoleRepository;
	private final ProductRepository productRepository;

	public RoleServiceImpl(RoleRepository userRoleRepository, ProductRepository productRepository) {
		this.userRoleRepository = userRoleRepository;
		this.productRepository = productRepository;
	}

	@Override
	public RoleCreateResponse presistRole(RoleCreateRequest request) throws Exception {
		if (!ObjectUtils.isEmpty(request) && !StringUtils.isEmpty(request.getProductId())) {
			Product product = productRepository.findProductById(request.getProductId());
			if (!ObjectUtils.isEmpty(product)) {
				log.info(":::::ProductId {}, RoleName {}", product.getId(), request.getName());
				Role roleFromDB = userRoleRepository.findRoleByProductIdAndName(product.getId(), request.getName());
				log.info(":::::Role From DB {}", roleFromDB);
				if (!ObjectUtils.isEmpty(roleFromDB)) {
					throw new RoleAlreadyExistException("Role already exist.");
				}
				Role role = new Role();
				role.setActive(true);
				role.setDescription(request.getDescription());
				role.setName(request.getName());
				role.setProductId(request.getProductId());
				userRoleRepository.save(role);
				RoleCreateResponse response = new RoleCreateResponse();
				response.setId(role.getId());
				response.setMsg("Successfully created userRole");
				response.setName(role.getName());
				return response;
			} else {
				throw new ProductNotFoundException("Product not found. Please create a product first");
			}
		} else {
			throw new InvalidInputException("Invalid input.");
		}
	}

}
