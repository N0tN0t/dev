package main.respositories;

import main.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE code = :code",nativeQuery = true)
    Users findByCode(@Param("code") String code);
}
