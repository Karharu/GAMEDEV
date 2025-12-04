public abstract class Character {
    protected String name;
    protected int hp;
    protected int maxHp = 100;
    protected int mana;
    protected int maxMana = 40;
    protected int currency = 0;
    protected int weaponDamage = 5; 
    protected final int STARTER_WEAPON_DAMAGE = 5;

    public Character(String name) { 
        this.name = name;
        this.hp = maxHp;
        this.mana = maxMana;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getMana() { return mana; }
    public int getMaxMana() { return maxMana; }
    public int getCurrency() { return currency; }
    public int getWeaponDamage() { return weaponDamage; }

    public void showStats() {
        System.out.println("\n==================================");
        System.out.println("--- HERO PROFILE: " + this.name.toUpperCase() + " ---");
        System.out.println("==================================");
        System.out.println(" | HEALTH: " + String.format("%-3d", this.hp) + " / 100");
        System.out.println(" | MANA:   " + String.format("%-3d", this.mana) + " / 40 (Max)");
        System.out.println(" | GOLD:   " + this.currency + " G");
        System.out.println(" | WEAPON BONUS: +" + this.weaponDamage + " Damage");
        System.out.println("==================================");
    }

    public int generateDamage(int base) { return base + weaponDamage; }

    public boolean spendMana(int cost) {
        if (mana >= cost) { mana -= cost; return true; }
        return false;
    }

    public void healHp(int amount) { hp = Math.min(maxHp, hp + amount); }
    public void restoreMana(int amount) { mana = Math.min(maxMana, mana + amount); }
    public void addGold(int g) { currency += g; }
    public boolean spendGold(int cost) {
        if (currency >= cost) { currency -= cost; return true; } else return false;
    }
    public void takeDamage(int dmg) { hp -= dmg; if (hp < 0) hp = 0; }
    public boolean isDead() { return hp <= 0; }
    public boolean hasBetterWeaponThanStarter() { return weaponDamage > STARTER_WEAPON_DAMAGE; }
    public abstract void showSkills();
}