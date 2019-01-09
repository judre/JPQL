/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Semester;
import entity.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import mapper.StudentInfo;

/**
 *
 * @author andre
 */
public class MyFacade {

    EntityManagerFactory emf;

    public MyFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Student> findAllStudents()
    {
        EntityManager manager = getEntityManager();

        TypedQuery<Student> qt = manager.createNamedQuery("Student.findAll", Student.class);
        List<Student> studentList = qt.getResultList();
        return studentList;

    }
    
     public List<Student> getAllStudentsWithName(String name)
    {
        EntityManager manager = getEntityManager();

        Query q = manager.createQuery("select s from Student s where s.firstname = :name");
        q.setParameter("name", name);

        List<Student> studentList = q.getResultList();
        return studentList;

    }
     
     public Student insertStudent(Student student)
    {
        EntityManager manager = getEntityManager();
        try
        {
            manager.getTransaction().begin();

            manager.persist(student);

            manager.getTransaction().commit();
        } finally
        {
            manager.close();
        }
        return student;
    }
     
     
     public Student assignStudent(long studentid, long semesterid)
    {

        EntityManager manager = getEntityManager();

        try
        {
            manager.getTransaction().begin();
            Student student = manager.find(Student.class, studentid);
            Semester semester = manager.find(Semester.class, semesterid);

            if (student != null)
            {
                student.setCurrentsemesterId(semester);
                manager.merge(student);
            }
            manager.getTransaction().commit();
            return student;
        } finally
        {
            manager.close();
        }
    }
     
      public List<Student> findStudentsWithLastName(String name)
    {
        EntityManager manager = getEntityManager();

        Query q = manager.createQuery("select s from Student s where s.lastname = :name");
        q.setParameter("name", name);

        List<Student> studentList = q.getResultList();
        return studentList;

    }
      
     public long getAmountOfStudentsThisSemester(String semestername)
    {
        EntityManager manager = getEntityManager();

        Query q = manager.createQuery("select COUNT(s) from Student s where s.currentsemesterId.name = :name");
        q.setParameter("name", semestername);
        long result = (long) q.getSingleResult();
        return result;

    }
     public long getAmountOfStudentsAllSemesters(){
         EntityManager manager = getEntityManager();
         
         Query q = manager.createQuery("select COUNT(s) from Student s where s.currentsemesterId.name IS NOT NULL");
         long result = (long) q.getSingleResult();
         return result;
     }
     
     public List<StudentInfo> createStudentInfo()
    {
        EntityManager manager = getEntityManager();

        List<Student> list = findAllStudents();
        List<StudentInfo> studentInfoList = getStudentInfo();
        try
        {
            for (Student student : list)
            {

                StudentInfo si = new StudentInfo(student.getFirstname() + " " + student.getLastname(), student.getId(),
                        student.getCurrentsemesterId().getName(), student.getCurrentsemesterId().getDescription());

                manager.getTransaction().begin();
                studentInfoList.add(si);
                manager.persist(si);
                manager.getTransaction().commit();

            }
        } finally
        {
            manager.close();
        }
        return studentInfoList;

    }

    public List<StudentInfo> getStudentInfo()
    {
        EntityManager em = getEntityManager();

        Query q = em.createQuery("select c from StudentInfo c");

        List<StudentInfo> studentInfoList = q.getResultList();
        return studentInfoList;

    }
    
    public List<StudentInfo> getStudentInfo(Long id)
    {
        EntityManager em = getEntityManager();

        Query q = em.createQuery("select c from StudentInfo c where c.id=:id");

        List<StudentInfo> studentInfoList = q.getResultList();
        return studentInfoList;

    }
}
