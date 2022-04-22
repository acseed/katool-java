package net.katool.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author hongchen.cao
 * @since 22 四月 2022
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidateUtils {
    private static final String REGEX_EMAIL = "^([a-zA-Z0-9\\-_\\.]+@[a-zA-Z0-9\\-_\\.]+)$";

    public static boolean checkEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    private static final String REGEX_MOBILE = "^[1][3,4,5,6,7,8,9][0-9]{9}$";

    public static boolean validateMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return false;
        }

        mobile = mobile.replaceAll("\\s", "");

        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    //校验18位身份证
    public static boolean validateIdCard(String idCard) {
        if (StringUtils.isBlank(idCard) || idCard.length() != 18) {
            return false;
        }

        String birthday = idCard.substring(6, 14);

        try {
            LocalDate localDate = LocalDate.parse(birthday, DateTimeFormatUtils.DATE_PLAIN_FORMATTER);
            if (Objects.isNull(localDate)) {
                return false;
            }
        } catch (Exception e) {
            log.error("Validator validateIdCard error", e);
            return false;
        }

        String idCardBase = idCard.substring(0, 17);
        //加权因子
        String[] factor = new String[] {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
        String[] verifyNumberList = new String[] {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

        int checkSum = 0;
        int len = idCardBase.length();
        for (int i = 0; i < len; i++) {
            checkSum += Integer.parseInt(idCardBase.substring(i, i + 1)) * Integer.parseInt(factor[i]);
        }
        int mode = checkSum % 11;
        String verifyNum = verifyNumberList[mode];

        return verifyNum.equals(idCard.substring(17).toUpperCase());
    }
}

