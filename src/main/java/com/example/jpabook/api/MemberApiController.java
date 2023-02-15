package com.example.jpabook.api;


import com.example.jpabook.entity.Member;
import com.example.jpabook.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;


    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){//entity자체를 파라미터로 받는건 별로 좋지 않다. 회원가입 DTO를 따로 만드는게 좋다.
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);

    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){//DTO를 따로 받는다
        Member member = new Member();
        member.setUsername(request.name);

        Long id = memberService.join(member);

        return new CreateMemberResponse(id);

    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request){

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);//분리한다.
        return new UpdateMemberResponse(findMember.getId(), findMember.getUsername());
    }

    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        return memberService.findMembers();
    }
    
    @GetMapping("/api/v2/members")
    public Result memberV2(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getUsername(),m.getId()))
                .collect(Collectors.toList());//
        return new Result(collect,collect.size());

    }

    @GetMapping("/api/v11/members")
    public List<Member> membersV11(@RequestParam(value = "name") String name){
        return memberService.findByName(name);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
        private int size;
    }
    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
        private Long id;
    }


    @Data
    static  class UpdateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private  Long id;
        private  String name;
    }

    @Data
    static class CreateMemberRequest{
        private String name;
    }

    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
