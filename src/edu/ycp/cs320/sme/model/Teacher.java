package edu.ycp.cs320.sme.model;
import java.util.LinkedList;
import java.util.List;

public class Teacher extends User{
	//CRN's of the classes they teach
	private LinkedList<Course> classList = new LinkedList<Course>();
	
	public Teacher(){

	}
	
	public void addClass(Course newClass){
		this.classList.add(newClass);
	}
	
	public void removeClass(int CRN){
		int classIndex = -1;
		LinkedList<Course> tempCourseList = this.classList;
		//find index of class in class list
		for(int i = 0; i < tempCourseList.size(); i++){
			if(tempCourseList.get(i).getCRN() == CRN){
				classIndex = i;
			}
		}
		//if it exsist remove it, dont need to worry about 
		//removing a class that never exsisted
		if(classIndex != -1){
			this.classList.remove(classIndex);
		}	
	}

	public List<String> getScheduleNameList() {
		// TODO Auto-generated method stub
		return null;
	}

	public Schedule getSelectedSchedule() {
		Schedule s = new Schedule();
		s.setCourseList(this.classList);
		s.setName("Fall 2016");
		s.setSemester("Fall 2016");
		return s;
	}
}
