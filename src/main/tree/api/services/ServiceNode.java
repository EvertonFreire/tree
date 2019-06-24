package main.tree.api.services;

import main.tree.api.model.Node;
import main.tree.api.model.PostNode;
import main.tree.api.model.UpdateNode;
import main.tree.api.repository.RepositoryProduct;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceNode {

    private final RepositoryProduct repositoryProduct;

    public ServiceNode(RepositoryProduct repositoryProduct) {
        this.repositoryProduct = repositoryProduct;
    }

    public Optional<Node> findAll() throws IOException {
        concatChildrens(repositoryProduct.getOne(Long.valueOf(1)));
        return repositoryProduct.findById(Long.valueOf(1));
    }

    public Optional<Node> findById(Long id){
        return repositoryProduct.findById(id);
    }

    public PostNode save(Node stock) {
        updateHasChildrenPost(stock);
        repositoryProduct.save(stock);
        PostNode postNode = new PostNode();
        postNode.setId(stock.getId());
        return postNode;
    }

    public PostNode update(Node node) {
        Node updateNode = repositoryProduct.getOne(node.getId());
        updateNode.setCode(node.getCode());
        updateNode.setDescription(node.getDescription());
        updateNode.setDetail(node.getDetail());
        Optional<Node> getFather = repositoryProduct.findById(node.getParentId());
        if (getFather.isPresent())
            updateNode.setParentId(node.getParentId());
        else
            updateNode.setParentId(Long.valueOf(1));
        repositoryProduct.save(updateNode);
        PostNode postNode = new PostNode();
        postNode.setId(node.getId());
        return postNode;
    }

    public UpdateNode update(Long id, UpdateNode node){
        Node updateNode = repositoryProduct.getOne(id);
        updateNode.setCode(node.getCode());
        updateNode.setDescription(node.getDescription());
        updateNode.setDetail(node.getDetail());
        updateNode.setParentId(node.getParentId());
        repositoryProduct.save(updateNode);
     return node;
    }


    public void deleteById(Long id) throws IOException {
        Node stock = repositoryProduct.getOne(id);
        updateHasChildrenDelete(stock);
        repositoryProduct.deleteById(id);
    }

    public Optional<List<Node>> findByParentId(Long parentId) throws IOException {
        Node stock = repositoryProduct.getOne(parentId);
        return repositoryProduct.findAllByParentId(stock.getId());
    }

    public void concatChildrens(@NotNull Node stock) throws IOException {
        if (stock.getHasChildren()==true){
            Optional<List<Node>> childrens = repositoryProduct.findAllByParentId(stock.getId());
            for( int i=0; i< childrens.get().size();i++){
                Optional<Node> nodes = findById(childrens.get().get(i).getId());
                if(nodes.get().getHasChildren()==true){
                    concatChildrens(repositoryProduct.getOne(nodes.get().getId()));
                }else{
                    nodes.get().setChildren(repositoryProduct.findAllByParentId(nodes.get().getId()));
                    nodes.get().setHasChildren(null);
                }
            }
        }else{
            stock.setChildren(repositoryProduct.findAllByParentId(stock.getId()));
            stock.setHasChildren(null);
        }
        stock.setChildren(repositoryProduct.findAllByParentId(stock.getId()));
        stock.setHasChildren(null);
    }

    public void updateHasChildrenPost(@NotNull Node stock){
        Node fatherNodeUpdateHasChildren = repositoryProduct.getOne(stock.getParentId());
        Optional<Node> father= findById(Long.valueOf(stock.getParentId()));
        if (father.isPresent())
             fatherNodeUpdateHasChildren.setHasChildren(true);//Isso ta dando pau.
        stock.setHasChildren(false);
    }

    public void updateHasChildrenDelete(@NotNull Node stock) throws IOException {
        Node fatherNodeUpdateHasChildren = repositoryProduct.getOne(stock.getParentId());
        Optional<List<Node>> father = findByParentId(Long.valueOf(stock.getParentId()));
        int fatherSize = father.get().size();

        if(fatherNodeUpdateHasChildren.getParentId()!=0){
            if(fatherSize>1){

            }else{
                fatherNodeUpdateHasChildren.setHasChildren(false);
            }
        }else{ }

        if(stock.getHasChildren()==true){
            deleteSubNodes(stock);
        }
}

    public void deleteSubNodes(Node stock) throws IOException {
        if (stock.getHasChildren()==true){
            Optional<List<Node>> cleanNode = repositoryProduct.findAllByParentId(Long.valueOf(stock.getId()));
            for (int i=0; i <cleanNode.get().size(); i++) {
                Optional<Node> nodes = findById(cleanNode.get().get(i).getId());
                if (nodes.get().getHasChildren() == true){
                    deleteSubNodes(repositoryProduct.getOne(nodes.get().getId()));
                    deleteById(nodes.get().getId());
                }else{
                    deleteById(nodes.get().getId());
                }
            }
        }
    }
}
