package backend.academy.scrapper.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Link> links;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Link> getLinks() {
        return links;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
    }
}
