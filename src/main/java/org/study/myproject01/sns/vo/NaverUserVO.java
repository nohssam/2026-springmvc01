package org.study.myproject01.sns.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverUserVO {
    private String resultcode;
    private String message ;
    private Response response;

    @Getter
    @Setter
    public class Response{
        private String id;
        private String nickname;
        private String profile_image;
        private String email;
        private String mobile;
        private String mobile_e164;
        private String name;
    }
}
