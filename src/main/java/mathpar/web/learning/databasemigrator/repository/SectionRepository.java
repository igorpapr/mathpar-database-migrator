package mathpar.web.learning.databasemigrator.repository;

import mathpar.web.learning.databasemigrator.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {
}
