package com.hallak.shared_libraries.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.util.UUID;

public final class Utils {
        public static String makeHashSHA256TX(String originAddress, String destinyAddress, BigDecimal amount, UUID nonce){
            return DigestUtils.sha256Hex(originAddress + destinyAddress + amount + nonce);
    }

}
