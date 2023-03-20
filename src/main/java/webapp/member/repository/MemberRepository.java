package webapp.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webapp.member.pojo.Members;

@Repository
public interface MemberRepository extends JpaRepository<Members, Integer> {
    Boolean existsByMemEmailAndMemPassword(String memEmail,String memPassword);


}
