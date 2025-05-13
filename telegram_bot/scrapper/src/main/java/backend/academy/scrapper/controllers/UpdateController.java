package backend.academy.scrapper.controllers;

import backend.academy.scrapper.service.UpdateMonitoringService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/updates")
public class UpdateController {
    @Autowired
    private UpdateMonitoringService monitoringService;

    private final Map<Long, String> updates = new ConcurrentHashMap<>();

    @PostMapping("/{userId}")
    public ResponseEntity<String> sendUpdate(@PathVariable Long userId, @RequestBody String message) {
        updates.put(userId, message);
        return ResponseEntity.ok("Update sent to user " + userId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> getUpdate(@PathVariable Long userId) {
        String message = monitoringService.monitorUpdates(userId);
        if (message != null) {
            updates.remove(userId);
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.noContent().build();
    }
}
