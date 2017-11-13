package crud.backend;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAllBy(Pageable pageable);

    List<Person> findByNameLikeIgnoreCase(String nameFilter);

    //For lazy loading
    List<Person> findByNameLikeIgnoreCase(String nameFilter, Pageable pageable);

    long countByNameLike(String nameFilter);
}
