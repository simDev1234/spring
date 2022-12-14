package com.example.demo.notice.controller;

import com.example.demo.entity.Notice;
import com.example.demo.entity.User;
import com.example.demo.exception.ErrorResponse;
import com.example.demo.exception.NoticeException;
import com.example.demo.notice.model.NoticeModel;
import com.example.demo.notice.model.NoticeRegister;
import com.example.demo.notice.model.NoticeUpdate;
import com.example.demo.repository.NoticeRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.type.ErrorCode;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ApiNoticeController {

  private final NoticeRepository noticeRepository;

  private final UserRepository userRepository;

//    @Autowired
//    NoticeRepository noticeRepository;
//
//    public ApiNoticeController(NoticeRepository noticeRepository) {
//        this.noticeRepository = noticeRepository;
//    }

  @GetMapping("/api/notice")
  public List<NoticeModel> notice() {

    List<NoticeModel> noticeModelList = new ArrayList<>();

    return noticeModelList;
  }

  @GetMapping("/api/notice/count")
  public long noticeCount() {
    return 10;
  }

  /*Q11*/
  @PostMapping("/api/notice")
  public NoticeModel addNotice(@RequestParam String title, @RequestParam String contents) {
    return NoticeModel.builder()
        .id(1)
        .title(title)
        .contents(contents)
        .regDate(LocalDateTime.now())
        .build();
  }

  /*Q12*/
  @PostMapping("/api/notice2")
  public NoticeModel addNotice2(NoticeModel noticeModel) {
    noticeModel.setId(2);
    noticeModel.setRegDate(LocalDateTime.now());
    return noticeModel;
  }

  /*Q13*/
  @PostMapping("/api/notice3")
  public NoticeModel addNotice3(@RequestBody NoticeModel noticeModel) {
    noticeModel.setId(3);
    noticeModel.setRegDate(LocalDateTime.now());
    return noticeModel;
  }

  /*Q14*/
  // ????????? ?????? : ???? ??????? ???????????? ????????? ?????????, Response?????? ?????????.
  // -- ?????? ) Entity ???????????? ??????(equals)??? ?????? equals, hashcode ??????
  @PostMapping("/api/notice4")
  public Notice addNotice4(@RequestBody NoticeRegister noticeRegister) {
    return noticeRepository.save(
        Notice.builder()
            .title(noticeRegister.getTitle())
            .contents(noticeRegister.getContents())
            .build()
    );
  }

  /*Q15*/
  @PostMapping("/api/notice5")
  public Notice addNotice5(@RequestBody NoticeRegister noticeRegister) {
    return noticeRepository.save(
        Notice.builder()
            .title(noticeRegister.getTitle())
            .contents(noticeRegister.getContents())
            .build()
    );
  }

  /*Q16*/
  @GetMapping("/api/notice/{id}")
  public Notice findOneNotice(@PathVariable Long id) {
    return noticeRepository.findById(id)
        .orElse(null);
  }

  /*Q17-19*/
  /* PUT??? ?????? ?????? ???????????? ????????? ??? ????????????. */
  @PutMapping("/api/notice/{id}")
  public Notice updateOneNotice(@PathVariable(name = "id") Long notice_id,
      @RequestBody NoticeUpdate noticeUpdate) {

    Notice notice = noticeRepository.findById(notice_id)
        .orElseThrow(() -> new NoticeException(ErrorCode.NOTICE_POST_NOT_FOUND));

    notice.setTitle(noticeUpdate.getTitle());
    notice.setContents(noticeUpdate.getContents());

    return noticeRepository.save(notice);

  }

  /*Q20*/
  /* PATCH??? ?????? ???????????? ????????? ??? ????????????. */
  @PatchMapping("/api/notice/{id}/hits")
  public Notice increaseViews(@PathVariable(name = "id") Long notice_id) {

    Notice notice = noticeRepository.findById(notice_id)
        .orElseThrow(() -> new NoticeException(ErrorCode.NOTICE_POST_NOT_FOUND));

    notice.setViews(notice.getViews() + 1);

    return noticeRepository.save(notice);
  }

  /*Q21-23*/
  /* DELETE??? ??? ?????? ?????? ????????? ??? ??? ??????, ?????? ??????(????????? ??????)??? ??? ??? ??????. */
  @DeleteMapping("/api/notices/{id}")
  public Notice deleteOneNotice(@PathVariable(name = "id") Long notice_id) {

    Notice notice = noticeRepository.findById(notice_id)
        .orElseThrow(() -> new NoticeException(ErrorCode.NOTICE_POST_NOT_FOUND));

    if (notice.isDeleted()) {
      throw new NoticeException(ErrorCode.ALREADY_DELETED);
    }

    notice.setDeleted(true);
    notice.setDeletedDate(LocalDateTime.now());

    return noticeRepository.save(notice);
  }

  /*Q24*/
  @DeleteMapping("/api/notices2/{id}")
  public Notice deleteOneNotice2(@PathVariable(name = "id") Long notice_id,
      @RequestParam Long user_id) {

    Notice notice = noticeRepository.findById(notice_id)
        .orElseThrow(() -> new NoticeException(ErrorCode.NOTICE_POST_NOT_FOUND));

    User user = userRepository.findById(user_id)
        .orElseThrow(() -> new NoticeException(ErrorCode.USER_NOT_FOUND));

    if (!Objects.equals(notice.getUser().getId(), user.getId())) {
      throw new NoticeException(ErrorCode.NOTICE_USER_NOT_MATCH);
    }

    if (notice.isDeleted()) {
      throw new NoticeException(ErrorCode.ALREADY_DELETED);
    }

    notice.setDeleted(true);
    notice.setDeletedDate(LocalDateTime.now());

    return noticeRepository.save(notice);
  }

  /*Q25*/
  @DeleteMapping("/api/notices5")
  public void deleteAll() {
    List<Notice> list = noticeRepository.findAll();

    if (!CollectionUtils.isEmpty(list)) {
      list.stream()
          .filter(notice -> !notice.isDeleted())
          .forEach(e -> {
            e.setDeleted(true);
            e.setDeletedDate(LocalDateTime.now());
          });
    }

    noticeRepository.saveAll(list);
  }

  /*Q26-28 Data validation*/
  @PostMapping("/api/notice6")
  public ResponseEntity<Object> register(@RequestBody @Valid NoticeRegister noticeRegister,
                      Errors errors) {

    if (errors.hasErrors()) {
      //return new ResponseEntity(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
      List<ErrorResponse> errorResponses = new ArrayList<>();

      errors.getAllErrors().stream().forEach(e -> errorResponses.add(ErrorResponse.of((FieldError)e)));

      return new ResponseEntity(errorResponses, HttpStatus.BAD_REQUEST);

    }

    Notice notice = noticeRepository.save(
        Notice.builder()
            .title(noticeRegister.getContents())
            .contents(noticeRegister.getContents())
            .views(0)
            .likes(0)
            .build()
    );

    return ResponseEntity.ok(notice);
  }

  /*Q29 ????????? ???????????? ????????????*/
  @GetMapping(value = "api/notice7/latest/{size}")
  public Page<Notice> noticeLatest(@PathVariable int size){

    // size????????? ???????????? regDate???????????? ?????????????????? ????????????.
    Page<Notice> notices = noticeRepository.findAll(
        PageRequest.of(0, size, Sort.Direction.DESC, "regDate"));

    return notices;
  }

  /*Q30 : ?????? ?????? ??????*/
  @PostMapping(value = "/api/notice8")
  public void register_noDuplicate(@RequestBody @Valid NoticeRegister noticeRegister) {

    // ?????? ????????? 1??? ???
    LocalDateTime checkDate = LocalDateTime.now().minusMinutes(1);

    // Validation : ????????? ????????? ????????????, ?????? ????????? ?????? ?????? ?????? ??????.
//    List<Notice> notices = noticeRepository.findByTitleAndContentsAndRegDateIsGreaterThanEqual(
//        noticeRegister.getTitle(), noticeRegister.getContents(), checkDate
//    );
//
//    if (!CollectionUtils.isEmpty(notices)) {
//      throw new NoticeException(ErrorCode.DUPLICATED_NOTICE);
//    }
    int count = noticeRepository.countByTitleAndContentsAndRegDateIsGreaterThanEqual(
      noticeRegister.getTitle(), noticeRegister.getContents(), checkDate
    );

    if (count > 0) {
      throw new NoticeException(ErrorCode.DUPLICATED_NOTICE);
    }

    Notice notice = noticeRepository.save(
        Notice.builder()
            .title(noticeRegister.getTitle())
            .contents(noticeRegister.getContents())
            .views(0)
            .likes(0)
            .build()
    );

  }

}
