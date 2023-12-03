package rs.ac.uns.ftn.Bookify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import rs.ac.uns.ftn.Bookify.dto.NotificationDTO;
import rs.ac.uns.ftn.Bookify.dto.NotificationSettingsDTO;
import rs.ac.uns.ftn.Bookify.service.interfaces.INotificationService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<Collection<NotificationDTO>> getUserNotifications(@PathVariable Long userId){
        Collection<NotificationDTO> notifications = notificationService.getUserNotification(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{notificationId}")
    public ResponseEntity<String> removeNotification(@PathVariable Long userId, @PathVariable Long notificationId) {
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
    @GetMapping("/{userId}/settings")
    public ResponseEntity<NotificationSettingsDTO> getNotificationSettings(@PathVariable Long userId){
        Optional<NotificationSettingsDTO> settings = Optional.ofNullable(notificationService.getNotificationSettings(userId));
        return settings.map(notificationSettingsDTO -> new ResponseEntity<>(notificationSettingsDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
    @PutMapping("/{userId}/settings")
    public ResponseEntity<NotificationSettingsDTO> updateNotificationSettings(@PathVariable Long userId, @RequestBody NotificationSettingsDTO updatedSettings){
        Optional<NotificationSettingsDTO> settings = Optional.ofNullable(notificationService.updateNotificationSettings(userId, updatedSettings));
        return settings.map(notificationSettingsDTO -> new ResponseEntity<>(notificationSettingsDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }
}