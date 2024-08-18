package com.company.projects.course.coursemanagementsystem.controller;

import com.company.projects.course.coursemanagementsystem.dto.ClassroomDto;
import com.company.projects.course.coursemanagementsystem.dto.StudentDto;
import com.company.projects.course.coursemanagementsystem.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classrooms")
public class ClassroomControllerImpl extends BaseControllerImpl<String, ClassroomDto, ClassroomService> implements ClassroomController {
    ClassroomService classroomService;
    @Autowired
    public ClassroomControllerImpl(ClassroomService service, ClassroomService classroomService) {
        super(service);
        this.classroomService = classroomService;
    }

    @Override
    @PutMapping("/{id}/add_student")
    public ResponseEntity<ClassroomDto> addStudent(@PathVariable String id, StudentDto studentDto) {
        ClassroomDto classroomDto = classroomService.addStudent(id, studentDto);
        return ResponseEntity.ok(classroomDto);
    }

    @Override
    @PutMapping("/{id}/remove_student")
    public ResponseEntity<ClassroomDto> removeStudent(@PathVariable String id, StudentDto studentDto) {
        ClassroomDto classroomDto = classroomService.removeStudent(id, studentDto);
        return ResponseEntity.ok(classroomDto);
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<ClassroomDto>> search(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updatedAt:desc") String sort) {
        Page<ClassroomDto> results = classroomService.search(name, page, size, sort);
        return ResponseEntity.ok(results);
    }

    @Override
    @GetMapping("/filter")
    public ResponseEntity<Page<ClassroomDto>> filter(
            @RequestParam(required = false) String courseId,
            @RequestParam(required = false) String studentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updated:desc") String sort) {
        Page<ClassroomDto> results = classroomService.filter(courseId, studentId, page, size, sort);
        return ResponseEntity.ok(results);
    }
}
