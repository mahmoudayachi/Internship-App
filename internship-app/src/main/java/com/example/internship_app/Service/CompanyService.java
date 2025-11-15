package com.example.internship_app.Service;

import com.example.internship_app.Dto.ApplicationDTO;
import com.example.internship_app.Dto.CompanyDTO;
import com.example.internship_app.Entities.Application;
import com.example.internship_app.Entities.Company;
import com.example.internship_app.Entities.InternshipPost;
import com.example.internship_app.Enums.ApplicationStatus;
import com.example.internship_app.Enums.InternshipPostStatus;
import com.example.internship_app.Repositories.ApplicationRepository;
import com.example.internship_app.Repositories.CompanyRepository;
import com.example.internship_app.Repositories.InternshipPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    public String logodirectory = System.getProperty("user.dir") + "./src/main/resources/logoimages";

    @Autowired
    private ApplicationRepository applicationRepository;


    @Autowired
    private InternshipPostRepository internshipPostRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmailService emailService;



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


    public ApplicationDTO AcceptOffer(Long applicationid)  {
        Application application = applicationRepository.findById(applicationid)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(ApplicationStatus.ACCEPTED);
        applicationRepository.save(application);

        String studentEmail = application.getStudent().getEmail();
        String subject = " Internship Application Accepted";
        String body = "Hello " + application.getStudent().getFullName() + ",\n\n" +
                "Congratulations! Your application for the internship offer '" +
                application.getInternshipOffer().getTitle() + "  with the company   "+application.getInternshipOffer().getCompany().getFullName()+" has been accepted.\n\n" +
                "The company will contact you soon with further details.\n\n" +
                "Best regards,\nInternship Portal Team";

        emailService.sendmail(studentEmail, subject, body);

        return getApplicationDto(application);
    }
    public ApplicationDTO Rejectoffer (Long applicationid){
        Application application = applicationRepository.findById(applicationid)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(ApplicationStatus.REJECTED);
        applicationRepository.save(application);


        String studentEmail = application.getStudent().getEmail();
        String subject = " Internship Application Rejected";
        String body = "Hello " + application.getStudent().getFullName()+ ",\n\n" +
                "We regret to inform you that your application for the internship offer '" +
                application.getInternshipOffer().getTitle() +"with the company   "+application.getInternshipOffer().getCompany().getFullName()+ "' has been rejected.\n\n" +
                "Don't give up! You can explore more offers in our portal.\n\n" +
                "Best regards,\nInternship Portal Team";

        emailService.sendmail(studentEmail, subject, body);

        return getApplicationDto(application);
    }


    public CompanyDTO Updateprofile( Long companyid , Company company, MultipartFile logo) throws IOException {
        Company existingcompany = companyRepository.findById(companyid).orElseThrow(()->new RuntimeException("company doesn't exist"));
        existingcompany.setDescription(company.getDescription());
        existingcompany.setCompanysize(company.getCompanysize());
        existingcompany.setEmail(company.getEmail());
        existingcompany.setPassword(new BCryptPasswordEncoder().encode(company.getPassword()));
        existingcompany.setFullName(company.getFullName());
        if(logo!=null) {
            String originallogoname = logo.getOriginalFilename();
            Path filenameandpath = Paths.get(logodirectory, originallogoname);
            Files.write(filenameandpath, logo.getBytes());
            existingcompany.setCompanyLogo(originallogoname);
        }
        Company updatecompany = companyRepository.save(existingcompany);
        CompanyDTO companyDTO = new CompanyDTO();
        return companyDTO.getcompanydto(updatecompany);

    }

    public InternshipPost updateInternshippost(Long postid ,InternshipPostStatus status){
     InternshipPost post = internshipPostRepository.findById(postid).orElseThrow(()->new RuntimeException("post not found"));
     post.setStatus(status);
     InternshipPost updatedpost = internshipPostRepository.save(post);
     return updatedpost;
    }

    public CompanyDTO GetcompanyById(Long companyid){
        Company existingcompany = companyRepository.findById(companyid).orElseThrow(()->new RuntimeException("company doesn't exist"));
        CompanyDTO companyDTO = new CompanyDTO();
        return companyDTO.getcompanydto(existingcompany);
    }

    public List<InternshipPost> GetpostsByCompanyId(Long companyid){
        List<InternshipPost> post= internshipPostRepository.findByCompanyId(companyid);
        return  post;
    }

    public List<Application> getApplicationsByCompany(Long companyId) {
        return applicationRepository.findByinternshipOffer_Company_Id(companyId);
    }
}
