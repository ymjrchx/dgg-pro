package net.dgg.gspt.dqc.dto.user;

import lombok.Data;
import net.dgg.gspt.dqc.entity.business.User;

/**
 * @author 刘阳
 * @ClassName <UserNewDto>
 * @despriction
 * @create 2018/12/27 15:27
 **/
@Data
public class UserNewDto extends User {
    private String smsVerifyCode; //短信验证码
    private String imageVerifyCode;  //图片验证码
}
