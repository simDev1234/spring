package com.example.fastlms.admin.controller;

import com.example.fastlms.admin.dto.BannerDto;
import com.example.fastlms.admin.model.BannerParam;
import com.example.fastlms.banner.entity.BannerTypeCode;
import com.example.fastlms.banner.service.BannerService;
import com.example.fastlms.course.controller.BaseController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminBannerController extends BaseController {

    private final BannerService bannerService;

    @GetMapping("/admin/banner/list.do")
    public String bannerListPage(BannerParam parameter, Model model){

        parameter.init();

        List<BannerDto> list = bannerService.list(parameter);

        long totalCount = 0;

        if (!CollectionUtils.isEmpty(list)) {
            totalCount = list.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();

        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(),
                parameter.getPageIndex(), queryString);

        model.addAttribute("list", list);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/banner/list";
    }

    @GetMapping("/admin/banner/register.do")
    public String bannerRegisterPage(){
        return "admin/banner/detail";
    }

    @PostMapping("/admin/banner/register.do")
    public String bannerRegister(BannerParam param, MultipartFile file){

        // 파일 저장
        loadFile(param, file);

        // 배너 타입
        String bannerType = "";
        if (param.getBannerType() == BannerTypeCode.BANNER_TYPE_THIS_WINDOW) {
            bannerType = BannerTypeCode.BANNER_TYPE_THIS_WINDOW;
        } else {
            bannerType = BannerTypeCode.BANNER_TYPE_NEW_WINDOW;
        }

        // 배너 등록
        bannerService.register(
                BannerDto.builder()
                        .title(param.getTitle())
                        .localFilePath(param.getLocalFilePath())
                        .urlFilePath(param.getUrlFilePath())
                        .link(param.getLink())
                        .bannerType(bannerType)
                        .bannerOrder(param.getBannerOrder())
                        .openYn(param.isOpenYn())
                        .build()
        );

        return "redirect:/admin/banner/list.do";
    }

    private void loadFile(BannerParam param, MultipartFile file) {
        if (file != null) {
            // 파일 경로
            String[] path = getFilePath("C:\\sebinSample\\spring\\prj-01-LMS\\fastlms\\files",
                    "/files", file.getOriginalFilename());
            param.setLocalFilePath(path[0]);
            param.setUrlFilePath(path[1]);

            // 로컬 경로에 파일 저장
            File newLocalFile = new File(path[0]);

            try {
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newLocalFile));
            } catch (FileNotFoundException e) {
                log.info(e.getMessage());
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }
    }

    private String[] getFilePath(String baseLocalPath, String baseUrlPath, String originalFilename) {

        // 금일 날짜로 디렉토리 생성
        String[] newDir = makeDirWithDate(baseLocalPath, baseUrlPath, LocalDate.now());

        // 파일 확장자 추출
        String fileExtension = getFileExtension(originalFilename);

        // uuid로 파일명 변환
        String fileUUID = UUID.randomUUID().toString().replace("-","");
        String newLocalFilePath = String.format("%s/%s.%s", newDir[0], fileUUID,fileExtension);
        String newUrlFilePath = String.format("%s/%s.%s", newDir[1], fileUUID, fileExtension);

        return new String[]{newLocalFilePath, newUrlFilePath};
    }

    private static String getFileExtension(String originalFilename) {
        String fileExtension = "";
        if (originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }
        return fileExtension;
    }

    private static String[] makeDirWithDate(String baseLocalPath, String baseUrlPath, LocalDate today) {

        String[] dirs = {
                String.format("%s/%d", baseLocalPath,today.getYear()),
                String.format("%s/%d/%02d", baseLocalPath, today.getYear(), today.getMonthValue()),
                String.format("%s/%d/%02d/%02d", baseLocalPath, today.getYear(), today.getMonthValue(), today.getDayOfMonth())
        };

        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String newLocalDir = dirs[2];
        String newUrlDir = String.format("%s/%d/%02d/%02d",
                baseUrlPath, today.getYear(), today.getMonthValue(), today.getDayOfMonth());

        return new String[]{newLocalDir, newUrlDir};
    }

}
