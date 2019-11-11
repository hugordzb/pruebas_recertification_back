package com.truper.recertification.vo;

import com.truper.recertification.utils.constants.AuthorityName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Authority {

    private Long id;
    private AuthorityName name;
}