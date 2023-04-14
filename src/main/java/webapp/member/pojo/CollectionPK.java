package webapp.member.pojo;

import lombok.Data;
import java.io.Serializable;


@Data
public class CollectionPK implements Serializable {

    public Integer memNo;

    public Integer pdNo;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        CollectionPK that = (CollectionPK) o;
//        return Objects.equals(memNo, that.memNo) && Objects.equals(pdNo, that.pdNo);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(memNo, pdNo);
//    }
}
