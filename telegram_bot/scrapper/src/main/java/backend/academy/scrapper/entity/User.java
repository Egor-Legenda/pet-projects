package backend.academy.scrapper.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "chat_id", unique = true, nullable = false)
    private Long chatId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Link> links;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Integer getId() {
        return id;
    }

    public Long getChatId() {
        return chatId;
    }

    public List<Link> getLinks() {
        return links;
    }
}
