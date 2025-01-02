package com.metalearning.user.userdto;

import com.metalearning.user.userentity.UserStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSignUpDTO {
    private Long userId;
    private String userEmail;
    private String userPw;
    private String userRole;
    private String name;
    private String userGender;
    private LocalDate userBirth;
    private String userPhone;
    private String userPostcode;
    private String userAddress;
    private String userAddressDetail;
    private String userEduLevel;
    private Boolean userMarketingAgree;  // Boolean으로 변경
    private Boolean userPrivacyAgree;    // Boolean으로 변경
    private UserStatus userStatus;       // Enum으로 변경
    private LocalDateTime userCreatedAt;
    private LocalDateTime userUpdatedAt;
    private String userSns;
    private String userThumbnail;
    private LocalDateTime userLastLogin;
}
