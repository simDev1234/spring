package com.example.fastlms.member.controller;

import com.example.fastlms.member.model.MemberRegister;
import com.example.fastlms.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Test
    void successRegisterSubmit() throws Exception {
        // given
        given(memberService.register(any()))
                .willReturn(true);

        // when
        // then
        mockMvc.perform(post("/member/register")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .content(objectMapper.writeValueAsString(
                            MemberRegister.Request.builder()
                                .userId("simDev1234@gmail.com")
                                .password("a1234%")
                                .userName("testtest")
                                .phoneNumber("010-2030-2222").build()
                    ))
                ).andExpect(status().isOk())
                 .andDo(print());
    }
}