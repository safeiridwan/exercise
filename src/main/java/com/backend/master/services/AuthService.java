package com.backend.master.services;

import com.backend.master.domain.dto.request.AuthRequest;
import com.backend.master.domain.dto.response.ResponseAPI;
import com.backend.master.domain.models.Users;
import com.backend.master.repository.UserRepository;
import com.backend.master.utils.JwtUtil;
import com.backend.master.utils.NullEmptyChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import static java.util.Collections.emptyList;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository applicationUserRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public ResponseAPI signUp(@RequestBody AuthRequest req) {
        ResponseAPI response = new ResponseAPI();
        try {
            Users user = applicationUserRepository.findByUsername(req.getUsername());
            if (NullEmptyChecker.isNullOrEmpty(user)) {
                Users saveUser = new Users();
                saveUser.setUsername(req.getUsername());
                saveUser.setPassword(bCryptPasswordEncoder.encode(req.getPassword()));
                applicationUserRepository.save(saveUser);

                response.setSuccess(true);
                response.setMessages("Registration Success!");
                response.setResult(null);
                response.setAdditionalEntity(null);
            } else {
                response.setSuccess(false);
                response.setMessages("Username Already Exist!");
                response.setResult(null);
                response.setAdditionalEntity(null);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            response.setSuccess(false);
            response.setMessages("Internal Server Error");
            response.setResult(null);
            response.setAdditionalEntity(e.toString());
        }

        return response;
    }

    public ResponseEntity<ResponseAPI> signIn(@RequestBody AuthRequest req) {
        ResponseAPI response;
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
            response = new ResponseAPI(
                    true,
                    "Login Success!",
                    null,
                    null);
        } catch (BadCredentialsException notFoundException) {
            response = new ResponseAPI(false, HttpStatus.UNAUTHORIZED.toString(),
                    "Username or password invalid", null);
        } catch (Exception e) {
            response = new ResponseAPI(false, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    "Error when trying to obtain user", null);
        }

        responseHeaders.set("Authorization", jwtUtil.generateToken(req.getUsername()));
        return ResponseEntity.ok().headers(responseHeaders).body(response);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = applicationUserRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getUsername(), user.getPassword(), emptyList());
    }

}
