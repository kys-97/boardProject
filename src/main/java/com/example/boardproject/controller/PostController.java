package com.example.boardproject.controller;

import com.example.boardproject.data.dto.CommentRequest;
import com.example.boardproject.data.dto.MemberResponse;
import com.example.boardproject.data.dto.PostRequest;
import com.example.boardproject.data.dto.PostResponse;
import com.example.boardproject.service.MemberService;
import com.example.boardproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/post")
@RequiredArgsConstructor
@Controller //이걸 붙여줘야 페이지와 매핑이 됨
public class PostController {
    private final PostService postService;
    private final PostResponse postResponse;
    private final MemberService memberService;

    //이제 안 써서 주석처리
//    @GetMapping("/list") //"/post/list"와 같음
//    public String list(Model model) {  //Model은 데이터베이스에서 뷰로 전달할때 사용하는 객체
//        List<Post> postList = this.postService.getList();
//        model.addAttribute("postList", postList); //attribute 모델에 잇는 속성 postlist.html과 연결하려고 씀
//        return "listpage";   //11/03 page_list였는데 수정함
//    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, CommentRequest commentRequest) {
        PostResponse post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "post_detail";
    }

    //11/7 @PreAuthorize 추가
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String postCreate(PostRequest postRequest) {

        return "post_form";
    }

    //  PostRequest --> @NotEmpty, @Size 등으로 설정한 검증 기능 동작
// BindingResult 매개변수는 @Valid 애너테이션으로 인해 검증이 수행된 결과를 의미하는 객체이다.
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String postCreate(@Valid PostRequest postRequest,
                             BindingResult bindingResult, Principal principal, MultipartFile file) throws Exception {
        if (bindingResult.hasErrors()) {
            return "post_form";
        }
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "등록 권한이 없습니다.");
        }
        MemberResponse memberResponse = this.memberService.getMember(principal.getName());
        postService.create(postRequest, memberResponse, file);
        return "redirect:/post/list";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("update/{id}")
    public String postUpdate(PostRequest postRequest, @PathVariable("id") Integer id, Principal principal) {
        PostResponse postResponse = this.postService.getPost(id);
        if(!postResponse.getAuthor().getNickname().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        postRequest.setSubject(postResponse.getSubject());
        postRequest.setContent(postResponse.getContent());
        return "post_update";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("update/{id}")
    public String postUpdate(@Valid PostRequest postRequest, BindingResult bindingResult, Principal principal,
                             @PathVariable("id") Integer id, MultipartFile newFile) throws Exception {
        if (bindingResult.hasErrors()) {
            return "post_update";
        }
        PostResponse postResponse = this.postService.getPost(id);
        if (!postResponse.getAuthor().getNickname().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.postService.update(postResponse, postRequest.getSubject(), postRequest.getContent(), newFile);
        return String.format("redirect:/post/detail/%s", id);   //  /post/detail/{id}로 리디렉션
    }





    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String postdelete(Principal principal, @PathVariable("id") Integer id) {
        PostResponse postResponse = this.postService.getPost(id);
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제권한이 없습니다..");
        }
        if (!postResponse.getAuthor().getNickname().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.postService.delete(postResponse);
        return "redirect:/post/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String postVote(Principal principal, @PathVariable("id") Integer id) {
        PostResponse postResponse = this.postService.getPost(id);
        MemberResponse memberResponse = this.memberService.getMember(principal.getName());
        this.postService.vote(postResponse, memberResponse);
        return String.format("redirect:/post/detail/%s", id);
    }
}