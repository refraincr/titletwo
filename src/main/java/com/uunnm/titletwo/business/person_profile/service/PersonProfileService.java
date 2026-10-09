package com.uunnm.titletwo.business.person_profile.service;

import com.uunnm.titletwo.business.person_profile.entity.PersonProfile;
import com.uunnm.titletwo.business.record.bo.RecordAddBO;

public interface PersonProfileService {
    void processNewEvent(RecordAddBO recordAddBO,String keywords);
    PersonProfile getByNameAndPhone(String name, String phone);
    Integer getCountByNameAndPhone(String name, String phone);
}
