package main.tree.api.resources;

import lombok.extern.slf4j.Slf4j;
import main.tree.api.model.Node;
import main.tree.api.model.UpdateNode;
import main.tree.api.services.ServiceNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.sun.activation.registries.LogSupport.log;


@RestController
@RequestMapping("/tree")
@Slf4j
public class ResourcesNode {
    private final ServiceNode serviceNode;

    public ResourcesNode(ServiceNode serviceNode) {
        this.serviceNode = serviceNode;
    }

    @GetMapping
    public ResponseEntity<List<Node>> findAll(){
        return ResponseEntity.ok(serviceNode.findAll());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Node node){
        Long id = Long.valueOf(1);
        Optional<Node> validate1 = serviceNode.findById(Long.valueOf(node.getParentId()));
        Optional<Node> validate2 = serviceNode.findById(Long.valueOf(1));

        if(!validate2.isPresent()){
            node.setParentId(0);
        }else{
            if (!validate1.isPresent()){
                node.setParentId(id);
            }
        }
        return ResponseEntity.ok(serviceNode.save(node));
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<Optional<List<Node>>> findByParentId(@PathVariable Long parentId) throws IOException {
        Optional<List<Node>> stock = serviceNode.findByParentId(parentId);
        if (!stock.isPresent()){
            log( "ParentId" + parentId + " is not existed");
            return ResponseEntity.badRequest().build();
        }
//        return ResponseEntity.ok(stock.get());
        return ResponseEntity.ok(stock);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Node> findById(@PathVariable Long id){
        Optional<Node> stock = serviceNode.findById(id);
        if (!stock.isPresent()){
            log( "ParentId" + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }


    @PutMapping("/{id}")
    public ResponseEntity<UpdateNode> update(@PathVariable Long id, @Valid @RequestBody UpdateNode node) {
        if (!serviceNode.findById(id).isPresent()){
            String log = "ID" + id + " is not existed";
            return ResponseEntity.badRequest().build();
        }
        node = serviceNode.update(id, node);

        return ResponseEntity.ok(node);
    }

    @PostMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws IOException {
        if (!serviceNode.findById(id).isPresent()){
            log("ID " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        serviceNode.deleteById(id);

        return ResponseEntity.ok().build();
    }

}
