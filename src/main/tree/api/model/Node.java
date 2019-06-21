package main.tree.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@SequenceGenerator(name = "id", initialValue = 1, allocationSize = 5000)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    private long id;

    private long parentId;

    private String code;

    private String detail;

    private String description;

    @OneToMany
    @JsonSerialize
    @Transient
    private Optional<List<Node>> children;

    @Column(nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean hasChildren;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public Optional<List<Node>> getChildren() {
        return children;
    }

    public void setChildren(Optional<List<Node>> children) {
        this.children = children;
    }

    /*
    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        String a="Optional[Node";
        String b= "][";
        String c = "\\(";
        String d = "\\)";
        children = RegExUtils.replaceAll(children, Pattern.quote(a),"\\[");
        children = RegExUtils.replaceAll(children,c,"\\[{");
        children = RegExUtils.replaceAll(children,d,"\\}]");
        children = RegExUtils.replaceAll(children,Pattern.quote(b),",");
        children.replaceAll(a,b);
        System.out.println(children);
        this.children = children;
    } */
}
