package com.uunnm.titletwo.business.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UnitsVO {
    private String category;
    private String role;
    private List<String> units;
}
