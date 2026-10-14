package com.uunnm.titletwo.business.person_profile.controller;

import com.uunnm.titletwo.business.person_profile.bo.ArchivePageBO;
import com.uunnm.titletwo.business.person_profile.service.PersonProfileService;
import com.uunnm.titletwo.business.person_profile.vo.ArchiveVO;
import com.uunnm.titletwo.common.entity.PageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person_profile")
@RequiredArgsConstructor
public class PersonProfileController {
    private final PersonProfileService personService;

    @PostMapping("page")
    public PageVO<ArchiveVO> page(@RequestBody ArchivePageBO archivePageBO) {
        return personService.page(archivePageBO);
    }
}
