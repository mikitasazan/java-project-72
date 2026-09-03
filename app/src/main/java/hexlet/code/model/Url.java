package hexlet.code.model;

import java.time.LocalDateTime;

public class Url {
    private Long id;
    private String name;
    private LocalDateTime createdAt;

    // Not persisted on this entity — filled in by the /urls list handler
    // from the latest row in url_checks, for display only.
    private Integer lastCheckStatusCode;
    private LocalDateTime lastCheckCreatedAt;

    public Url() {
    }

    public Url(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getLastCheckStatusCode() {
        return lastCheckStatusCode;
    }

    public void setLastCheckStatusCode(Integer lastCheckStatusCode) {
        this.lastCheckStatusCode = lastCheckStatusCode;
    }

    public LocalDateTime getLastCheckCreatedAt() {
        return lastCheckCreatedAt;
    }

    public void setLastCheckCreatedAt(LocalDateTime lastCheckCreatedAt) {
        this.lastCheckCreatedAt = lastCheckCreatedAt;
    }
}
