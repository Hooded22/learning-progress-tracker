package tracker.notifications;

import tracker.courses.Course;
import tracker.students.Student;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotificationsService {
    NotificationsRepository notificationsRepository;


    public NotificationsService(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    public void addCourseCompletedNotification(Course course, Student student, int pointsCount) {
        String subject = NotificationsMessages.GRADUATED_STUDENTS_SUBJECT.getMessage();
        String message = String.format(NotificationsMessages.GRADUATED_STUDENTS_MESSAGE.getMessage(), student.getFullName(), course.getName());

        notificationsRepository.addNotificationsToSend(new Notification(student.getEmail(), subject, message));
    }

    public void sendAllNotifications() {
        List<Notification> notifications = notificationsRepository.getNotificationsToSend();

        for (Notification notification : notifications) {
            System.out.println(notification.getNotification());
        }

        System.out.printf((NotificationsMessages.TOTAL_NOTIFIED_STUDENTS.getMessage()) + "%n", getNumberOfNotifiedUsersMessage(notifications));


        notificationsRepository.setSendNotifications(notifications);
        notificationsRepository.clearNotificationsToSend();
    }

    public int getNumberOfNotifiedUsersMessage(List<Notification> notifications) {
        Set<String> uniqueStudentIds = new HashSet<>();

        for (Notification notification : notifications) {
            uniqueStudentIds.add(notification.getEmail());
        }
        return uniqueStudentIds.size();
    }

}
