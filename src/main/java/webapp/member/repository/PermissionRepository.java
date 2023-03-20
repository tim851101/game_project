package webapp.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.member.pojo.MemberPermission;

@Repository
public interface PermissionRepository extends JpaRepository<MemberPermission, Integer> {

    @Query("SELECT mp.duration " +
        "FROM MemberPermission mp " +
        "WHERE :times BETWEEN mp.lowerLimit AND mp.upperLimit")
    Integer findDurationByTimes(@Param("times") Integer times);
    // find duration by violation times
}
