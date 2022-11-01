package AmilaAdhikari.StudentRegistrationbackend.repository;

import AmilaAdhikari.StudentRegistrationbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long > {
}
