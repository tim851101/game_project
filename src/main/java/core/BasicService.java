package core;

import jakarta.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class BasicService<R extends JpaRepository<E, Integer>, E, D> implements intrinsicService {
    /**
     * E for Entity
     * D for DTO
     */

    @Resource
    private R repository;
    private final ModelMapper modelMapper;

    @Autowired
    public BasicService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public List<D> getAllDTO() {
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE);
        return repository.findAll()
            .stream()
            .map(this::entityToDTO)
            .collect(Collectors.toList());
    }

    private D entityToDTO(E entity) {
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(entity, (Class<D>) ((ParameterizedType) getClass()
            .getGenericSuperclass())
            .getActualTypeArguments()[2]);
    }
}
