package gr.hua.dit.ds.controller;


import gr.hua.dit.ds.dao.StudentDAO;
import gr.hua.dit.ds.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentDAO studentDAO;


    @GetMapping("/")
    public String listStudents(Model model) {
        List<Student> students = studentDAO.geStudents();
        model.addAttribute("students",students);
        return "students";

    }
}