package in.auth_oauth2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import in.auth_oauth2.entity.UserInfo;

@Repository
public interface UserInfoRepository extends MongoRepository<UserInfo, String> {

	public UserInfo findByUserName(String userName);

}
