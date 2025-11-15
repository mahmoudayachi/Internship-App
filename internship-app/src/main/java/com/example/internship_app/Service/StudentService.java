package com.example.internship_app.Service;

import com.example.internship_app.Dto.ApplicationDTO;
import com.example.internship_app.Dto.StudentDTO;
import com.example.internship_app.Entities.Application;
import com.example.internship_app.Entities.InternshipPost;
import com.example.internship_app.Entities.Student;
import com.example.internship_app.Enums.ApplicationStatus;
import com.example.internship_app.Repositories.ApplicationRepository;
import com.example.internship_app.Repositories.InternshipPostRepository;
import com.example.internship_app.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {

    public String profileimagedirectory = System.getProperty("user.dir") + "./src/main/resources/profileImages";
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private InternshipPostRepository internshipPostRepository;
    @Autowired
    private StudentRepository studentRepository;

    public ApplicationDTO getApplicationDto(Application application){
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setApplicationDate(application.getApplicationDate());
        applicationDTO.setId(application.getId());
        applicationDTO.setStatus(application.getStatus());
        applicationDTO.setCv(application.getCv());
        applicationDTO.setMotivationLetter(application.getMotivationLetter());
        applicationDTO.setStudent_id(application.getStudent().getId());
        applicationDTO.setInternship_offer_id(application.getInternshipOffer().getId());
        return applicationDTO;
    }



    public ApplicationDTO ApplytoOffer(ApplicationDTO application) throws IOException {
        Student student = studentRepository.findById(application.getStudent_id()).orElseThrow(()-> new RuntimeException("student doesn't exist "));
        InternshipPost offer = internshipPostRepository.findById(application.getInternship_offer_id()).orElseThrow(()->new RuntimeException("post doesn't exist "));

        Application newapplication = new Application();
        newapplication.setStatus(ApplicationStatus.PENDING);
        newapplication.setStudent(student);
        newapplication.setInternshipOffer(offer);
        newapplication.setCv(application.getCv());
        newapplication.setMotivationLetter(application.getMotivationLetter());
        newapplication.setApplicationDate(LocalDateTime.now());
        Application savedapplication = applicationRepository.save(newapplication);
        return getApplicationDto(savedapplication);

    }

    public StudentDTO Updateprofile(Long  studentid , Student student, MultipartFile logo) throws IOException {
        Student existingstudent = studentRepository.findById(studentid).orElseThrow(()->new RuntimeException("student doesn't exist"));

        existingstudent.setBio(student.getBio());
        existingstudent.setResume(student.getResume());
        existingstudent.setEmail(student.getEmail());
        if(student.getPassword()!=null) {
            existingstudent.setPassword(new BCryptPasswordEncoder().encode(student.getPassword()));
        }
        existingstudent.setFullName(student.getFullName());
        String originalprofileimagename = logo.getOriginalFilename();
        Path filenameandpath = Paths.get(profileimagedirectory,originalprofileimagename);
        Files.write(filenameandpath,logo.getBytes());
        existingstudent.setProfileimage(originalprofileimagename);
        Student updatestudent = studentRepository.save(existingstudent);
       StudentDTO studentDTO = new StudentDTO();
        return studentDTO.getstudentdto(updatestudent);

    }

    public StudentDTO GetstudentById(Long id ){
        Student exisitngstudent = studentRepository.findById(id).orElseThrow(()->new RuntimeException("student not found"));
        StudentDTO studentDTO = new StudentDTO();
        return studentDTO.getstudentdto(exisitngstudent);
    }
    public List<Application> GetApplicationBystudent(Long id ){
      return applicationRepository.findByStudentId(id);
    }

    public List<Application> getAcceptedApplicationsByStudent(Long studentId) {
        return applicationRepository.findByStudentIdAndStatus(studentId, ApplicationStatus.ACCEPTED);
    }

    public Set<InternshipPost> saveInternship(Long studentId, Long postId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        InternshipPost post = internshipPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        if (student.getSavedInternships().contains(post)) {
            return student.getSavedInternships();
        }

        student.getSavedInternships().add(post);
        studentRepository.save(student);

        return student.getSavedInternships();
    }

    @Transactional
    public Set<InternshipPost> DeletesavedInternship(Long studentId, Long postId) {
        studentRepository.unsaveInternship(studentId, postId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return student.getSavedInternships();
    }


    public Set<InternshipPost> getsavedInternshippost(Long studentId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return student.getSavedInternships();
    }
}
