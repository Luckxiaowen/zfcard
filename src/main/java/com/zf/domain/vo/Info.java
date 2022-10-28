package com.zf.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    private Long dept_id;
    private String name;
    private Long parent_id;
    private List<Info> children;
}
