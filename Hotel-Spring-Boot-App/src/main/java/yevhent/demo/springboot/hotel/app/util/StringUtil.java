package yevhent.demo.springboot.hotel.app.util;

import org.springframework.util.StringUtils;

public class StringUtil {

    public static void requireNotEmpty(String... values) {
        for (String value : values) {
            if (!StringUtils.hasLength(value)) {
                throw new IllegalArgumentException("String value should not be empty: '" + value + "'");
            }
        }
    }
}