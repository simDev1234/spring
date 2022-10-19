package com.example.demo.notice.controller;

import com.example.demo.entity.Notice;
import com.example.demo.exception.NoticeException;
import com.example.demo.notice.model.NoticeModel;
import com.example.demo.notice.model.NoticeRegister;
import com.example.demo.notice.model.NoticeUpdate;
import com.example.demo.repository.NoticeRepository;
import com.example.demo.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ApiNoticeController {

    private final NoticeRepository noticeRepository;

//    @Autowired
//    NoticeRepository noticeRepository;
//
//    public ApiNoticeController(NoticeRepository noticeRepository) {
//        this.noticeRepository = noticeRepository;
//    }

    @GetMapping("/api/notice")
    public List<NoticeModel> notice(){

        List<NoticeModel> noticeModelList = new ArrayList<>();

        return noticeModelList;
    }

    @GetMapping("/api/notice/count")
    public long noticeCount(){
        return 10;
    }

    /*Q11*/
    @PostMapping("/api/notice")
    public NoticeModel addNotice(@RequestParam String title, @RequestParam String contents){
        return NoticeModel.builder()
                .id(1)
                .title(title)
                .contents(contents)
                .regDate(LocalDateTime.now())
                .build();
    }

    /*Q12*/
    @PostMapping("/api/notice2")
    public NoticeModel addNotice2(NoticeModel noticeModel){
        noticeModel.setId(2);
        noticeModel.setRegDate(LocalDateTime.now());
        return noticeModel;
    }

    /*Q13*/
    @PostMapping("/api/notice3")
    public NoticeModel addNotice3(@RequestBody NoticeModel noticeModel){
        noticeModel.setId(3);
        noticeModel.setRegDate(LocalDateTime.now());
        return noticeModel;
    }

    /*Q14*/
    // 트러블 슈팅 : 어? 뭐지? 데이터가 저장은 됐는데, Response값이 안온다.
    // -- 원인 ) Entity 인스턴스 비교(equals)를 위해 equals, hashcode 필요
    @PostMapping("/api/notice4")
    public Notice addNotice4(@RequestBody NoticeRegister noticeRegister){
        return noticeRepository.save(
            Notice.builder()
                    .title(noticeRegister.getTitle())
                    .contents(noticeRegister.getContents())
                    .build()
        );
    }

    /*Q15*/
    @PostMapping("/api/notice5")
    public Notice addNotice5(@RequestBody NoticeRegister noticeRegister){
        return noticeRepository.save(
                Notice.builder()
                        .title(noticeRegister.getTitle())
                        .contents(noticeRegister.getContents())
                        .build()
        );
    }

    /*Q16*/
    @GetMapping("/api/notice/{id}")
    public Notice findOneNotice(@PathVariable Long id){
        return noticeRepository.findById(id)
                               .orElse(null);
    }

    /*Q17-19*/
    /* PUT는 본래 전체 데이터를 수정할 때 사용된다. */
    @PutMapping("/api/notice/{id}")
    public Notice updateOneNotice(@PathVariable(name = "id") Long notice_id,
                                  @RequestBody NoticeUpdate noticeUpdate){
        
        Notice notice = noticeRepository.findById(notice_id)
                .orElseThrow(() -> new NoticeException(ErrorCode.NOTICE_POST_NOT_FOUND));

        notice.setTitle(noticeUpdate.getTitle());
        notice.setContents(noticeUpdate.getContents());

        return noticeRepository.save(notice);
        
    }

    /*Q20*/
    /* PATCH는 일부 데이터를 수정할 때 사용된다. */
    @PatchMapping("/api/notice/{id}/hits")
    public Notice increaseViews(@PathVariable(name = "id") Long notice_id){

        Notice notice = noticeRepository.findById(notice_id)
                .orElseThrow(() -> new NoticeException(ErrorCode.NOTICE_POST_NOT_FOUND));

        notice.setViews(notice.getViews() + 1);

        return noticeRepository.save(notice);
    }

}
