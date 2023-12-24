package com.devneutro.investiments.service;

import com.devneutro.investiments.controller.CreateUserDto;
import com.devneutro.investiments.controller.UpdateUserDto;
import com.devneutro.investiments.entity.User;
import com.devneutro.investiments.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto){

         //DTO - Entity
        var entity = new User(
                UUID.randomUUID(),
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);

        var userSaved = userRepository.save(entity);

        return userSaved.getUserID();
    }

    public Optional<User> getUserById(String userId){

        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers (){
        return userRepository.findAll();
    }

    public void deleteById(String userId){

        var id = UUID.fromString(userId);
        var userExist = userRepository.existsById(id);

        if (userExist){
            userRepository.deleteById(id);
        }
    }

    public void updateUserById(String userId,
                               UpdateUserDto updateUserDto){
        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()){
            var user = userEntity.get();

            if(updateUserDto.username() != null){
                user.setUsername(updateUserDto.username());
            }

            if(updateUserDto.password() != null){
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }


        userRepository.existsById(id);
    }
}
