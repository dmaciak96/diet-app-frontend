package pl.daveproject.frontendservice.ui.component;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import pl.daveproject.frontendservice.ui.component.type.WebdietNotificationType;

public class WebdietNotification {
    private static final int NOTIFICATION_DISPLAY_DURATION_MS = 3000;

    public static void show(String message, WebdietNotificationType type) {
        var notification = Notification.show(message, NOTIFICATION_DISPLAY_DURATION_MS, Notification.Position.BOTTOM_END);
        switch (type) {
            case ERROR -> notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            case INFO -> notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            case WARNING -> notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
        }
    }
}
