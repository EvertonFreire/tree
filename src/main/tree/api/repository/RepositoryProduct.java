package main.tree.api.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import main.tree.api.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties({"hasChildren"})
public interface RepositoryProduct extends JpaRepository<Node, Long> {

    Optional<List<Node>> findAllByParentId(Long id);

}
