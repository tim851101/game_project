package webapp.member.repository;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import webapp.member.pojo.Members;
import webapp.member.dto.MemberDTO;
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

    @Query("SELECT new webapp.member.dto.MemberDTO(m.memNo, m.memName, m.memGender, m.memEmail, m.memPassword, m.memPhone, m.memAddress, m.memBirthday, m.coupon, m.reserveAuth, m.memVIO, m.memStatus) FROM Members m")
    List<MemberDTO> findAllDto();

    @Transactional
    @Modifying
    @Query("UPDATE Members m SET m.reserveAuth = :reserveAuth WHERE m.memNo = :memNo")
    int updateReserveAuth(@Param("memNo") Integer memNo, @Param("reserveAuth") Boolean reserveAuth);

}
