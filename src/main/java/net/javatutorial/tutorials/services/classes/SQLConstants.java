package net.javatutorial.tutorials.services.classes;

public class SQLConstants {

    public static final String REGISTER_USER = "INSERT INTO users (username, password, realname, email) values (?, ?, ?, ?)";

    public static final String IS_USER_REGISTERED = "SELECT COUNT(*) as rowcount FROM users where username = ? and password = ?";

    public static final String IS_USERNAME_AVAILABLE = "SELECT COUNT(*) as rowcount FROM users where username = ?";

    public static final String IS_USERNAME_ADMIN = "SELECT COUNT(*) as rowcount FROM users where username = ? and isAdmin = 1";

    public static final String GET_STUDENT = "SELECT * FROM users WHERE username = ?";

    public static final String ADD_COURSE = "INSERT INTO courses (courseid, coursename, credits) values (?, ?, ?)";

    public static final String APPLY_FOR_COURSE = "INSERT INTO studentcourses (studentUsername, courseid, status) values (?, ?, 'pending')";

    public static final String ACCEPT_STUDENT_TO_COURSE = "UPDATE studentcourses set status='accepted' where studentUsername = ? and courseid = ?";

    public static final String DENY_STUDENT_TO_COURSE = "UPDATE studentcourses set status='rejected' where studentUsername = ? and courseid = ?";

    public static final String SET_STUDENT_GRADE_FOR_COURSE = "UPDATE studentcourses set grade = ? where studentUsername = ? and courseid = ?";

    public static final String PENDING_STUDENTS = "SELECT * FROM studentcourses where status = 'pending'";

    public static final String ACCEPTED_STUDENTS = "SELECT * FROM studentcourses where status = 'accepted'";

    public static final String STUDENTS_ON_COURSE = "SELECT * FROM studentcourses where and courseid = ? and status='accepted'";

    public static final String COURSES = "SELECT * FROM courses";

    public static final String GET_COURSE = "SELECT * FROM courses where courseid = ?";

    public static final String GET_COURSES_FOR_STUDENT = "SELECT * FROM studentcourses where studentUsername = ?";

    public static final String IS_CANDIDATE_ON_COURSE = "SELECT * from studentcourses where studentUsername = ? and courseid = ?";
}
