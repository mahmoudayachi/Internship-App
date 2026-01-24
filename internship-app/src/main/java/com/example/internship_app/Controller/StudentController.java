package com.example.internship_app.Controller;


import com.example.internship_app.Dto.ApplicationDTO;
import com.example.internship_app.Dto.StudentDTO;
import com.example.internship_app.Entities.Application;
import com.example.internship_app.Entities.InternshipPost;
import com.example.internship_app.Entities.Student;
import com.example.internship_app.Enums.InternshipPostStatus;
import com.example.internship_app.Enums.InternshipType;
import com.example.internship_app.Service.InternshipPostService;
import com.example.internship_app.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

@RequestMapping("/student")
@RestController
public class StudentController {

    public String cvuploaddirectory = System.getProperty("user.dir") + "./src/main/resources/cvuploads";
    public String cvupload = System.getProperty("user.dir") + "./src/main/resources/cvuploads";
    public String motivationletterdirectory =  System.getProperty("user.dir") + "./src/main/resources/motivationletteruploads";
    public String profileimagedirectory = System.getProperty("user.dir") + "./src/main/resources/profileImages";
   @Autowired
   private StudentService studentService;

   @Autowired
   private InternshipPostService internshipPostService;


    @PostMapping("/job")
    public ResponseEntity<?> Applytooffer(@ModelAttribute ApplicationDTO application, @RequestParam("cvfile") MultipartFile cvfile , @RequestParam("letter") MultipartFile letter ) throws IOException {
        String cvname = cvfile.getOriginalFilename();
        Path filepath = Paths.get(cvuploaddirectory,cvname);
        Files.write(filepath,cvfile.getBytes());
        String motivationletter = letter.getOriginalFilename();
        Path filepathforletter = Paths.get(motivationletterdirectory,motivationletter);
        Files.write(filepathforletter,letter.getBytes());
        application.setMotivationLetter(motivationletter);
        application.setCv(cvname);


        return ResponseEntity.ok(studentService.ApplytoOffer(application));
    }

    @GetMapping("/profileimage/{profileimage}")
    public ResponseEntity<byte[]> getprofileimage(@PathVariable("profileimage") String filename) throws IOException {
        Path imagePath = Paths.get(profileimagedirectory, filename);
        byte[] imageBytes = Files.readAllBytes(imagePath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/letter/{filename}")
    public ResponseEntity<?> getlettter(@PathVariable String filename) throws IOException {
        try {
            Path file = Paths.get(motivationletterdirectory).resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists()) {
                System.out.println("File not found: " + file.toAbsolutePath());
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(file);
            if (contentType == null) contentType = "application/octet-stream";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error reading file: " + e.getMessage());
        }
    }

    @GetMapping("/pdf/{filename}")
    public ResponseEntity<?> getFile(@PathVariable String filename) throws IOException {
        try {
            Path file = Paths.get(cvupload).resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists()) {
                System.out.println("File not found: " + file.toAbsolutePath());
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(file);
            if (contentType == null) contentType = "application/octet-stream";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error reading file: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetstudentByid(@PathVariable("id")  Long id ){
        return ResponseEntity.ok(studentService.GetstudentById(id));
    }
    @GetMapping("/application/{id}")
    public ResponseEntity<?> GetApplicationBystudentid(@PathVariable Long id ){
        return ResponseEntity.ok(studentService.GetApplicationBystudent(id));
    }

    @GetMapping("/application/{id}/accepted")
    public ResponseEntity<List<Application>> getAcceptedApplications(@PathVariable Long id) {
        List<Application> applications = studentService.getAcceptedApplicationsByStudent(id);
        return ResponseEntity.ok(applications);
    }


    @PutMapping("/update/student/{id}")
    public  ResponseEntity<StudentDTO> updatestudentprofile(@PathVariable Long id , @ModelAttribute Student student, @RequestParam("image") MultipartFile image) throws IOException {
        return ResponseEntity.ok(studentService.Updateprofile(id,student,image));

    }
    @GetMapping("/search")
    public Page<InternshipPost> getInternships(
            @RequestParam(required = false) InternshipPostStatus status,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String duration,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) InternshipType type,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return internshipPostService.getFilteredInternshipsPaged(status, location ,duration,type, title,description,pageable);
    }
    @GetMapping("/search/all")
    public ResponseEntity<?> GetAllInternshippost(){
        return ResponseEntity.ok(internshipPostService.GetAllInternshipPosts());
    }
    @GetMapping("/search/{id}")
    public ResponseEntity<?> GetInternshippostById( @PathVariable  Long id ){
        return ResponseEntity.ok(internshipPostService.GetinternshipPostById(id));
    }

    @PostMapping("/{studentId}/save/{postId}")
    public ResponseEntity<Set<InternshipPost>> saveInternship(@PathVariable Long studentId, @PathVariable Long postId) {

        return ResponseEntity.ok(studentService.saveInternship(studentId,postId));
    }
    @DeleteMapping("/{studentId}/unsave/{postId}")
    public ResponseEntity<?> unsaveInternship(@PathVariable Long studentId, @PathVariable Long postId) {

        return ResponseEntity.ok(studentService.DeletesavedInternship(studentId,postId));
    }
    @GetMapping("/{studentId}/saved")
    public ResponseEntity<Set<InternshipPost>> getSavedInternships(@PathVariable Long studentId) {

        return ResponseEntity.ok(studentService.getsavedInternshippost(studentId));
    }

}
