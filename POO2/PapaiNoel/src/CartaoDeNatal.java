public class CartaoDeNatal {
    String mensagem;

    boolean brasileiro;
    boolean americano;
    boolean mexicano;

    public static class Builder{
        String mensagem;

        // by default
        boolean brasileiro = false;
        boolean americano = false;
        boolean mexicano = false;

        public Builder(){}

        public Builder brasileiro(){
            this.brasileiro = true;
            mensagem = "Feliz natal brasileiro fdp!";
            return this;
        }

        public Builder americano(){
            this.americano = true;
            mensagem = "Feliz navidad!!";
            return this;
        }

        public Builder mexicano(){
            this.mexicano = true;
            return this;
        }

        public CartaoDeNatal build(){
            return new CartaoDeNatal(this);
        }
    }

    public CartaoDeNatal(Builder builder){
        this.mensagem = builder.mensagem;
        this.brasileiro = builder.brasileiro;
        this.americano = builder.americano;
        this.mexicano = builder.mexicano;
    }

    public String getMensagem() {
        return mensagem;
    }

    public static void main(String[] args) {
        CartaoDeNatal cartao = new CartaoDeNatal.Builder()
                                    .brasileiro()
                                    .build();

        System.out.println(cartao.getMensagem());
    }
}
