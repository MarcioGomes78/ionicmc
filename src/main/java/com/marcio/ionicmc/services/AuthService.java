package com.marcio.ionicmc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.marcio.ionicmc.domain.Cliente;
import com.marcio.ionicmc.repositories.ClienteRepository;
import com.marcio.ionicmc.services.exception.ObjectNotFoundException;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    private Random random = new Random();

    public void sendNewPasswordEmail(String email) {

        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new ObjectNotFoundException("Email não cadastrado!");
        }

        String newPass = newPassword();
        cliente.setSenha(bCryptPasswordEncoder.encode(newPass));
        clienteRepository.save(cliente);
        emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for(int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = random.nextInt(3);
        if(opt == 0) {
            return (char) (random.nextInt(26) + 97);// Gera uma letra minúscula
        } else if(opt == 1) {
            return (char) (random.nextInt(26) + 65);// Gera uma letra maiúscula
        } else {
            return (char) (random.nextInt(10) + 48);// Gera um número como char
        }
    }
}
