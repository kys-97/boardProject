package com.example.boardproject.service;

import com.example.boardproject.config.DataNotFoundException;
import com.example.boardproject.data.dto.MemberResponse;
import com.example.boardproject.data.dto.PostRequest;
import com.example.boardproject.data.dto.PostResponse;
import com.example.boardproject.data.entity.Member;
import com.example.boardproject.data.entity.Post;
import com.example.boardproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final Member member;

    private PostResponse of(Post post) {
        return modelMapper.map(post, PostResponse.class);
    }

    private Post of(PostResponse postResponse) {
        return modelMapper.map(postResponse, Post.class);
    }

    //전체 리스트 받아오기
    public Page<Post> getList(int page, String kw) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(Sort.Order.desc("createdDate")); //createdDate 필드를 내림차순으로 정렬, desc : 역순(최근 날짜순)

        Pageable pageable = PageRequest.of(page, 10, Sort.by(orders)); //정렬 정보를 포함한 Pageable 생성

        //검색해서 보여 주는 기능
        if (kw != null && !kw.isEmpty()) {  //키워드가 null이 아니거라, 비어있지 않거나 둘 다 넣어서 확실하게 하려고
            return postRepository.findBySubjectContaining(kw, pageable); //키워드가 포함된 걸 보여주고
        } else {
            return this.postRepository.findAll(pageable); //검색어가 비어 있으면 전체를 보여줘라
        }

    }


    //    **** 최종 수정일이 계속 업데이트되는 오류
    public PostResponse getPost(Integer id) {
        Optional<Post> post = this.postRepository.findById(id);
        if (post.isPresent()) {
            Post p1 = post.get();
            p1.setReadCnt(p1.getReadCnt()+1);
            this.postRepository.save(p1);
            return of(post.get());
        } else {
            throw new DataNotFoundException("POST NOT FOUND");
        }
    }


    //10/31 과제
    public Page<Post> getPage(String kw, int page) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(Sort.Order.desc("createdDate")); //createdDate 필드를 내림차순으로 정렬, desc : 역순(최근 날짜순)

        Pageable pageable = PageRequest.of(page, 10, Sort.by(orders)); //정렬 정보를 포함한 Pageable 생성

        //검색해서 보여 주는 기능
        if (kw != null && !kw.isEmpty()) {  //키워드가 null이 아니거라, 비어있지 않거나 둘 다 넣어서 확실하게 하려고
            return postRepository.findBySubjectContaining(kw, pageable); //키워드가 포함된 걸 보여주고
        } else {
            return this.postRepository.findAll(pageable); //검색어가 비어 있으면 전체를 보여줘라
        }

    }

    public PostResponse create(PostRequest postRequest, MemberResponse memberResponse, MultipartFile file) throws Exception {
        PostResponse postResponse = new PostResponse();
        postResponse.setSubject(postRequest.getSubject());
        postResponse.setContent(postRequest.getContent());

        // 작성자(Member) 정보를 DB에서 가져온 MemberResponse에서 설정
        Member author = new Member();
        author.setId(memberResponse.getId());
        postResponse.setAuthor(author);

        Post post = of(postResponse);

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\upload";
        UUID uuid = UUID.randomUUID(); // 랜덤 식별자
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);
        post.setFilename(fileName);
        post.setFilepath("/upload/" + fileName);
        this.postRepository.save(post);
        return postResponse;
    }




    public PostResponse update(PostResponse postResponse, String subject, String content, MultipartFile newFile) throws Exception {
        postResponse.setSubject(subject);
        postResponse.setContent(content);

        // 새로운 파일이 전송되었을 경우에만 파일을 업로드하고 저장
        if (newFile != null && !newFile.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\upload";
            UUID uuid = UUID.randomUUID(); // 랜덤 식별자
            String fileName = uuid + "_" + newFile.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            newFile.transferTo(saveFile);

            postResponse.setFilename(fileName);
            postResponse.setFilepath("/upload/" + fileName);
        }

        postResponse.setUpdatedDate(LocalDateTime.now());
        Post post = of(postResponse);
        this.postRepository.save(post);
        return postResponse;
    }


    public void delete(PostResponse postResponse) {
        this.postRepository.deleteById(postResponse.getId());
    }

    public PostResponse vote(PostResponse postResponse, MemberResponse memberResponse) {
        postResponse.getVoter().add(memberResponse);
        this.postRepository.save(of(postResponse));
        return postResponse;
    }


}
