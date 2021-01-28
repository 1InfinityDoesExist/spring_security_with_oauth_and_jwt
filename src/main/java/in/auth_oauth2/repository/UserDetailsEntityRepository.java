package in.auth_oauth2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import in.auth_oauth2.entity.UserDetailsEntity;

@Repository
public interface UserDetailsEntityRepository extends MongoRepository<UserDetailsEntity, String> {

	public UserDetailsEntity findUserDetailsEntityById(String id);

	public UserDetailsEntity findUserDetailsEntityByUsername(String username);

}
