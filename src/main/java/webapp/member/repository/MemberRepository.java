package webapp.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webapp.member.dto.MemberDTO;
import webapp.member.pojo.Members;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Members, Integer> {
    Boolean existsByMemEmailAndMemPassword(String memEmail,String memPassword);

    Boolean findByMemEmail(String memEmail);
}
