package net.javatutorial.tutorials.services.services;

import net.javatutorial.tutorials.services.classes.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {

    private DatabaseTest db = new DatabaseTest();

    private static DatabaseService single_instance = null;

    public static DatabaseService getInstance(){

        if (single_instance == null)
            single_instance = new DatabaseService();

        return single_instance;

    }

    public boolean registerUser(String username, String password, String realname, String email) throws SQLException {
        String prs = SQLConstants.REGISTER_USER;
        Connection con = db.connect();
        PreparedStatement ps = con.prepareStatement(prs);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, realname);
        ps.setString(4, email);
        try {
            ps.execute();
        }catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean isUserRegistered(String username, String password) throws SQLException {
        String prs = SQLConstants.IS_USER_REGISTERED;
        Connection con = db.connect();
        PreparedStatement ps = con.prepareStatement(prs);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        rs.next();
        if (rs.getInt("rowcount") > 0) {
            return true;
        }
        return false;
    }

    public boolean isUsernameAvailable(String username) {
        String prs = SQLConstants.IS_USERNAME_AVAILABLE;
        Connection con = db.connect();
        try {
            PreparedStatement ps = con.prepareStatement(prs);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt("rowcount") > 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isAdmin(String username) {
        String prs = SQLConstants.IS_USERNAME_ADMIN;
        Connection con = db.connect();
        try {
            PreparedStatement ps = con.prepareStatement(prs);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt("rowcount") > 0) {
                return !false;
            }
            return !true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*Student Actions*/

    public boolean applyForCourse(String studentUsername, String courseId) {
        String prs = SQLConstants.APPLY_FOR_COURSE;
        Connection con = db.connect();
        try {
            PreparedStatement ps = con.prepareStatement(prs);
            ps.setString(1, studentUsername);
            ps.setString(2, courseId);
            ps.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*Admin Actions*/

    public boolean addCourse(String courseId, String courseName, int credits) {
        String prs = SQLConstants.ADD_COURSE;
        Connection con = db.connect();
        try {
            PreparedStatement ps = con.prepareStatement(prs);
            ps.setString(1, courseId);
            ps.setString(2, courseName);
            ps.setInt(3, credits);
            ps.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean acceptStudentToCourse(String studentUsername, String courseId) {
        String prs = SQLConstants.ACCEPT_STUDENT_TO_COURSE;
        Connection con = db.connect();
        try {
            PreparedStatement ps = con.prepareStatement(prs);
            ps.setString(1, studentUsername);
            ps.setString(2, courseId);
            ps.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean denyStudentToCourse(String studentUsername, String courseId) {
        String prs = SQLConstants.DENY_STUDENT_TO_COURSE;
        Connection con = db.connect();
        try {
            PreparedStatement ps = con.prepareStatement(prs);
            ps.setString(1, studentUsername);
            ps.setString(2, courseId);
            ps.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean setStudentGradeForCourse(int grade, String studentUsername, String courseId) {
        String prs = SQLConstants.SET_STUDENT_GRADE_FOR_COURSE;
        Connection con = db.connect();
        try {
            PreparedStatement ps = con.prepareStatement(prs);
            ps.setInt(1, grade);
            ps.setString(2, studentUsername);
            ps.setString(3, courseId);
            ps.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<StudentCourses> getPendingStudents() {
        String prs = SQLConstants.GET_STUDENT;
        Connection con = db.connect();
        try {
            List<StudentCoursesStrings> studentIds = getPendingStudentsUsernames();
            List<StudentCourses> students = new ArrayList<>();
            for (StudentCoursesStrings studentId : studentIds) {
                PreparedStatement ps = con.prepareStatement(prs);
                ps.setString(1, studentId.getStudentUsername());
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    Student student = new Student();
                    StudentCourses studentCourses = new StudentCourses();
                    List<Courses> courses = new ArrayList<>();
                    student.setUsername(rs.getString("username"));
                    student.setRealName(rs.getString("realname"));
                    student.setEmail(rs.getString("email"));
                    studentCourses.setGrade(0);
                    studentCourses.setStudent(student);
                    courses.add(getCourse(studentId.getStudentCourse()));
                    studentCourses.setCoursesList(courses);
                    students.add(studentCourses);
                }
            }
            return students;
        }catch (Exception e) {
            System.out.println("Error");
            return null;
        }
    }

    private Courses getCourse(String courseId){
        String prs = SQLConstants.GET_COURSE;
        Connection con = db.connect();
        try {
            PreparedStatement ps = con.prepareStatement(prs);
            ps.setString(1, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Courses course = new Courses();
                course.setCourseId(rs.getString("courseid"));
                course.setCourseName(rs.getString("coursename"));
                course.setCredits(rs.getInt("credits"));
                return course;
            }
        }catch (Exception e) {
            System.out.println("Error in 204");
        }
        return null;
    }

    //Helper function to retrieve students pending
    private List<StudentCoursesStrings> getPendingStudentsUsernames() throws Exception {
        String prs = SQLConstants.PENDING_STUDENTS;
        Connection con = db.connect();
        try {
            PreparedStatement ps = con.prepareStatement(prs);
            ResultSet rs = ps.executeQuery();
            List<StudentCoursesStrings> studentCoursesStrings = new ArrayList<>();
            while(rs.next()) {
                StudentCoursesStrings studentCoursesString = new StudentCoursesStrings();
                studentCoursesString.setStudentUsername(rs.getString("studentUsername"));
                studentCoursesString.setStudentCourse(rs.getString("courseid"));
                studentCoursesStrings.add(studentCoursesString);
            }
            return studentCoursesStrings;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<StudentCourses> getAcceptedCandidates() {
        String prs = SQLConstants.GET_STUDENT;
        Connection con = db.connect();
        try {
            List<StudentCoursesStrings> studentIds = getAcceptedCandidatesUsernames();
            System.out.println("in getAcceptedCandidates list"+studentIds);
            List<StudentCourses> students = new ArrayList<>();
            for (StudentCoursesStrings studentId : studentIds) {
                System.out.println("in getAcceptedCandidates list"+studentId.getStudentUsername());
                PreparedStatement ps = con.prepareStatement(prs);
                ps.setString(1, studentId.getStudentUsername());
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    System.out.println("in getAcceptedCandidates"+studentId.getStudentUsername());
                    Student student = new Student();
                    StudentCourses studentCourses = new StudentCourses();
                    List<Courses> courses = new ArrayList<>();
                    student.setUsername(rs.getString("username"));
                    student.setRealName(rs.getString("realname"));
                    student.setEmail(rs.getString("email"));
                    studentCourses.setGrade(studentId.getGrade());
                    studentCourses.setStudent(student);
                    courses.add(getCourse(studentId.getStudentCourse()));
                    studentCourses.setCoursesList(courses);
                    students.add(studentCourses);
                }
            }
            return students;
        }catch (Exception e) {
            System.out.println("Error");
            return null;
        }
    }

    private List<StudentCoursesStrings> getAcceptedCandidatesUsernames() throws Exception {
        String prs = SQLConstants.ACCEPTED_STUDENTS;
        Connection con = db.connect();
        try {
            PreparedStatement ps = con.prepareStatement(prs);
            ResultSet rs = ps.executeQuery();
            List<StudentCoursesStrings> studentCoursesStrings = new ArrayList<>();
            while(rs.next()) {
                StudentCoursesStrings studentCoursesString = new StudentCoursesStrings();
                studentCoursesString.setStudentUsername(rs.getString("studentUsername"));
                studentCoursesString.setStudentCourse(rs.getString("courseid"));
                System.out.println("in getAcceptedCandidatesUsernames"+studentCoursesString.getStudentUsername());
                int grade = rs.getInt("grade");
                System.out.println("gets here");
                studentCoursesString.setGrade(rs.wasNull() ? 0 : grade);
                studentCoursesStrings.add(studentCoursesString);
            }
            return studentCoursesStrings;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<Student> getStudentsOnCourse(String courseId) {
        String prs = SQLConstants.GET_STUDENT;
        Connection con = db.connect();
        try {
            List<String> studentIds = getStudentsOnCourseUsernames(courseId);
            List<Student> students = new ArrayList<>();
            for (String studentId : studentIds) {
                PreparedStatement ps = con.prepareStatement(prs);
                ps.setString(1, studentId);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    Student student = new Student();
                    student.setUsername(rs.getString("username"));
                    student.setRealName(rs.getString("realname"));
                    student.setEmail(rs.getString("email"));
                    students.add(student);
                }
                return students;
            }
        }catch (Exception e) {
            System.out.println("Error");
            return null;
        }
        return null;
    }

    //Helper function to retrieve students pending
    private List<String> getStudentsOnCourseUsernames(String courseId) throws Exception {
        String prs = SQLConstants.STUDENTS_ON_COURSE;
        Connection con = db.connect();
        try {
            PreparedStatement ps = con.prepareStatement(prs);
            ResultSet rs = ps.executeQuery();
            ps.setString(1, courseId);
            List<String> studentUsernames = new ArrayList<>();
            while(rs.next()) {
                studentUsernames.add(rs.getString("studentUsername"));
            }
            return studentUsernames;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<Student> getAcceptedStudents() {
        String prs = SQLConstants.GET_STUDENT;
        Connection con = db.connect();
        try {
            List<String> studentIds = getAcceptedStudentsUsernames();
            List<Student> students = new ArrayList<>();
            for (String studentId : studentIds) {
                PreparedStatement ps = con.prepareStatement(prs);
                ps.setString(1, studentId);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    Student student = new Student();
                    student.setUsername(rs.getString("username"));
                    student.setRealName(rs.getString("realname"));
                    student.setEmail(rs.getString("email"));
                    students.add(student);
                }
                return students;
            }
        }catch (Exception e) {
            System.out.println("Error");
            return null;
        }
        return null;
    }

    //Helper function to retrieve students pending
    private List<String> getAcceptedStudentsUsernames() throws Exception {
        String prs = SQLConstants.ACCEPTED_STUDENTS;
        Connection con = db.connect();
        try {
            PreparedStatement ps = con.prepareStatement(prs);
            ResultSet rs = ps.executeQuery();
            List<String> studentUsernames = new ArrayList<>();
            while(rs.next()) {
                studentUsernames.add(rs.getString("studentUsername"));
            }
            return studentUsernames;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<Courses> getCourses() throws Exception {
        String prs = SQLConstants.COURSES;
        Connection con = db.connect();
        try {
            PreparedStatement ps = con.prepareStatement(prs);
            List<Courses> courses = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Courses course = new Courses();
                course.setCourseId(rs.getString("courseid"));
                course.setCourseName(rs.getString("coursename"));
                course.setCredits(rs.getInt("credits"));
                courses.add(course);
            }
            return courses;
        } catch (Exception e) {
            return null;
        }
    }

    private Student getStudent(String username) throws Exception {
        String prs = SQLConstants.GET_STUDENT;
        Connection con = db.connect();
        try {
            PreparedStatement ps = con.prepareStatement(prs);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Student student = new Student();
                student.setEmail(rs.getString("email"));
                student.setRealName(rs.getString("realname"));
                student.setUsername(rs.getString("username"));
                return student;
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
        return null;
    }

    public StudentCourses getStudentCourses(String username) throws Exception {
        String prs = SQLConstants.GET_COURSES_FOR_STUDENT;
        Student student = getStudent(username);
        Connection con = db.connect();
        try {
            PreparedStatement ps = con.prepareStatement(prs);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            StudentCourses studentCourses = new StudentCourses();
            studentCourses.setStudent(student);
            List<Courses> courses = new ArrayList<>();
            while (rs.next()) {
                Courses course = getCourse(rs.getString("courseid"));
                int grade = rs.getInt("grade");
                course.setStatus(rs.getString("status"));
                course.setGrade(rs.wasNull() ? 0 : grade);
                courses.add(course);
            }
            studentCourses.setCoursesList(courses);
            return studentCourses;
        } catch (Exception e) {
            System.out.println("Something wrong on line 411");
        }
        return null;
    }

    public List<Courses> getAvailableCourses(String username) throws Exception {

        List<Courses> potentialCourses = getCourses();
        List<Courses> availableCourses = new ArrayList<>();
        for (Courses course : potentialCourses) {
            if (!isCandidateOnCourse(username, course.getCourseId())) {
                availableCourses.add(course);
            }
        }
        return availableCourses;
    }

    private boolean isCandidateOnCourse(String username, String courseId) {
        String prs = SQLConstants.IS_CANDIDATE_ON_COURSE;
        Connection con = db.connect();
        try {
            PreparedStatement ps = con.prepareStatement(prs);
            ps.setString(1, username);
            ps.setString(2, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
           return false;
        } catch (Exception e) {
            System.out.println("Something wrong on line isCandidateOnCourse");
        }
        return false;
    }
}
