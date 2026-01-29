package com.example.internship_app.Service;

import com.example.internship_app.Dto.InternshipPostDto;
import com.example.internship_app.Entities.Company;
import com.example.internship_app.Entities.InternshipPost;
import com.example.internship_app.Enums.InternshipPostStatus;
import com.example.internship_app.Enums.InternshipType;
import com.example.internship_app.Repositories.CompanyRepository;
import com.example.internship_app.Repositories.InternshipPostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InternshipPostService {

    private final InternshipPostRepository internshipPostRepository ;
    private final CompanyRepository companyRepository;
    public InternshipPostService(InternshipPostRepository internshipPostRepository, CompanyRepository companyRepository) {
        this.internshipPostRepository = internshipPostRepository;
        this.companyRepository = companyRepository;
    }

    public InternshipPostDto getinternshippostdto(InternshipPost post){
        InternshipPostDto postdto = new InternshipPostDto();
        postdto.setCompany_id(post.getId());
        postdto.setTitle(post.getTitle());
        postdto.setDescription(post.getDescription());
        postdto.setLocation(post.getLocation());
        postdto.setCompany_id(post.getCompany().getId());
        postdto.setCreatedAt(post.getCreatedAt());
        postdto.setRequirements(post.getRequirements());
        postdto.setSkills(post.getSkills());
        postdto.setStatus(post.getStatus());
        postdto.setDuration(post.getDuration());
        postdto.setApplydeadline(post.getApplydeadline());
        postdto.setInternshiptype(post.getInternshiptype());
        postdto.setStartDate(post.getStartDate());
        postdto.setEndDate(post.getEndDate());
        return postdto;
    }


    public InternshipPostDto createInternshipPost(InternshipPostDto post){
        InternshipPost internshipPost = new InternshipPost();
         Company company = companyRepository.findById(post.getCompany_id()).orElseThrow();
         internshipPost.setTitle(post.getTitle());
         internshipPost.setApplydeadline(post.getApplydeadline());
         internshipPost.setDescription(post.getDescription());
         internshipPost.setLocation(post.getLocation());
         internshipPost.setCreatedAt(LocalDateTime.now());
         internshipPost.setDuration(post.getDuration());
         internshipPost.setStartDate(post.getStartDate());
         internshipPost.setEndDate(post.getEndDate());
         internshipPost.setApplydeadline(post.getApplydeadline());
         internshipPost.setCompany(company);
         internshipPost.setStatus(InternshipPostStatus.AVAILABLE);
         internshipPost.setInternshiptype(post.getInternshiptype());
         internshipPost.setRequirements(post.getRequirements());
         internshipPost.setSkills(post.getSkills());
        InternshipPost createdpost = internshipPostRepository.save(internshipPost);
         return getinternshippostdto(createdpost);
    }

    public void deletePost(Long id) {
        InternshipPost post = internshipPostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Internship post not found"));

        internshipPostRepository.delete(post);
    }

    public InternshipPostDto UpdateInternshipPost(InternshipPost post, Long id){
      InternshipPost existingpost = internshipPostRepository.findById(id).orElseThrow();
      existingpost.setRequirements(post.getRequirements());
      existingpost.setApplydeadline(post.getApplydeadline());
      existingpost.setInternshiptype(post.getInternshiptype());
      existingpost.setDescription(post.getDescription());
      existingpost.setStatus(post.getStatus());
      existingpost.setTitle(post.getTitle());
      existingpost.setEndDate(post.getEndDate());
       InternshipPost updatedpost =internshipPostRepository.save(existingpost);
       return getinternshippostdto(updatedpost);
    }


    public Page<InternshipPost> getFilteredInternshipsPaged(InternshipPostStatus status, String location,String duration, InternshipType type,String title,List<String> skills,String description,Pageable pageable) {
        if (location != null && location.isBlank()) location = null;
        if (duration != null && duration.isBlank()) duration = null;
        if (status != null && status.toString().equalsIgnoreCase("All")) status = null;
        if (type != null && type.toString().equalsIgnoreCase("All")) type = null;
        if (skills != null && skills.isEmpty()) skills = null;

        return internshipPostRepository.searchInternships(title, description,location,duration,status,type,skills,pageable);
    }

    public List<InternshipPost> GetAllInternshipPosts(){
        return internshipPostRepository.findAll();
    }

    public InternshipPost GetinternshipPostById(Long id ){
        return  internshipPostRepository.findById(id).orElseThrow((()-> new RuntimeException("post not found")));
    }





}
