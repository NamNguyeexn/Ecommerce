package com.iden.services.impl;

import com.common.services.IRedisService;
import com.common.utils.ConvertJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.iden.DTO.OTP;
import com.iden.services.IOTPService;
import com.common.utils.otp.GenerateOTPCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class OTPService implements IOTPService {
    @Autowired
    private IRedisService redisService;
    @Autowired
    private ConvertJson convertJson;
    /**
     * Tai 1 thoi diem nhat dinh chi cho phep toi da 1 ma OTP cung xuat hien tren 1 tai khoan,
     * ma OTP co thoi gian ton tai 5p
     */
    @Override
    public OTP getOTPByAccountId(int accountid) throws JsonProcessingException {
        OTP otp = convertJson.convertFromJson(
                redisService.get(String.valueOf(accountid)),
                OTP.class
        );
        if (otp != null) {
            return otp;
        } else {
            String otpStr = GenerateOTPCode.getOTPCode();
            OTP o = OTP.builder()
                    .accountid(accountid)
                    .otp(otpStr)
                    .build();
            //OTP co thoi gian song 5 phut
            redisService.saveWithTimeLine(
                    "account_id:" + accountid,
                    convertJson.convertToJson(o),
                    300000
            );
            return o;
        }
    }

    @Override
    public void deleteOTPByAccountId(int accountid) throws JsonProcessingException {
        OTP o = convertJson.convertFromJson(
                redisService.get("account_id:" + accountid),
                OTP.class
        );
        if (o != null) {
            redisService.delete("account_id:" + accountid);
        }
    }


//    Nguyen code
@Override
public OTP getOTPByIdAccount(int accountid) throws JsonProcessingException {
        String key = String.valueOf(accountid);
        String value = redisService.get(key);
        if(value != null) {
            return  convertJson.convertFromJson(
                    redisService.get(String.valueOf(accountid)),
                    OTP.class
            );
        }
        else{
            OTP otp =OTP.builder()
                    .accountid(accountid)
                    .otp(GenerateOTPCode.getOTPCode())
                    .build();
            redisService.saveWithTimeLine("account_id:" + accountid,convertJson.convertToJson(otp),50000*60);
            return otp;
        }





}

}
