package org.study.myproject01.kakao.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddrVO {
    private String postcode, roadAddress, jubunAddress, detailAddress,extraAddress;
}
