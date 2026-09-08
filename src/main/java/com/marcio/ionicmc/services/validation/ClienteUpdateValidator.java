package com.marcio.ionicmc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.marcio.ionicmc.domain.Cliente;
import com.marcio.ionicmc.dto.ClienteDTO;
import com.marcio.ionicmc.repositories.ClienteRepository;
import com.marcio.ionicmc.resources.exception.FieldMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * ClienteUpdateValidator
 */
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO>{

    @Autowired 
    private HttpServletRequest request;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteUpdate ann) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

        //obtem o id do cliente da() requisicao
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();
        // buscar o cliente no banco de dados pelo email
        Cliente cli = clienteRepository.findByEmail(objDto.getEmail());
        // verificar se o cliente existe e se o id é diferente do id da requisicao
        if (cli != null && !cli.getId().equals(uriId)) {
            list.add(new FieldMessage("email", "Email já cadastrado"));
        }
        for (FieldMessage e: list){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }

}
