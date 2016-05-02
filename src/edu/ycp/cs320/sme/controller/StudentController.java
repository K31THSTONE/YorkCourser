package edu.ycp.cs320.sme.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.ycp.cs320.sme.model.Course;
import edu.ycp.cs320.sme.model.Schedule;
import edu.ycp.cs320.sme.model.Student;
import edu.ycp.cs320.sme.model.Teacher;
import edu.ycp.cs320.sme.model.Course.Subject;
import edu.ycp.cs320.sme.sql.DatabaseProvider;
import edu.ycp.cs320.sme.sql.IDatabase;

/*
 * Build student that will (ideally) persist through session
 */
public class StudentController {
	
	public Student buildStudent(){
		Student s = new Student();
		
		s.setName("John Smith");
		s.setEmail("coolBeans@ycp.edu");
		//initialize this new "student"
		Schedule sched = new Schedule();
		sched.setName("Default");
		
		Schedule MTsched = new Schedule();
		MTsched.setName("EmptySchedule");
		//sched.setLastModified(new Date());
		 try{
	            BufferedReader reader = new BufferedReader(new FileReader(new File("./war/Student_test.csv")));
	            //Skip first line - titles
	            reader.readLine();
	            String line = "";
	            //Loop line by line
	            
	            while((line = reader.readLine()) != null){
	            	Course c = new Course();
	            	String[] tokens = line.split(",");
	            	c.setCRN(Integer.parseInt(tokens[1]));
	            	c.setSubject(Subject.valueOf(tokens[2]));
	            	c.setCourseNum(tokens[3]);
	            	c.setTitle(tokens[4]);
	            	c.setCredits(Integer.parseInt(tokens[5]));
	            	c.setRoom(tokens[7]);
	            	//monday
	            	c.setDay(tokens[8], 0);
	            	c.setDay(tokens[9], 1);
	            	c.setDay(tokens[10], 2);
	            	c.setDay(tokens[11], 3);
	            	c.setDay(tokens[12], 4);
	            	c.setDay(tokens[13], 5);
	            	//sunday
	            	c.setDay(tokens[14], 6);
	            	//Make instructor a new teacher
	            	String inst = tokens[15] + tokens[16];
	                Teacher t = new Teacher();
	                t.setName(inst);
	                c.setInstructor(t);
	                
	                sched.addCourse(c);
	            }
	            // after loop, close reader
				reader.close();
			
	        }catch (FileNotFoundException e){
	            e.printStackTrace();
	        } catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 s.addSchedule(sched);
		 s.setSelectedSchedule(sched);
		 s.addSchedule(MTsched);
		return s;
	}
	/**
	 * 
	 * @return a "john smith", either fetched from DB or built and inserted if hes not found
	 */
	public Student getGenericStudent(){
		IDatabase db = DatabaseProvider.getInstance();
		//Get john smith if he exists
		Student firstTry = db.fetchStudent("John", "Smith", null);
		if (firstTry != null){
			return firstTry;
		}
		//Otherwise create a new john smith
		Student nuStudent = new Student();
		nuStudent.setName("John Smith");
		nuStudent.setEmail("jSmith@ycp.edu");
		//get some BS classes in his schedule
		Schedule nuSchedule = new Schedule();
		nuSchedule.setName("Default0");
		Course course1 = db.getCourseFromCRN(10143);
		nuSchedule.addCourse(course1);
		Course course2 = db.getCourseFromCRN(11309);
		nuSchedule.addCourse(course2);
		Course course3 = db.getCourseFromCRN(10389);
		nuSchedule.addCourse(course3);
		Course course4 = db.getCourseFromCRN(10011);
		nuSchedule.addCourse(course4);
		
		Schedule nuSchedule2 = new Schedule();
		nuSchedule.setName("Schedule2");
		Course c2_1= db.getCourseFromCRN(10143);
		nuSchedule.addCourse(c2_1);
		
		
		nuStudent.addSchedule(nuSchedule);
		nuStudent.addSchedule(nuSchedule2);
		nuStudent.setSelectedSchedule(nuSchedule);
		db.updateStudent(nuStudent);
		
		return nuStudent;
		
	}

	public Student changeSelectedSchedule (Student s, String newSchedule){
		//We don't want to change the schedule this case
		if (newSchedule == null || newSchedule == s.getSelectedSchedule().getName()){
			return s;
		}
		s.setSelectedSchedule(s.getScheduleByN(newSchedule));
		return s;
	}

	public Student createSchedule (Student s, String newSchedule, String semester){
		Schedule newSche = new Schedule();
		newSche.setName(newSchedule);
		newSche.setSemester(semester);
		s.addSchedule(newSche);
		s.setSelectedSchedule(newSche);
		return s;
	}
	
	
	

}
