package com.marcio.ionicmc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.marcio.ionicmc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

    public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instantDoPedido){
        //implementar aqui a lógica de preenchimento do pagamento com boleto
        //usar o mock service de boleto

        Calendar cal = Calendar.getInstance();
        cal.setTime(instantDoPedido);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        pagto.setDataVencimento(cal.getTime());
    }
}
