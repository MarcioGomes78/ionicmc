package com.marcio.ionicmc.services.validation;

import java.util.ArrayList;
import java.util.List;

import com.marcio.ionicmc.domain.enums.TipoCliente;
import com.marcio.ionicmc.dto.ClienteNewDTO;
import com.marcio.ionicmc.resources.exception.FieldMessage;
import com.marcio.ionicmc.services.validation.utils.BR;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        // criar lista de mensagens de erro
        List<FieldMessage> list = new ArrayList<>();
        // incluir validação de cpf e cnpj
        if (objDto.getTipo().equals(TipoCliente.PESSOA_FISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        } else if (objDto.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }
        
        // incluir validação de email
        for (FieldMessage e: list){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }

}
