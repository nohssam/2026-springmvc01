package org.study.myproject01.sns.vo;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Bool;

@Getter
@Setter
public class KakaoUserVO {
    private String id;
    private String connected_at;
    // 내부 클래스
    private Properties properties;
    // 내부 클래스
    private KakaoAccount kakao_account;

    @Getter
    @Setter
    public static class Properties{
        private String nickname;
        private String profile_image;
        private String humbnail_image;
    }

    @Getter
    @Setter
    public static class KakaoAccount{
       private Boolean profile_nickname_needs_agreement;
       private Boolean profile_image_needs_agreement;
       private Profile profile;
       private String name;
       private String email;
       private String phone_number;


       @Getter
       @Setter
       public static class Profile{
           private String nickname;
           private String thumbnail_image_url;
       }
    }

}
