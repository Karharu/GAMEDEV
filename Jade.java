public class Jade extends Character {
    public Jade() { 
        super("Jade"); 
    }
    @Override
    public void showSkills() {
        System.out.println("1) Driving Blow (10 mana) - strong strike");
        System.out.println("2) Crushing Advance (20 mana) - crushing hit");
        System.out.println("3) Iron Judgment (30 mana) - devastating strike");
    }
}
