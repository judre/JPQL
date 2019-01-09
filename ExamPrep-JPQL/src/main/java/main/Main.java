/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import entity.Student;
import facade.MyFacade;
import javax.persistence.Persistence;

/**
 *
 * @author andre
 */
public class Main {
    public static void main(String[] args) {
        MyFacade myfacade = new MyFacade(Persistence.createEntityManagerFactory("pu"));
        
        System.out.println("Find all Students in the system: \n" + myfacade.findAllStudents());
        
        System.out.println("__________________________________");

        System.out.println("Find all Students in the System with the first name Anders: \n" + myfacade.getAllStudentsWithName("Anders"));
        
        System.out.println("__________________________________");

        Student st = new Student("Anders", "Andersson");
        System.out.println("Insert a new Student into the system: " + myfacade.insertStudent(st) + myfacade.assignStudent(st.getId(), 1));
        
        System.out.println("__________________________________");
        long semester = 1;
        long student = 7;
        System.out.println("Assign a new student to a semester: " + myfacade.assignStudent(student, semester) + ", assigned to semester: " + semester);

        System.out.println("__________________________________");
        
        System.out.println("Find (using JPQL) all Students in the system with the last name And: \n" + myfacade.findStudentsWithLastName("And"));

        System.out.println("__________________________________");

        System.out.println("Find (using JPQL) the total number of students, for a semester given the semester name as parameter: \n" + myfacade.getAmountOfStudentsThisSemester("CLcos-v14e"));
        
        System.out.println("__________________________________");
        
        System.out.println("Find (using JPQL) the total number of students in all semesters: \n" + myfacade.getAmountOfStudentsAllSemesters());

        System.out.println("__________________________________");


       System.out.println("Create StudentInfo: \n" + myfacade.createStudentInfo());
       System.out.println("\n");
        
        System.out.println("Return a list of all Students, encapsulated as StudentInfo’s: \n" + myfacade.getStudentInfo());
        
        System.out.println("__________________________________");
        
      //  System.out.println("Create a method, which returns a single StudentInfo, given a students id as sketched below: \n" + myfacade.getStudentInfo(sem1));
        
        System.out.println("__________________________________");
        
    }
}
