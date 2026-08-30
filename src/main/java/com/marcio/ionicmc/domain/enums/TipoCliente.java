package com.marcio.ionicmc.domain.enums;

public enum TipoCliente {
    //criando os tipos de cliente
    PESSOA_FISICA(1, "Pessoa Física"),
    PESSOA_JURIDICA(2, "Pessoa Jurídica");

    //colocando como private e final
    private int cod;
    private String descricao;

    //colocando como private
    private TipoCliente(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoCliente toEnum(Integer cod) {
        //passando o código para retornar o tipo do cliente
        if(cod == null){
            return null;
        }
        //passando todos os valores do enum
        for (TipoCliente x : TipoCliente.values()){
            //passando os valores
            if(x.getCod() == cod){
                return x;
            }
        }
        //lançando exceção se o código for inválido
        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
