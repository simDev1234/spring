package com.example.demo.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NOTICE_POST_NOT_FOUND("게시글을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("작성자를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    NOTICE_USER_NOT_MATCH("본인이 작성한 글만 삭제하실 수 있습니다.", HttpStatus.BAD_REQUEST),
    ALREADY_DELETED("이미 삭제된 게시글입니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
