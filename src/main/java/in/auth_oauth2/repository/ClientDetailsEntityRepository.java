package in.auth_oauth2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import in.auth_oauth2.entity.ClientDetailsEntity;

@Repository
public interface ClientDetailsEntityRepository extends MongoRepository<ClientDetailsEntity, String> {

	public ClientDetailsEntity findClientDetailsEntityById(String id);

	public ClientDetailsEntity findClientDetailsEntityByClientId(String clientId);

}
