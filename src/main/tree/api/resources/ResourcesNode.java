package main.tree.api.resources;

import main.tree.api.model.Node;
import main.tree.api.services.ServiceNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return ResponseEntity.ok(serviceNode.save(node));
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<Node>> findByParentId(@PathVariable Long parentId){
        Optional<List<Node>> stock = serviceNode.findByParentId(parentId);
        if (!stock.isPresent()){
            log( "ParentId" + parentId + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
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
    //Verificar porque não está atualizando o nó
    @PutMapping
    public ResponseEntity<Node> update(@PathVariable Long id, @Valid @RequestBody Node node){
        if (!serviceNode.findById(id).isPresent()){
            log("ID" +id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(serviceNode.save(node));
    }

    @PostMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        if (!serviceNode.findById(id).isPresent()){
            log("ID " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        serviceNode.deleteById(id);

        return ResponseEntity.ok().build();
    }

}
