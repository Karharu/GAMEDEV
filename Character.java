public abstract class Character {
    protected String name;
    protected int hp = 100;
    protected int mana = 40;
    protected int currency = 0;
    protected int weaponDamage = 5; 
    protected final int STARTER_WEAPON_DAMAGE = 5;

    public Character(String name) { this.name = name; }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMana() { return mana; }
    public int getCurrency() { return currency; }
    public int getWeaponDamage() { return weaponDamage; }

    public void showStats() {
        System.out.println("\n=== " + name + " Stats ===");
        System.out.println("HP: " + hp + " | Mana: " + mana + " | Gold: " + currency + " | Weapon Bonus: " + weaponDamage);
    }

    public int generateDamage(int base) { return base + weaponDamage; }

    public boolean spendMana(int cost) {
        if (mana >= cost) { mana -= cost; return true; }
        return false;
    }

    public void healHp(int amount) { hp = Math.min(100, hp + amount); }
    public void restoreMana(int amount) { mana = Math.min(100, mana + amount); }
    public void addGold(int g) { currency += g; }
    public boolean spendGold(int cost) {
        if (currency >= cost) { currency -= cost; return true; } else return false;
    }
    public void takeDamage(int dmg) { hp -= dmg; if (hp < 0) hp = 0; }
    public boolean isDead() { return hp <= 0; }
    public boolean hasBetterWeaponThanStarter() { return weaponDamage > STARTER_WEAPON_DAMAGE; }
    public abstract void showSkills();
}