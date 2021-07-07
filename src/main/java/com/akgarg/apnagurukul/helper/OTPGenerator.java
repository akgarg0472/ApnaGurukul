package com.akgarg.apnagurukul.helper;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPGenerator {

    private static final Integer OTP_EXPIRATION_DURATION = 5;
    private final LoadingCache<String, Integer> otpCache;

    public OTPGenerator() {
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(OTP_EXPIRATION_DURATION, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    public Integer load(@NotNull String key) {
                        return 0;
                    }
                });
    }


    public int generateOTP(String key) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        otpCache.put(key, otp);
        return otp;
    }


    public int getOtp(String key) {
        try {
            return otpCache.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public void deleteOTP(String key) {
        otpCache.invalidate(key);
    }
}