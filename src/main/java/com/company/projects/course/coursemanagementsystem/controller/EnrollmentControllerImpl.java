package com.company.projects.course.coursemanagementsystem.controller;

import com.company.projects.course.coursemanagementsystem.dto.CourseDto;
import com.company.projects.course.coursemanagementsystem.dto.EnrollmentDto;
import com.company.projects.course.coursemanagementsystem.dto.StudentDto;
import com.company.projects.course.coursemanagementsystem.mapper.StudentMapper;
import com.company.projects.course.coursemanagementsystem.model.StudentEntity;
import com.company.projects.course.coursemanagementsystem.repository.StudentRepository;
import com.company.projects.course.coursemanagementsystem.service.CourseService;
import com.company.projects.course.coursemanagementsystem.service.EmailService;
import com.company.projects.course.coursemanagementsystem.service.EnrollmentService;
import com.company.projects.course.coursemanagementsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentControllerImpl extends BaseControllerImpl<String, EnrollmentDto, EnrollmentService> implements EnrollmentController{
    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final EmailService emailService;
    private final CourseService courseService;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public EnrollmentControllerImpl(EnrollmentService service, StudentService studentService, EmailService emailService, CourseService courseService, StudentRepository studentRepository, StudentRepository studentRepository1, StudentMapper studentMapper) {
        super(service);
        this.enrollmentService = service;
        this.studentService = studentService;
        this.emailService = emailService;
        this.courseService = courseService;
        this.studentRepository = studentRepository1;
        this.studentMapper = studentMapper;
    }

    @Override
    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<EnrollmentDto> create(@RequestBody EnrollmentDto enrollmentDto) {
        StudentDto studentDto;
        StudentEntity studentEntity;
        if (!studentRepository.existsByEmail(enrollmentDto.getStudent().getEmail()) && !studentRepository.existsByPhone(enrollmentDto.getStudent().getPhone())) {
            studentDto = studentService.save(enrollmentDto.getStudent());
        } else {
            studentEntity = studentRepository.findByEmailAndDeletedFalse(enrollmentDto.getStudent().getEmail())
                    .orElseThrow(() -> new RuntimeException("Student with email or phone already exists"));
            studentDto = studentMapper.toDto(studentEntity);
        }
        enrollmentDto.setStudent(studentDto);
        enrollmentDto.setStatus("PENDING");
        EnrollmentDto createdDto = service.save(enrollmentDto);
        CourseDto courseDto = courseService.findById(createdDto.getCourse().getId());
        try {
            emailService.sendThanksEnrollment(studentDto.getEmail(), studentDto, courseDto, courseDto.getCompany());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDto);
    }

    @Override
    @PreAuthorize("hasAnyRole('admin', 'admin_company')")
    @GetMapping("/filter")
    public ResponseEntity<Page<EnrollmentDto>> filter(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String courseId,
            @RequestParam(required = false) String studentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updatedAt:desc") String sort) {
        Page<EnrollmentDto> results = enrollmentService.filter(status, date, courseId, studentId, page, size, sort);
        return ResponseEntity.ok(results);
    }
}
