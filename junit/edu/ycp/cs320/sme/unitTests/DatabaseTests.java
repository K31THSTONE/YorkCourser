package edu.ycp.cs320.sme.unitTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.ycp.cs320.sme.model.Course;
import edu.ycp.cs320.sme.model.Course.Subject;
import edu.ycp.cs320.sme.model.Student;
import edu.ycp.cs320.sme.model.Teacher;
import edu.ycp.cs320.sme.sql.DBmain;
import edu.ycp.cs320.sme.sql.DatabaseProvider;
import edu.ycp.cs320.sme.sql.IDatabase;
import edu.ycp.cs320.sme.model.*;
public class DatabaseTests {

	@BeforeClass
	public static void buildup() throws IOException{
		DBmain db = new DBmain();
		db.main(null);
		DatabaseProvider.setInstance(db);
	}
	
	@Test
	public void CourseQueryTest() {
		
		IDatabase db = DatabaseProvider.getInstance();
		//Fall 2016,10015,FYS,100.115,RACE JUSTICE AMERICA,3,LEC,HUM 144,M,,W,,,,,"Levy, Peter"
		List<Course> course = db.queryCourses(10015, "FYS", "RACE JUSTICE AMERICA");
		//should contain only one course
		Course c = course.get(0);
		
		assertEquals(Subject.FYS,c.getSubject() );
		assertEquals("100.115",c.getCourseNum());
		assertEquals(true,"RACE JUSTICE AMERICA".equals(c.getTitle()));
		String days = c.getDays();
		assertEquals(true, days == "MW");
		
		course = db.queryCourses(3, null, "work");
		//should contain only one course
		c = course.get(0);
		System.out.println(c.getTitle() +" "+ c.getInstructor().getName());
			
		//bad search returns null
		List<Course> nullList = db.queryCourses(9, null, null);
		assertEquals(nullList, null);

		
	}
	@Test
	public void getSingleCourse() {
		IDatabase db = DatabaseProvider.getInstance();
		//get a real course - 10462,FIN,310.801,Real Estate Finance,3,LEC,T,06:30PM- 09:15PM,225,WBC 225,Gregory T,25,8
		Course c = db.getCourseFromCRN(10462);
		
		assertEquals(Subject.FIN,c.getSubject());
		assertEquals("310.801",c.getCourseNum());
		assertEquals(true,"Real Estate Finance".equals(c.getTitle()));
		String days = c.getDays();
		assertEquals(true, days.charAt(0) == 'T');
		assertEquals(true,"Gregory, T".equals(c.getInstructor().getName()));
	
		//get a course not found in DB
		Course q = db.getCourseFromCRN(21012);
		assertEquals(q,null);
		
		
	}
	@Test
	public void testFetchTeacher(){
		IDatabase db = DatabaseProvider.getInstance();
		Teacher t = null;
		t = db.fetchTeacher("Hake");
		assertEquals("Hake, D",t.getName());
		System.out.println(t.getName());
		t = db.fetchTeacher("hake");
		assertEquals(false, t == null);
		t = db.fetchTeacher("xxxx");
		assertEquals(null, t);
	}
	@Test
	public void testFetchStudent(){
		IDatabase db = DatabaseProvider.getInstance();
		Student s = new Student();
		s.setName("Lebron James");
		s.setEmail("ljames@ycp.edu");
		db.updateStudent(s);
		Student retrieve = new Student();
		retrieve = db.fetchStudent("Lebron", "James", "ljames@ycp.edu");
		assertEquals(true, retrieve.getName().equals("Lebron James"));
		assertEquals(true, retrieve.getEmail().equals("ljames@ycp.edu"));
	}
	@Test
	public void testUpdateCourse(){
		//test to update course
	}
	@Test
	public void testUpdateStudent(){
		IDatabase db = DatabaseProvider.getInstance();
		Student s = new Student();
		Schedule schedule = new Schedule();
		s.setEmail("ljames@ycp.edu");
		s.setName("Lebron James");
		schedule.addCourse(db.getCourseFromCRN(10011));
		schedule.addCourse(db.getCourseFromCRN(10400));
		s.setSelectedSchedule(schedule);
		db.updateStudent(s);
		Student retrieve = db.fetchStudent("Lebron", "James", "ljames@ycp.edu");
		assertEquals(true, schedule.getCourseList().equals(retrieve.getSelectedSchedule().getCourseList()));
		assertEquals(true, retrieve.getName().equals("Lebron James"));
		assertEquals(true, retrieve.getEmail().equals("ljames@ycp.edu"));
	}
}
