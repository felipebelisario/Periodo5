public class App {
    P p1;

    public App(AbstractFactory factory){
        p1 = factory.create();

        p1.f();
    }
}
