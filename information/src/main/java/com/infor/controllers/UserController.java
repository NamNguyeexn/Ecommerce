package com.infor.controllers;

import com.common.DTO.ResponseObject;
import com.common.utils.ConvertJson;
import com.infor.DTO.*;
import com.infor.filters.JwtTokenService;
import com.infor.models.User;
import com.infor.services.IRedisInfoService;
import com.infor.services.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api/info/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private ConvertJson convertJson;
    @Autowired
    private IRedisInfoService redisInfoService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
//    @GetMapping("/info")
//    public ResponseEntity<ResponseObject<User>> getUserInfo(HttpServletRequest request) {
//        try {
//            String JWT = jwtTokenService.getToken(request);
//            int accountId = jwtTokenService.getAccountId(JWT);
//            Optional<User> user = userService.getUserByAccountId(accountId);
//            if(user.isPresent()) {
//                return ResponseEntity.ok().body(
//                        ResponseObject.<User>builder()
//                                .data(user.get())
//                                .isSuccess(true)
//                                .message("Lay thong tin thanh cong")
//                                .build()
//                );
//            } else return ResponseEntity.badRequest().body(
//                    ResponseObject.<User>builder()
//                            .message("Khong tim thay user")
//                            .build()
//            );
//        } catch (Exception e){
//            log.error(e.getMessage());
//            return ResponseEntity.badRequest().body(
//                    ResponseObject.<User>builder()
//                            .message("Gap loi server")
//                            .build()
//            );
//        }
//
//    }
    @PostMapping("/create")
    public ResponseEntity<ResponseObject<Object>> createInfo(@RequestBody ReqUserDTO request) {
        try {

            ResponseObject<Object> dataOutput = ResponseObject.builder().build();
//            Optional<User> user = userService.getUserByAccountId(Integer.parseInt(accountId));
            User user= User.builder()
                    .account_id(Integer.parseInt(request.getAccount_id()))
                    .is_active(true)
                    .build();
            if(request.getFull_name()!=null){
                user.setFull_name(request.getFull_name());
            }
            if(request.getPhone()!=null){
                user.setPhone(request.getPhone());
            }
            if(request.getUser_name()!=null){
                user.setUser_name(request.getUser_name());
            }
            if(request.getEmail()!=null){
                user.setEmail(request.getEmail());
            }
            user.setAccount_id(Integer.parseInt(request.getAccount_id()));
            User newUser = userService.createUser(user);
            dataOutput.setData(null);
            dataOutput.setSuccess(true);
            dataOutput.setMessage("Tao user thanh cong");
            return ResponseEntity.ok(dataOutput);

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(ResponseObject.builder().message("Gap loi server, kiem tra log").build());
        }

    }
    @PostMapping("/change/email")
    public ResponseEntity<ResponseObject<User>> changeUserEmail(
            HttpServletRequest request,
            @RequestBody String newEmail
    ) {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            String accountId = context.getAuthentication().getName();
            Optional<User> user = userService.getUserByAccountId(Integer.parseInt(accountId));
            if (user.isPresent()) {
                user.get().setEmail(newEmail);
                User user1 = userService.createUser(user.get());
                return ResponseEntity.ok().body(
                        ResponseObject.<User>builder()
                                .message("Thay doi thong tin thanh cong")
                                .isSuccess(true)
                                .data(user1)
                                .build()
                );
            } else {
                return ResponseEntity.badRequest().body(
                        ResponseObject.<User>builder()
                                .message("Khong tim thay user")
                                .build()
                );
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ResponseObject.<User>builder()
                            .message("Gap loi server")
                            .build()
            );
        }
    }
    @PostMapping("/change/info")
    public ResponseEntity<ResponseObject<User>> changeUserInfo(
            HttpServletRequest request,
            @RequestBody InfoInputDTO input
    ) {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            String accountId = context.getAuthentication().getName();
            Optional<User> user = userService.getUserByAccountId(Integer.parseInt(accountId));
            if (user.isPresent()) {
                user.get().setFull_name(input.getFull_name());
                user.get().setPhone(input.getPhone());
                User user1 = userService.createUser(user.get());
                return ResponseEntity.ok().body(
                        ResponseObject.<User>builder()
                                .message("Thay doi thong tin thanh cong")
                                .isSuccess(true)
                                .data(user1)
                                .build()
                );
            } else {
                return ResponseEntity.badRequest().body(
                        ResponseObject.<User>builder()
                                .message("Khong tim thay user")
                                .build()
                );
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ResponseObject.<User>builder()
                            .message("Gap loi server")
                            .build()
            );
        }
    }
    @GetMapping("/info")
    public ResponseEntity<ResponseObject<User>> getUserInfo(HttpServletRequest request) {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            String accountId = context.getAuthentication().getName();
            log.info("account, {}" , accountId);
            Optional<User> user = userService.getUserByAccountId(Integer.parseInt(accountId));
            if(user.isPresent()) {
                redisInfoService.pushUserToRedis(user.get());
                return ResponseEntity.ok().body(
                        ResponseObject.<User>builder()
                                .data(user.get())
                                .isSuccess(true)
                                .message("Lay thong tin thanh cong")
                                .build()
                );
            } else return ResponseEntity.badRequest().body(
                    ResponseObject.<User>builder()
                            .message("Khong tim thay user")
                            .build()
            );
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(
                    ResponseObject.<User>builder()
                            .message("Gap loi server")
                            .build()
            );
        }

    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject<UserListDTO>> getAllUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String key,
            @RequestParam boolean isActive) {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            String accountId = context.getAuthentication().getName();
            log.info("account, {}" , accountId);
            if(!"1".equals(accountId)) {
                return ResponseEntity.badRequest().body(
                        ResponseObject.<UserListDTO>builder()
                                .data(null)
                                .message("Bạn không có quyền xem, vui lòng đăng nhập tài khoản ADMIN")
                                .build()
                );
            }
            UserListDTO users = userService.getAllUser(page,limit,key,isActive);
                return ResponseEntity.ok().body(
                        ResponseObject.<UserListDTO>builder()
                                .data(users)
                                .isSuccess(true)
                                .message("Lay thong tin thanh cong")
                                .build()
                );

        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(
                    ResponseObject.<UserListDTO>builder()
                            .message("Gap loi server")
                            .build()
            );
        }

    }

    @PostMapping("/change-password")
    public ResponseEntity<ResponseObject<Object>> changePassword(HttpServletRequest httpServletRequest,@RequestBody ChangeUserDTO request) {
        try {
            String authHeader = httpServletRequest.getHeader("Authorization");
            String accountId = jwtTokenService.getAccountId(authHeader.substring(7))+"";
            log.info("account, {}" , accountId);
            ResponseObject<Object> dataOutput = ResponseObject.builder().build();
            Optional<User> user = userService.getUserByAccountId(Integer.parseInt(accountId));
            if (user.isPresent()) {
                user.get().setPhone(request.getPhone());
                user.get().setFull_name(request.getFull_name());

                    try {
                        userService.changeUser(user.get());
                        dataOutput.setData(null);
                        dataOutput.setSuccess(true);
                        dataOutput.setMessage("Cập nhật thông tin thành công");
                        return ResponseEntity.ok(dataOutput);
                    }catch(Exception e){
                        log.error(e.getMessage());
                        dataOutput.setData(null);
                        dataOutput.setSuccess(true);
                        dataOutput.setMessage("Cập nhật thông tin thất bại");
                        return ResponseEntity.badRequest().body(dataOutput);
                    }



            } else {
                dataOutput.setMessage("Khong tim thay tai khoan");
                return ResponseEntity.badRequest().body(dataOutput);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(ResponseObject.builder().message("Gap loi server, kiem tra log").build());
        }

    }

    @PostMapping("/change/avt")
    public ResponseEntity<ResponseObject<Object>> changeAvatarInfo(
//            @RequestParam("file") MultipartFile file
            HttpServletRequest httpServletRequest,
            @RequestBody ChangeAvtDTO request
            ) {
        try {
//            = file.getOriginalFilename();
//           String avtUrl =
            String authHeader = httpServletRequest.getHeader("Authorization");
            String accountId = jwtTokenService.getAccountId(authHeader.substring(7))+"";
            log.info("account, {}" , accountId);
            ResponseObject<Object> dataOutput = ResponseObject.builder().build();
            Optional<User> user = userService.getUserByAccountId(Integer.parseInt(accountId));
            if (user.isPresent()) {

                user.get().setAvt(request.getUrlAvatar());

                try {
                    userService.changeUser(user.get());
                    dataOutput.setData(null);
                    dataOutput.setSuccess(true);
                    dataOutput.setMessage("Cập nhật thông tin thành công");
                    return ResponseEntity.ok(dataOutput);
                }catch(Exception e){
                    log.error(e.getMessage());
                    dataOutput.setData(null);
                    dataOutput.setSuccess(true);
                    dataOutput.setMessage("Cập nhật thông tin thất bại");
                    return ResponseEntity.badRequest().body(dataOutput);
                }



            } else {
                dataOutput.setMessage("Khong tim thay tai khoan");
                return ResponseEntity.badRequest().body(dataOutput);
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ResponseObject.<Object>builder()
                            .message("Gap loi server")
                            .build()
            );
        }
    }
}
