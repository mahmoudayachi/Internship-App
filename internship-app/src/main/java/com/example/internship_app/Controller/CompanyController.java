package com.example.internship_app.Controller;

import com.example.internship_app.Dto.ApplicationDTO;
import com.example.internship_app.Dto.CompanyDTO;
import com.example.internship_app.Dto.InternshipPostDto;
import com.example.internship_app.Entities.Company;
import com.example.internship_app.Entities.InternshipPost;
import com.example.internship_app.Enums.InternshipPostStatus;
import com.example.internship_app.Repositories.InternshipPostRepository;
import com.example.internship_app.Service.CompanyService;
import com.example.internship_app.Service.InternshipPostService;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/company")
public class CompanyController {
    public String logodirectory = System.getProperty("user.dir") + "./src/main/resources/logoimages";

    @Autowired
    private CompanyService companyService;

    @Autowired
    private InternshipPostService internshipPostService ;
    @Autowired
    private InternshipPostRepository internshipPostRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> GetcompanyByid( @PathVariable("id") Long id ){
        return ResponseEntity.ok(companyService.GetcompanyById(id));

    }
    @GetMapping("/post/{id}")
    public ResponseEntity<?> GetpostsBycompanyid( @PathVariable Long id ){
        return ResponseEntity.ok(companyService.GetpostsByCompanyId(id));

    }
    @GetMapping("/application/{id}")
    public ResponseEntity<?> GetApplicationByCompany( @PathVariable Long id ){
        return ResponseEntity.ok(companyService.getApplicationsByCompany(id));

    }


    @GetMapping("/Logo/{companyLogo}")
    public ResponseEntity<byte[]> getcompanyLogo(@PathVariable("companyLogo") String filename) throws IOException {
        Path imagePath = Paths.get(logodirectory, filename);
        byte[] imageBytes = Files.readAllBytes(imagePath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @PutMapping("/update/post/{id}/{status}")
    public ResponseEntity<InternshipPost> updatepoststatus( @PathVariable  Long id ,@PathVariable InternshipPostStatus  status){
        return ResponseEntity.ok(companyService.updateInternshippost(id,status));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<ApplicationDTO> acceptApplication(@PathVariable Long id) {
        ApplicationDTO updated = companyService.AcceptOffer(id);
        return ResponseEntity.ok(updated);
    }
    @PutMapping("/{id}/reject")
    public ResponseEntity<ApplicationDTO> rejectApplication(@PathVariable Long id) {
        ApplicationDTO updated = companyService.Rejectoffer(id);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/updateprofile/{id}")
    public  ResponseEntity<CompanyDTO> updateprofile(@PathVariable("id") Long id , @ModelAttribute Company company, MultipartFile logo) throws IOException {
        return ResponseEntity.ok(companyService.Updateprofile(id,company,logo));

    }

    @PostMapping("/create")
    public ResponseEntity<InternshipPostDto>  createInternshipPost(@ModelAttribute InternshipPostDto post){
        if(post == null){
            throw new RuntimeException("post is empty");
        }
        return ResponseEntity.ok(internshipPostService.createInternshipPost(post));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInternshipPost(@PathVariable Long id) {
        internshipPostService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInternshipPost( @RequestBody InternshipPost post , Long id ){
        return ResponseEntity.ok(internshipPostService.UpdateInternshipPost(post,id));

    }


}
