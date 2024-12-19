package com.iden.controllers;

import com.common.DTO.ResponseObject;
import com.common.controllers.ControllerUtil;
import com.common.services.IEmailService;
import com.common.services.IRedisService;
import com.iden.DTO.*;
import com.iden.custom.CustomClientException;
import com.iden.mappers.AccountMapper;
import com.iden.models.Account;
import com.iden.models.ENUM.ERole;
import com.iden.models.Role;
import com.iden.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private final Map<Integer, Account> accounts = new HashMap<>();
    private final String ecommerceEmail = "montoan01102002@gmail.com";
    @Autowired
    private IJwtTokenService jwtTokenService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private IOTPService otpService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRedisAccountService redisAccountService;

    @Autowired
    private IUserClient userClient;
    /**
     * Chuc nang dang nhap
     * Khi tai khoan cua nguoi dung da xac thuc moi co the dang nhap he thong
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginInputDTO inputDTO) {
        try {
            log.info("login da vao");
            DataOutput<Object> dataOutput = DataOutput.builder().build();
            ResponseLoginDTO respDTO = ResponseLoginDTO.builder().build();
            Optional<Account> account = accountService.getAccountByUsername(inputDTO.getUsername());
            //check pass
            if (account.isPresent() && passwordEncoder.matches(inputDTO.getPassword(), account.get().getPassword())) {
                String JWT = jwtTokenService.generateToken(account.get());
                redisAccountService.pushAccountToRedis(account.get());
                log.info("Got JWT : {}", JWT);


                // Gọi API ngoài thông qua Feign client
                ResponseEntity<UserClientResponseDTO> externalApiResponse =null;
                try {
                    externalApiResponse = userClient.getUserInfo("Bearer " + JWT);
                }catch (CustomClientException e){

                }


                log.info("code");
                UserDTO userDTO=null;
                if(externalApiResponse==null) {
//                    return ControllerUtil.error("Lỗi khi gọi API ngoài");
                }
//                if (externalApiResponse.getStatusCode().is2xxSuccessful())
                else{
                    log.info("success");
                    UserClientResponseDTO userInfo = (UserClientResponseDTO)externalApiResponse.getBody();
                    // Xử lý dữ liệu từ API ngoài nếu cần
                    userDTO= (UserDTO) userInfo.getData();

                }
                AccountDTO accountDTO = accountMapper.accountToAccountDTO(account.get(),userDTO);
                dataOutput.setSuccess(true).setData(JWT).setMessage("Dang nhap thanh cong");
                respDTO.setToken(JWT);
                respDTO.setAccount(accountDTO);

                return ControllerUtil.ok(respDTO);
            } else {
                dataOutput.setMessage("Dang nhap khong thanh cong");
                return ControllerUtil.invalidated(null, "Dang nhap khong thanh cong");
            }
        }catch (CustomClientException e) {
            // Xử lý lỗi từ API bên ngoài

            log.error("Lỗi từ API bên ngoài: {}", e.getMessage());
            return ControllerUtil.error("Lỗi khi gọi API ngoài: " + e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ControllerUtil.error("Lỗi server");
        }
    }

//    Ve sua tiep
    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Object> Delete(HttpServletRequest request,@RequestParam("accountId") Integer accountId) {
        try {
            String authHeader = request.getHeader("Authorization");
//            SecurityContext context = SecurityContextHolder.getContext();
            String adminId = jwtTokenService.getAccountId(authHeader.substring(7))+"";
            if(!"1".equals(adminId)) {
                return ResponseEntity.badRequest().body(
                        ResponseObject.<String>builder()
                                .message("Bạn không có quyền xem, vui lòng đăng nhập tài khoản ADMIN")
                                .build()
                );
            }
            DataOutput<Object> dataOutput = DataOutput.builder().build();
            ResponseLoginDTO respDTO = ResponseLoginDTO.builder().build();
            Optional<Account> account = accountService.getAccountById(accountId);
            //check pass
            if (account.isPresent()) {
                String JWT = jwtTokenService.generateToken(account.get());
                redisAccountService.pushAccountToRedis(account.get());
                log.info("Got JWT : {}", JWT);


                // Gọi API ngoài thông qua Feign client
                ResponseEntity<UserClientResponseDTO> externalApiResponse =null;
                try {
                    externalApiResponse = userClient.getUserInfo("Bearer " + JWT);
                }catch (CustomClientException e){

                }


                log.info("code");
                UserDTO userDTO=null;
                if(externalApiResponse==null) {
//                    return ControllerUtil.error("Lỗi khi gọi API ngoài");
                }
//                if (externalApiResponse.getStatusCode().is2xxSuccessful())
                else{
                    log.info("success");
                    UserClientResponseDTO userInfo = (UserClientResponseDTO)externalApiResponse.getBody();
                    // Xử lý dữ liệu từ API ngoài nếu cần
                    userDTO= (UserDTO) userInfo.getData();

                }
                AccountDTO accountDTO = accountMapper.accountToAccountDTO(account.get(),userDTO);
                dataOutput.setSuccess(true).setData(JWT).setMessage("Dang nhap thanh cong");
                respDTO.setToken(JWT);
                respDTO.setAccount(accountDTO);

                return ControllerUtil.ok(respDTO);
            } else {
                dataOutput.setMessage("Dang nhap khong thanh cong");
                return ControllerUtil.invalidated(null, "Dang nhap khong thanh cong");
            }
        }catch (CustomClientException e) {
            // Xử lý lỗi từ API bên ngoài

            log.error("Lỗi từ API bên ngoài: {}", e.getMessage());
            return ControllerUtil.error("Lỗi khi gọi API ngoài: " + e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ControllerUtil.error("Lỗi server");
        }
    }


    /**
     * Chuc nang dang nhap
     * Khi tai khoan cua nguoi dung da xac thuc moi co the dang nhap he thong
     */
    @GetMapping("/my-account")
    public ResponseEntity<Object> myAccount(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
//            SecurityContext context = SecurityContextHolder.getContext();
            String accountId = jwtTokenService.getAccountId(authHeader.substring(7))+"";
            log.info("account, {}" , accountId);
            DataOutput<Object> dataOutput = DataOutput.builder().build();
            AccountDTO respDTO = AccountDTO.builder().build();
            Optional<Account> account = accountService.getAccountById(Integer.parseInt(accountId));
            //check pass
            if (account.isPresent()) {
                // Gọi API ngoài thông qua Feign client
                ResponseEntity<UserClientResponseDTO> externalApiResponse =null;
                try {
                    externalApiResponse = userClient.getUserInfo("Bearer " + authHeader.substring(7));
                }catch (CustomClientException e){

                }


                log.info("code");
                UserDTO userDTO=null;
                if(externalApiResponse==null) {
//                    return ControllerUtil.error("Lỗi khi gọi API ngoài");
                }
//                if (externalApiResponse.getStatusCode().is2xxSuccessful())
                else{
                    log.info("success");
                    UserClientResponseDTO userInfo = (UserClientResponseDTO)externalApiResponse.getBody();
                    // Xử lý dữ liệu từ API ngoài nếu cần
                    userDTO= (UserDTO) userInfo.getData();

                }
                AccountDTO accountDTO = accountMapper.accountToAccountDTO(account.get(),userDTO);
                return ControllerUtil.ok(accountDTO);
            } else {
                dataOutput.setMessage("Dang nhap khong thanh cong");
                return ControllerUtil.invalidated(null, "Dang nhap khong thanh cong");
            }
        }catch (CustomClientException e) {
            // Xử lý lỗi từ API bên ngoài

            log.error("Lỗi từ API bên ngoài: {}", e.getMessage());
            return ControllerUtil.error("Lỗi khi gọi API ngoài: " + e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ControllerUtil.error("Lỗi server");
        }
    }



    /**
     * Chuc nang dang nhap su dung email va otp
     * Su dung email de nhan ma OTP
     */
    @PostMapping("/login/request")
    public ResponseEntity<DataOutput<Object>> loginOTPRequest(@Valid @RequestBody String email) {
        try {
            DataOutput<Object> dataOutput = DataOutput.builder().build();
            Optional<Account> account = accountService.getAccountByEmail(email);
            if (account.isPresent()) {
                OTP otp = otpService.getOTPByAccountId(account.get().getId());
                if (otp != null) {
                    String OTP = otp.getOtp();
                    emailService.sendEmail(ecommerceEmail, email, email, OTP);
                    log.info("Got OTP : {}", otp.getOtp());
                    dataOutput.setSuccess(true).setData(null).setMessage("Kiem tra email cua ban");
                    return ResponseEntity.ok().body(dataOutput);
                } else {
                    dataOutput.setMessage("Dang nhap khong thanh cong");
                    return ResponseEntity.badRequest().body(dataOutput);
                }
            } else {
                dataOutput.setMessage("Khong tim thay email");
                return ResponseEntity.badRequest().body(dataOutput);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(DataOutput.builder().message("Gap loi server, kiem tra log").build());
        }
    }

    /**
     * Chuc nang xac nhan dang nhap su dung email va otp
     * Xac thuc Email va ma OTP
     */
    @PostMapping("/login/response")
    public ResponseEntity<DataOutput<Object>> loginOTPResponse(@Valid @RequestBody String OTPRequest, String email, int account_id) {
        try {
            DataOutput<Object> dataOutput = DataOutput.builder().build();
            Optional<Account> account = accountService.getAccountByEmail(email);
            if (account.isPresent()) {
                OTP OTP = otpService.getOTPByAccountId(account_id);
                if (OTP != null) {
                    //xoa otp sau khi da su dung
                    otpService.deleteOTPByAccountId(OTP.getAccountid());
                    String JWT = jwtTokenService.generateToken(account.get());
                    log.info("Got JWT : {}", JWT);
                    dataOutput.setSuccess(true).setData(JWT).setMessage("Dang nhap thanh cong");
                    return ResponseEntity.ok().body(dataOutput);
                } else {
                    dataOutput.setMessage("Dang nhap khong thanh cong");
                    return ResponseEntity.badRequest().body(dataOutput);
                }
            } else {
                dataOutput.setMessage("Khong tim thay email");
                return ResponseEntity.badRequest().body(dataOutput);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(DataOutput.builder().message("Gap loi server, kiem tra log").build());
        }
    }

    /**
     * Chuc nang dang ky nguoi dung
     */
    @PostMapping("/registry")
    public ResponseEntity<DataOutput<Object>> registry(@Valid @RequestBody RegistryInputDTO inputDTO) {
        log.info("register");
        try {
            DataOutput<Object> dataOutput = DataOutput.builder().build();
            if (accountService.checkIfUsernameExists(inputDTO.getUsername())) {
                dataOutput.setMessage("Username da ton tai");
                return ResponseEntity.badRequest().body(dataOutput);
            }
            if (accountService.checkIfEmailExists(inputDTO.getEmail())) {
                dataOutput.setMessage("Email da ton tai");
                return ResponseEntity.badRequest().body(dataOutput);
            }
            ERole eRole;
            Role  role;
            if (inputDTO.getRole() == null) {
                eRole = ERole.USER;
                role=roleService.getRoleByName(eRole.name());
            } else {
                eRole = ERole.valueOf(inputDTO.getRole());
                role=roleService.getRoleByName(eRole.name());
            }
            inputDTO.setPassword(passwordEncoder.encode(inputDTO.getPassword()));

            Account account = accountService.createAccount(accountMapper.registryInputToAccount(inputDTO, role));
            InitialUserDTO initialUserDTO= InitialUserDTO.builder()
                    .is_active(true)
                    .user_name(inputDTO.getUsername())
                    .email(inputDTO.getEmail())
                    .account_id(account.getId()+"")
                    .build();
            userClient.initialUser(initialUserDTO);
            dataOutput.setSuccess(true).setData(null).setMessage("Tao tai khoan thanh cong");
            return ResponseEntity.ok(dataOutput);

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(DataOutput.builder().message("Gap loi server, kiem tra log").build());
        }
    }

    /**
     * Chuc nang quen mat khau su dung email va otp
     * Su dung email de nhan ma OTP
     */
    @GetMapping("/forgot/request")
    public ResponseEntity<DataOutput<Object>> requestForgotPassword(@Valid @RequestBody SendMailInputDTO request) {
        try {
            DataOutput<Object> dataOutput = DataOutput.builder().build();
            String email=request.getEmail();
            Optional<Account> account = accountService.getAccountByEmail(email);
            if (account.isPresent()) {
                OTP OTP = otpService.getOTPByIdAccount(account.get().getId());
                if (OTP != null) {
                    emailService.sendEmail(ecommerceEmail, email, email, OTP.getOtp());
//                    otpService.getOTPByAccountId(account.get().getId());
                    dataOutput.setData(null).setSuccess(true).setMessage("Kiem tra mail cua ban");
                    return ResponseEntity.ok(dataOutput);
                } else {
                    dataOutput.setMessage("Khong the tao ma OTP");
                    return ResponseEntity.badRequest().body(dataOutput);
                }
            } else {
                dataOutput.setMessage("Khong tim thay tai khoan");
                return ResponseEntity.badRequest().body(dataOutput);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(DataOutput.builder().message("Gap loi server, kiem tra log").build());
        }

    }

    /**
     * Xac thuc tai khoan su dung email va ma otp
     */
    @GetMapping("/forgot/response")
    public ResponseEntity<DataOutput<Object>> responseForgotPassword(@Valid @RequestBody String email, String otp, int account_id) {
        try {
            DataOutput<Object> dataOutput = DataOutput.builder().build();
            OTP otp1 = otpService.getOTPByAccountId(account_id);
            Optional<Account> account = accountService.getAccountByEmail(email);
            if (otp1 != null) {
                if (account.isPresent()) {
                    String JWT = jwtTokenService.generateToken(account.get());
                    log.info("Got JWT : {}", JWT);
                    dataOutput.setSuccess(true).setData(JWT).setMessage("Lay JWT thanh cong");
                    otpService.deleteOTPByAccountId(account.get().getId());
                    return ResponseEntity.ok().body(dataOutput);
                } else {
                    dataOutput.setMessage("Khong tim thay tai khoan");
                    return ResponseEntity.badRequest().body(dataOutput);
                }
            } else {
                dataOutput.setMessage("Khong tim thay ma OTP");
                return ResponseEntity.badRequest().body(dataOutput);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(DataOutput.builder().message("Gap loi server, kiem tra log").build());
        }

    }

    /**
     * Thay doi tai khoan va mat khau
     * Chi co the hoat dong khi da dang nhap
     */
    @PostMapping("/change/request")
    public ResponseEntity<DataOutput<Object>> requestChangePassword(@Valid @RequestBody ChangeInputDTO changeInputDTO) {
        Optional<Account> account = accountService.getAccountByUsername(changeInputDTO.getUsername());
        DataOutput<Object> dataOutput = DataOutput.builder().build();
        try {
            if (accounts.get(account.get().getId()) != null) {
                dataOutput.setMessage("Tai khoan dang thuc hien yeu cau thay doi thong tin");
                return ResponseEntity.badRequest().body(dataOutput);
            }
            if (accountService.checkIfUsernameExists(changeInputDTO.getUsername())) {
                dataOutput.setMessage("Username da ton tai");
                return ResponseEntity.badRequest().body(dataOutput);
            }
            accounts.put(account.get().getId(), account.get());
            OTP otp = otpService.getOTPByAccountId(account.get().getId());
            if (otp != null) {
                String email = account.get().getEmail();
                String OTP = otp.getOtp();
                emailService.sendEmail(ecommerceEmail, email, email, OTP);
                dataOutput.setMessage("Kiem tra email cua ban").setData(null).setSuccess(true);
                return ResponseEntity.ok().body(dataOutput);
            } else {
                dataOutput.setMessage("Loi khi tao OTP");
                return ResponseEntity.badRequest().body(dataOutput);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(DataOutput.builder().message("Gap loi server, kiem tra log").build());
        }
    }

    //can xoa thay doi trong vong 2 phut neu khong xac thuc otp
    @PostMapping("/change/response")
    public ResponseEntity<DataOutput<Object>> responseChangePassword(@Valid @RequestBody ChangeInputDTO changeInputDTO) {
        Optional<Account> account = accountService.getAccountByUsername(changeInputDTO.getUsername());
        DataOutput<Object> dataOutput = DataOutput.builder().build();
        try {
            if (accounts.get(account.get().getId()) != null) {
                dataOutput.setMessage("Tai khoan dang thuc hien yeu cau thay doi thong tin");
                return ResponseEntity.badRequest().body(dataOutput);
            }
            OTP otp = otpService.getOTPByAccountId(account.get().getId());
            if (otp != null) {
                Account accountInChange = Account.builder()
                        .id(account.get().getId())
                        .username(changeInputDTO.getUsername())
                        .password(changeInputDTO.getPassword())
//                        .phone(account.get().getPhone())
                        .email(account.get().getEmail())
                        .updated_at(LocalDateTime.now())
                        .build();
                accountService.createAccount(accountInChange);
                String JWT = jwtTokenService.generateToken(accountInChange);
                dataOutput.setData(JWT).setSuccess(true).setMessage("Thay doi thanh cong");
                return ResponseEntity.ok().body(dataOutput);
            } else {
                dataOutput.setMessage("Thay doi thong tin that bai");
                return ResponseEntity.badRequest().body(dataOutput);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(DataOutput.builder().message("Gap loi server, kiem tra log").build());
        }
    }

    //Gui mat khau cho nguoi dung khi quen mat khau
    @PostMapping("/forgot-password")
    public ResponseEntity<DataOutput<Object>> forgotPassword(@Valid @RequestBody SendMailInputDTO request) {
        try {
            DataOutput<Object> dataOutput = DataOutput.builder().build();
            String email=request.getEmail();
            Optional<Account> account = accountService.getAccountByEmail(email);
            if (account.isPresent()) {
                String passwordRandom=generateRandomString(10);

                emailService.sendEmail(ecommerceEmail, email, email, passwordRandom);
                String hashPass = passwordEncoder.encode(passwordRandom);
                log.info("password hash: " + hashPass +" : "+passwordRandom);
                account.get().setPassword(hashPass);
                accountService.changePassword(account.get());
                dataOutput.setData(null).setSuccess(true).setMessage("Kiem tra mail cua ban");
                return ResponseEntity.ok(dataOutput);

            } else {
                dataOutput.setMessage("Khong tim thay tai khoan");
                return ResponseEntity.badRequest().body(dataOutput);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(DataOutput.builder().message("Gap loi server, kiem tra log").build());
        }

    }


    @PostMapping("/change-password")
    public ResponseEntity<DataOutput<Object>> changePassword(HttpServletRequest httpServletRequest,@RequestBody ChangePasswordDTO request) {
        try {
            String authHeader = httpServletRequest.getHeader("Authorization");
            String accountId = jwtTokenService.getAccountId(authHeader.substring(7))+"";
            log.info("account, {}" , accountId);
            DataOutput<Object> dataOutput = DataOutput.builder().build();
            Optional<Account> account = accountService.getAccountById(Integer.parseInt(accountId));
            if (account.isPresent()) {
                if(passwordEncoder.matches(request.getOldPassword(), account.get().getPassword())){
                    account.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
                    try {
                        accountService.changePassword(account.get());
                        dataOutput.setData(null).setSuccess(true).setMessage("Thay đổi mật khẩu thành công");
                        return ResponseEntity.ok(dataOutput);
                    }catch(Exception e){
                        log.error(e.getMessage());
                        dataOutput.setData(null).setSuccess(false).setMessage("Thay đổi mật khẩu thất bại");
                        return ResponseEntity.badRequest().body(dataOutput);
                    }

                }
                else {
                    dataOutput.setData(null).setSuccess(false).setMessage("Mật khẩu cũ khoog chính xác");
                    return ResponseEntity.badRequest().body(dataOutput);
                }

            } else {
                dataOutput.setMessage("Khong tim thay tai khoan");
                return ResponseEntity.badRequest().body(dataOutput);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(DataOutput.builder().message("Gap loi server, kiem tra log").build());
        }



    }


    @PostMapping("/change-email")
    public ResponseEntity<DataOutput<Object>> changeMail(HttpServletRequest httpServletRequest,@RequestBody ChangeMailDTO request) {
        try {

            String authHeader = httpServletRequest.getHeader("Authorization");
            String accountId = jwtTokenService.getAccountId(authHeader.substring(7))+"";
            log.info("account, {}" , accountId);
            DataOutput<Object> dataOutput = DataOutput.builder().build();
            if(request.getNewEmail()==null){
                dataOutput.setMessage("Mail không được để trống!");
                return ResponseEntity.badRequest().body(dataOutput);
            }
            Optional<Account> account = accountService.getAccountById(Integer.parseInt(accountId));
            if (account.isPresent()) {
                    account.get().setEmail(request.getNewEmail());
                    try {
                        userClient.changeEmail("Bearer " + authHeader.substring(7),request.getNewEmail());
                        accountService.changePassword(account.get());
                        dataOutput.setData(null).setSuccess(true).setMessage("Cập nhật mail thành công");
                        return ResponseEntity.ok(dataOutput);
                    }catch(Exception e){
                        log.error(e.getMessage());
                        dataOutput.setData(null).setSuccess(false).setMessage("Thay đổi mail thất bại");
                        return ResponseEntity.badRequest().body(dataOutput);
                    }


            } else {
                dataOutput.setMessage("Khong tim thay tai khoan");
                return ResponseEntity.badRequest().body(dataOutput);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(DataOutput.builder().message("Gap loi server, kiem tra log").build());
        }



    }





    //    Function random
    public  String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        return random.ints(length, 0, characters.length())
                .mapToObj(characters::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
