package main.tree.api.repository;

import main.tree.api.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositoryProduct extends JpaRepository<Node, Long> {

    public Optional<List<Node>> findAllByParentId(Long id);

}
