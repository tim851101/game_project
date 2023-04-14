package webapp.member.repository;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import webapp.member.pojo.Members;

import java.lang.reflect.Member;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Members, Integer> {
    Boolean existsByMemEmailAndMemPassword(String memEmail,String memPassword);
    Boolean existsByMemEmail(String memEmail);
    Members findByMemEmail(String memEmail);

//    default boolean isValidMember(String email, String password) {
//        Members member = findByMemEmail(email);
//        if (member == null) {
//            return false;
//        }
//
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        return passwordEncoder.matches(password, member.getMemPassword());
//    }


}
