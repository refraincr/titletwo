package com.uunnm.titletwo.business.person_profile.service;

import com.uunnm.titletwo.business.person_profile.bo.ArchivePageBO;
import com.uunnm.titletwo.business.person_profile.entity.PersonProfile;
import com.uunnm.titletwo.business.person_profile.vo.ArchiveVO;
import com.uunnm.titletwo.business.record.entity.InputRecord;
import com.uunnm.titletwo.business.record.vo.PersonEventCountVO;
import com.uunnm.titletwo.common.entity.PageVO;

import java.util.List;

public interface PersonProfileService {
    PersonProfile processNewEvent(InputRecord inputRecord, int eventCount);
    PersonProfile getByNameAndPhone(String name, String phone);
    PageVO<ArchiveVO> page(ArchivePageBO archivePageBO);

    List<PersonProfile> processNewEvents(List<InputRecord> inputRecords, List<PersonEventCountVO> personEventCountVOS);
}
