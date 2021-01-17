package in.auth_oauth2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import in.auth_oauth2.entity.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

}
