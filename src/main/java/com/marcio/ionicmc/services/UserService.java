package com.marcio.ionicmc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.marcio.ionicmc.security.UserSS;

public class UserService {

    public static UserSS authenticated() {
        try {
            // Retorna o usuário autenticado logado no momento
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
