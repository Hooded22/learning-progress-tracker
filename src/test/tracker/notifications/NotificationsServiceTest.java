package test.tracker.notifications;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import tracker.courses.Course;
import tracker.notifications.Notification;
import tracker.notifications.NotificationsRepository;
import tracker.notifications.NotificationsService;
import tracker.students.Student;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

class NotificationsServiceTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private NotificationsService notificationsService;
    private NotificationsRepository notificationsRepository;

    @BeforeEach
    void setUp() {
        notificationsRepository = Mockito.mock(NotificationsRepository.class);
        notificationsService = new NotificationsService(notificationsRepository);

        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void addCourseCompletedNotification_shouldCallNotificationRepository() {
        Course course = new Course("Test course");
        Student student = new Student("Adam", "Nowak", "adam.nowak@gmail.com");
        ArgumentCaptor<Notification> notificationArgumentCaptor = ArgumentCaptor.forClass(Notification.class);

        notificationsService.addCourseCompletedNotification(course, student, 12);

        Mockito.verify(notificationsRepository).addNotificationsToSend(notificationArgumentCaptor.capture());
        Notification notification = notificationArgumentCaptor.getValue();


        Assertions.assertEquals(
                "To: adam.nowak@gmail.com\nRe: Your Learning Progress\nHello, Adam Nowak! You have accomplished our Test course course!",
                notification.getNotification()
        );

    }

    @Test
    public void sendAllNotifications_shouldPrintAllNotifications() {
        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(new Notification("test@test.com", "Test subject", "Test message"));
        Mockito.when(notificationsRepository.getNotificationsToSend()).thenReturn(notificationList);

        notificationsService.sendAllNotifications();

        Assertions.assertEquals("To: test@test.com\nRe: Test subject\nTest message\nTotal 1 students have been notified.", outContent.toString().trim());
    }

    @Test
    public void sendAllNotifications_clearNotificationsToSend() {
        List<Notification> notificationList = new ArrayList<>();
        Mockito.when(notificationsRepository.getNotificationsToSend()).thenReturn(notificationList);

        notificationsService.sendAllNotifications();

        Mockito.verify(notificationsRepository).clearNotificationsToSend();
        Mockito.verify(notificationsRepository).setSendNotifications(ArgumentMatchers.eq(notificationList));
    }

}