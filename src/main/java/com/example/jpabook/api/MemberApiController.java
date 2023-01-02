package com.example.jpabook.api;


import com.example.jpabook.entity.Member;
import com.example.jpabook.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
