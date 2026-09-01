package com.marcio.ionicmc.domain.enums;

//representa o estado do pagamento
public enum EstadoPagamento {
    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private Integer cod;
    private String descricao;

    private EstadoPagamento(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public Integer getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    //metodo para converter um inteiro em EstadoPagamento
    public static EstadoPagamento toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        //percorre todos os valores do enum EstadoPagamento
        for (EstadoPagamento x : EstadoPagamento.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }
        //se o codigo não for encontrado, lança uma exceção
        throw new IllegalArgumentException("Invalid code: " + cod);
    }
}
