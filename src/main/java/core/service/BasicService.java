package core.service;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Service superclass
 *
 * @param <R> repository
 * @param <E> Entity
 * @param <D> DTO
 */
@Service
public class BasicService<R extends JpaRepository<E, Integer>, E, D> implements intrinsicService<D> {

    protected final R repository;
    private final ModelMapper modelMapper;

    // Spring recommend constructor injection
    public BasicService(ModelMapper modelMapper, R repository) {
        this.modelMapper = modelMapper;
        this.repository = repository;
    }

    /**
     * convert pojo which represent database into DTO
     *
     * @return List<D> D is type of DTO
     */
    public List<D> getAllDTO() {
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE);
        return repository.findAll()
            .stream()
            .map(this::entityToDTO)
            .collect(Collectors.toList());
    }

    /**
     * use ModelMapper for loose mapping
     *
     * @param entity object we want convert to DTO
     * @return D type of DTO
     */
    private D entityToDTO(E entity) {
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE);
        // get runtime generic class type
        return modelMapper.map(entity, (Class<D>) ((ParameterizedType) getClass()
            .getGenericSuperclass())
            .getActualTypeArguments()[2]);
    }
}
