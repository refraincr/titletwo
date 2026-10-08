package com.uunnm.titletwo.business.record.contant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RiskLevel {
    LOW("低"),
    NORMAL("中"),
    HIGH("高");

    private final String name;
}
