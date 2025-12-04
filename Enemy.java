import java.util.*;
public class Enemy {
    private String name;
    private int hp;
    private int maxHp;
    private int minReward, maxReward;
    private int dmgMin, dmgMax;
    private boolean isBoss;

    public Enemy(String name, int hp, int dmgMin, int dmgMax, int minReward, int maxReward, boolean isBoss) {
        this.name = name; this.hp = hp; this.maxHp = hp;
        this.dmgMin = dmgMin; this.dmgMax = dmgMax;
        this.minReward = minReward; this.maxReward = maxReward;
        this.isBoss = isBoss;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public void setHp(int v){ hp = v; }
    public boolean isBoss() { return isBoss; }
    public int dealDamage(Random r) { return r.nextInt(dmgMax - dmgMin +1)+dmgMin; }
    public int getReward(Random r) { return r.nextInt(maxReward - minReward +1)+minReward; }
}
