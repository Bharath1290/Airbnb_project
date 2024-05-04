package com.airbnb_2.service;


import com.airbnb_2.dto.LoginDto;
import com.airbnb_2.dto.PropertyUserDto;
import com.airbnb_2.entity.PropertyUser;
import com.airbnb_2.repository.PropertyUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private PropertyUserRepository userRepository;
    private JWTService jwtService;

    public UserService(PropertyUserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
    public PropertyUser addUser(PropertyUserDto propertyUserDto){
        PropertyUser user =new PropertyUser();
        user.setFirstName(propertyUserDto.getFirstName());
        user.setLastName(propertyUserDto.getLastName());
        user.setUserName(propertyUserDto.getUserName());
        user.setEmail(propertyUserDto.getEmail());
        user.setPassword(BCrypt.hashpw(propertyUserDto.getPassword(),BCrypt.gensalt(5)));
        user.setUserRole(propertyUserDto.getUserRole());
        PropertyUser savedUser = userRepository.save(user);
        return savedUser;
    }

    public String verifyLogin(LoginDto loginDto) {
        Optional<PropertyUser> opUser = userRepository.findByUserName(loginDto.getUserName());
        if(opUser.isPresent()){
            PropertyUser propertyUser = opUser.get();
            if(BCrypt.checkpw(loginDto.getPassword(),propertyUser.getPassword())){
                return jwtService.generateToken(propertyUser);
            }
        }
        return null;
    }
}
