public class Omen extends Character {
    public Omen() { 
        super("Omen");
    }
    @Override
    public void showSkills() {
        System.out.println("1) Swift Cut (10 mana) - quick strike");
        System.out.println("2) Piercing Flurry (20 mana) - multiple hits");
        System.out.println("3) Silent Breaker (30 mana) - heavy blow");
    }
}