package main.tree.api.services;

import main.tree.api.model.Node;
import main.tree.api.repository.RepositoryProduct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class ServiceNode {

    private final RepositoryProduct repositoryProduct;

    public ServiceNode(RepositoryProduct repositoryProduct) {
        this.repositoryProduct = repositoryProduct;
    }

    public List<Node> findAll(){
        return repositoryProduct.findAll();
    }

    public Optional<Node> findById(Long id){
        return repositoryProduct.findById(id);
    }

    public Node save(Node stock){
        return repositoryProduct.save(stock);
    }

    public void deleteById(Long id){
        repositoryProduct.deleteById(id);
    }

    // Revisar pois tem que trazer os filhos
    public Optional<List<Node>> findByParentId(Long parentId) {
        return repositoryProduct.findAllByParentId(parentId);
    }
}
