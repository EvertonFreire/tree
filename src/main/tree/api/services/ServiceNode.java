package main.tree.api.services;

import main.tree.api.model.Node;
import main.tree.api.model.UpdateNode;
import main.tree.api.repository.RepositoryProduct;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
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
        updateHasChildren(stock);
        return repositoryProduct.save(stock);
    }

    public UpdateNode update(Long id, UpdateNode node){
        Node updateNode = repositoryProduct.getOne(id);
        updateNode.setCode(node.getCode());
        updateNode.setDescription(node.getDescription());
        updateNode.setDetail(node.getDetail());
        updateNode.setParentId(node.getParentId());// Porque é preciso retornar o getParentId.
        repositoryProduct.save(updateNode);
     return node;
    }


    public void deleteById(Long id){
        Node stock = repositoryProduct.getOne(id);
        updateHasChildren(stock);
        repositoryProduct.deleteById(id);
    }

    // Revisar pois tem que trazer os filhos
    public Optional<List<Node>> findByParentId(Long parentId) {

        return repositoryProduct.findAllByParentId(parentId);
    }



    public void updateHasChildren(@NotNull Node stock){
        Optional<Node> fatherNodeIsPresent = findById(stock.getParentId());
        Node fatherNodeUpdateHasChildren = repositoryProduct.getOne(stock.getParentId());
        Optional<Node> rootNotPresent = findById(Long.valueOf(1));
        if(stock.getParentId() != Long.valueOf(0)){
            if(stock.getParentId()==1 && !fatherNodeIsPresent.isPresent()){
                if(!rootNotPresent.isPresent()){
                    stock.setParentId(Long.valueOf(0));
                }else{
                   // aplicar aqui também o pai tem mais de um filho ou não
                }
            }
            //Verifica se nó pai é diferente de 0 e se sim entra
            if (fatherNodeIsPresent.isPresent() && stock.getParentId()!=Long.valueOf(0)){
                //Verifica se
                // Pai é presente e se pai é diferente de raiz
                Optional<List<Node>> checkUniqueSon = findByParentId(stock.getParentId());
                HashMap<Integer,Node> listHashMaps = new HashMap<Integer, Node>();
                int oo = listHashMaps.size();
                System.out.println(oo);
                fatherNodeUpdateHasChildren.setHasChildren(true);
                stock.setHasChildren(false);
            }else{
                fatherNodeUpdateHasChildren.setHasChildren(false);
            }
        }else{
            stock.setHasChildren(false);
        }
    }
}
