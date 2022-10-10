package com.example.fastlms.course.controller;

import com.example.fastlms.admin.service.CategoryService;
import com.example.fastlms.course.dto.CourseDto;
import com.example.fastlms.course.model.CourseInput;
import com.example.fastlms.course.model.CourseParam;
import com.example.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminCourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;

    /* 강좌 목록 조회 */
    @GetMapping("/admin/course/list.do")
    public String list(Model model, CourseParam parameter) {

        parameter.init();

        List<CourseDto> courses = courseService.list(parameter);

        long totalCount = 0;

        if (!CollectionUtils.isEmpty(courses)) {
            totalCount = courses.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();

        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(),
                parameter.getPageIndex(), queryString);

        model.addAttribute("list", courses);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/course/list";
    }

    /* 강좌 수정 및 등록 */
    @GetMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String add(Model model, HttpServletRequest request, CourseInput parameter) {

        // 카테고리 정보
        model.addAttribute("category", categoryService.list());

        // 수정 모드 여부 확인
        boolean editMode = request.getRequestURI().contains("/edit.do");

        CourseDto detail = new CourseDto();

        if (editMode) {

            long id = parameter.getId();

            CourseDto existCourse = courseService.getById(id);

            if (existCourse == null) {
                // error 처리
                model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
                return "common/error";
            }

            detail = existCourse;
        }

        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);

        return "admin/course/add";
    }

    private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFilename) {

        // A. 로컬 파일 경로
        // 금일 연-월-일에 해당하는 디렉토리 경로 문자열 생성
        LocalDate now = LocalDate.now();

        String[] dirs = { // 연/월/일 경로
                String.format("%s/%d/", baseLocalPath, now.getYear()),
                String.format("%s/%d/%02d/",baseLocalPath, now.getYear(),now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/",baseLocalPath, now.getYear(),
                        now.getMonthValue(), now.getDayOfMonth())
        };

        // 연-월-일 문자열 경로에 따라 디렉토리 생성
        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) { // 해당 경로가 존재하고 디렉토리인가? X
                file.mkdir(); // 해당 경로의 디렉토리를 생성한다.
            }
        }

        // B. URL 파일 경로
        String urlDir = String.format("%s/%d/%02d/%02d/",baseUrlPath, now.getYear(),
                now.getMonthValue(), now.getDayOfMonth());

        // C. 기존 파일 확장자
        String fileExtension = "";
        if (originalFilename != null){
            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }

        // D. 최종 파일 경로 추출
        // D-1.새로운 파일명(UUID)
        String uuid = UUID.randomUUID().toString().replace("-","");
        // D-2.신규 로컬 파일 경로
        String newLocalFileName = String.format("%s%s", dirs[2], uuid);
        // D-3.Url 파일 경로
        String newUrlFilename = String.format("%s%s", urlDir, uuid);
        // D-4.확장자 추가
        if (fileExtension.length() > 0) {
            newLocalFileName += "." + fileExtension;
            newUrlFilename += "." + fileExtension;
        }

        return new String[]{newLocalFileName, newUrlFilename};
    }

    /* 강좌 등록 */
    @PostMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String addSubmit(Model model, HttpServletRequest request,
                            CourseInput parameter, MultipartFile file) {

        String saveFileName = ""; // 로컬 저장 경로
        String urlFileName = "";  // URL 경로

        // 파일이 있으면
        if (file != null) {

            // 기존 파일 경로
            String originalFilename = file.getOriginalFilename();

            // 로컬 저장 경로 : 로컬 저장소에, 기존 파일명을 UUID로 암호화한 파일을 연/월/일 하위에 위치
            String baseLocalPath = "C:\\sebinSample\\spring\\prj-01-LMS\\fastlms\\files";
            // 불러올 경로 : URL 위치 - /files/하위
            String baseUrlPath = "/files";

            // 로컬 저장 경로 및 URL 경로 반환
            String[] arrFilename = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);
            saveFileName = arrFilename[0];
            urlFileName = arrFilename[1];

            // 신규 파일 저장 - 1) 파일 객체로 신규 로컬 경로 감싼 후,
            //                2) file stream을 통해 기존 파일을 해당 경로로 복사하여 저장
            File newLocalFile = new File(saveFileName);

            try {
                FileCopyUtils.copy(file.getInputStream(),new FileOutputStream(newLocalFile));
            } catch(Exception e) {
                log.info(e.getMessage());
            }

        }

        // 파라미터에 파일 경로 저장
        parameter.setSaveFilename(saveFileName);
        parameter.setUrlFilename(urlFileName);

        boolean editMode = request.getRequestURI().contains("/edit.do");

        if (editMode) {

            long id = parameter.getId();

            CourseDto existCourse = courseService.getById(id);

            if (existCourse == null) {
                // error 처리
                model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
                return "common/error";
            }

            boolean result = courseService.set(parameter);

        } else {
            boolean result = courseService.add(parameter);
        }

        return "redirect:/admin/course/list.do";
    }



    /* 강좌 삭제 */
    @PostMapping("/admin/course/delete.do")
    public String addSubmit(Model model, CourseInput parameter) {

        boolean result = courseService.del(parameter.getIdList());

        return "redirect:/admin/course/list.do";
    }



}
