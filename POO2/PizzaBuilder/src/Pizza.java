public class Pizza {
    public int tamanho;

    public boolean queijo;
    public boolean bacon;
    public boolean calabresa;

    public static class Builder {
        public int tamanho;

        // By default
        public boolean queijo = false;
        public boolean bacon = false;
        public boolean calabresa = false;

        public Builder(int tamanho){
            this.tamanho = tamanho;
        }

        public Builder queijo(){
            queijo = true;
            return this;
        }

        public Builder bacon(){
            bacon = true;
            return this;
        }

        public Builder calabresa(){
            calabresa = true;
            return this;
        }

        public Pizza build(){
            return new Pizza(this);
        }
    }

    public Pizza(Builder builder){
        tamanho = builder.tamanho;
        queijo = builder.queijo;
        bacon = builder.bacon;
        calabresa = builder.calabresa;

        System.out.println(tamanho);
        System.out.println(queijo);
        System.out.println(bacon);
        System.out.println(calabresa);

    }

    public static void main(String args[]){
        Pizza pizza = new Pizza.Builder(10)
                                    .queijo()
                                    .calabresa()
                                    .build();
    }
}
