package com.uunnm.titletwo.business.record.bo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RepeatQueryBO {
    private String name;
    private String phone;
    private String type;
    private LocalDateTime occurTime;
}
