package core.service;

import java.util.List;

public interface IntrinsicService<D> {
    List<D> getAllDTO();
    void insertAllDTO(List<D> dto);
}