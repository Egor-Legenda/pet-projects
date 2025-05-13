package backend.academy.scrapper.service.github;

import java.time.ZonedDateTime;

public class ResourceData {
    private ZonedDateTime lastUpdateTime;
    private ZonedDateTime createTime;

    public ResourceData(ZonedDateTime lastUpdateTime, ZonedDateTime createTime) {
        this.lastUpdateTime = lastUpdateTime;
        this.createTime = createTime;
    }

    public void setLastUpdateTime(ZonedDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }
}
