package backend.academy.scrapper.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "links")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String url;

    @Column(name = "update_time")
    private ZonedDateTime updateTime = ZonedDateTime.now();

    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();

    @ManyToMany
    @JoinTable(
            name = "links_tags",
            joinColumns = @JoinColumn(name = "link_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @ManyToMany
    @JoinTable(
            name = "links_filters",
            joinColumns = @JoinColumn(name = "link_id"),
            inverseJoinColumns = @JoinColumn(name = "filter_id"))
    private Set<Filter> filters;

    public Integer getId() {
        return id;
    }

    public Set<Filter> getFilters() {
        return filters;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public String getUrl() {
        return url;
    }

    public User getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void setFilters(Set<Filter> filters) {
        this.filters = filters;
    }
}
