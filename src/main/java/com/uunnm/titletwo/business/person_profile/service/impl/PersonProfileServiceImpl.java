package com.uunnm.titletwo.business.person_profile.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.uunnm.titletwo.business.person_profile.entity.PersonProfile;
import com.uunnm.titletwo.business.person_profile.mapper.PersonProfileMapper;
import com.uunnm.titletwo.business.person_profile.service.PersonProfileService;
import org.springframework.stereotype.Service;

@Service
public class PersonProfileServiceImpl extends ServiceImpl<PersonProfileMapper, PersonProfile> implements PersonProfileService {

}
