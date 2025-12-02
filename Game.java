import java.util.*;

public class Game {
    private Character player;
    private Scanner sc;
    private Random rand = new Random();
    private int winsSinceShop = 0;
    private int totalFights = 0;

    public Game(Character player, Scanner sc) {
        this.player = player; this.sc = sc;
    }

    public boolean travelToCastle() {
        // INTRO SA ATONG STORY
        storyPrint("-------------------------------");
        storyPrint("In the shadowed alleys of Gildenhall, a hooded figure approaches.");
        storyPrint("\"The King’s demise shall bring coin and fame,\" he whispers.");
        storyPrint("\"Will you strike the Crown or turn away?\"");

        int choice = -1;
        do {
            System.out.print("1 = Accept | 2 = Decline: ");
            choice = safeRead();
        } while (choice !=1 && choice !=2);

        if(choice==2) { storyPrint("You choose the quiet path. The shadows fade. GAME OVER."); return false; }

        storyPrint("You accept. Your dagger gleams under the moonlight. The road ahead awaits...");

       
        while((player.getWeaponDamage()<=5 || totalFights<18) && !player.isDead()) {
            storyPrint("-------------------------------");
            Enemy enemy;
            boolean isBoss = rand.nextInt(100)<10;
            if(isBoss) { 
                enemy = createBoss(); 
                storyPrint(enemy.getName());
                storyPrint(getBossDialogue(enemy.getName()));
            }
            else { enemy = createEnemy(); storyPrint(enemy.getName()); }

            boolean survived = fightEnemy(enemy);
            if(!survived) return false;

           
            System.out.print("Travelling through the lands");
            for(int i=0; i<3; i++) {
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
                System.out.print("..");
            }
            System.out.println();
            try { Thread.sleep(rand.nextInt(2000) + 1000); } catch (InterruptedException e) {} 

            winsSinceShop++;
            totalFights++;

            
            if(totalFights % 3 == 0) {
                storyPrint(randomEncouragement());
            }

            if(winsSinceShop >= 2) {
                storyPrint(randomTravelMessage());
                System.out.print("A travelling merchant appears nearby. Open Shop? 1=Yes 2=No: ");
                int shopChoice = safeRead();
                if(shopChoice==1) { Shop.openShop(player, sc); }
                else if(shopChoice!=1 && shopChoice!=2) { storyPrint("The traveller moves on! You miss the chance to trade."); }
                winsSinceShop=0;
            }
        }

        storyPrint("Your blade has been tempered. You march onward to the castle gates.");
        return true;
    }

    public boolean assaultCastle() {
        storyPrint("-------------------------------");
        storyPrint("At last, the towering gates of the King’s castle loom. Torches flicker along stone walls.");
        for(int i=1;i<=3;i++) {
            Enemy knight = new Enemy("A Royal Knight charges!", 60, 8, 14, 20,30,false);
            storyPrint("\nKnight " + i + " approaches!");
            if(!fightEnemy(knight)) return false;
        }

        storyPrint("\nThe King awaits atop the dais. His eyes cold, his armor shining.");
        Enemy king = new Enemy("The King stands before you.",1,15,25,150,200,true);
        boolean kingDefeated = fightEnemy(king);
        if(!kingDefeated) return false;

        storyPrint("\nThe King collapses silently. The court murmurs, both fearful and awed.");
        storyPrint("Your name will echo in whispers and songs. You have completed the assassination!");
        player.showStats();
        return true;
    }

    private boolean fightEnemy(Enemy enemy) {
        int enemyHp = enemy.getHp();
        while(enemyHp>0) {
            storyPrint("-------------------------------");
            storyPrint("Your Turn: HP:"+player.getHp()+" Mana:"+player.getMana()+" Gold:"+player.getCurrency());
            System.out.println("0) Normal Attack (0 mana)");
            player.showSkills();
            System.out.println("4) Attempt to Run");
            System.out.print("Choice: ");
            int action = safeRead();
            switch(action) {
                case 0:
                    int baseDmg = rand.nextInt(4) + 5; 
                    int dmg = player.generateDamage(baseDmg);
                    enemyHp-=dmg;
                    storyPrint("You strike and deal "+dmg+" damage!");
                    break;
                case 1:
                    if(player.spendMana(10)) { dmg=player.generateDamage(20); enemyHp-=dmg; storyPrint("Skill 1 hits for "+dmg+" damage!"); }
                    else { storyPrint("Not enough mana!"); continue; }
                    break;
                case 2:
                    if(player.spendMana(20)) { dmg=player.generateDamage(30); enemyHp-=dmg; storyPrint("Skill 2 hits for "+dmg+" damage!"); }
                    else { storyPrint("Not enough mana!"); continue; }
                    break;
                case 3:
                    if(player.spendMana(30)) { dmg=player.generateDamage(45); enemyHp-=dmg; storyPrint("Skill 3 hits for "+dmg+" damage!"); }
                    else { storyPrint("Not enough mana!"); continue; }
                    break;
                case 4:
                    boolean run = rand.nextInt(100)<40;
                    if(run) { storyPrint("You slip away successfully!"); storyPrint(randomTravelMessage()); return true; }
                    else { storyPrint("Failed to run!"); }
                    break;
                default: storyPrint("Hesitation wastes time!"); break;
            }

            if(enemyHp<=0) break;
            int edmg = enemy.dealDamage(rand);
            player.takeDamage(edmg);
            storyPrint("Enemy strikes for "+edmg+" damage!");
            try { Thread.sleep(rand.nextInt(2000) + 1000); } catch (InterruptedException e) {} 
            if(player.isDead()) { storyPrint("You fall in battle. GAME OVER."); return false; }
        }

        int gold = enemy.getReward(rand);
        player.addGold(gold);
        storyPrint("Victory! Gained "+gold+" gold.");
        return true;
    }

  
    private Enemy createEnemy() {
        int hp = rand.nextInt(21)+30;
        String[] encounters = {
            "A goblin skulks in the shadows.",
            "Bandits emerge from the trees.",
            "A lone wolf growls nearby.",
            "An orc raider blocks your path.",
            "A wild boar charges out of nowhere!",
            "A giant spider descends from the branches.",
            "A sly thief attempts to ambush you."
        };
        return new Enemy(encounters[rand.nextInt(encounters.length)],hp,7,12,10,15,false); 
    }

    private Enemy createBoss() {
        int hp = rand.nextInt(26) + 65; 
        String[] bosses = {
            "A hulking bandit captain snarls.",
            "A forest troll stomps into the clearing.",
            "An orc war-chief glares at you.",
            "A massive direwolf bars your path."
        };
        return new Enemy(bosses[rand.nextInt(bosses.length)], hp, 15, 20, 30, 50, true); 
    }

    private String getBossDialogue(String bossName) {
        if (bossName.contains("bandit captain")) {
            return "Bandit Captain growls: 'Your gold is mine now!'";
        } else if (bossName.contains("forest troll")) {
            return "Forest Troll bellows: 'Intruder! Crush you!'";
        } else if (bossName.contains("orc war-chief")) {
            return "Orc War-Chief snarls: 'You dare challenge me?'";
        } else if (bossName.contains("direwolf")) {
            return "Direwolf howls: 'Flee or be devoured!'";
        }
        return "The boss eyes you menacingly.";
    }

    private void storyPrint(String text) { Main.typePrint(text); }
    private int safeRead() {
        while(!sc.hasNextInt()) { sc.next(); System.out.print("Enter number: "); }
        return sc.nextInt();
    }

    private String randomTravelMessage() {
        String[] msgs = {
            "You stride through misty woods, every shadow a potential foe...",
            "Moonlight glints on your blade while distant howls echo through the valley...",
            "The road winds endlessly, yet your resolve pushes onward...",
            "Fog curls around your feet as you advance silently...",
            "Birds scatter in alarm as you make your way forward..."
        };
        return msgs[rand.nextInt(msgs.length)];
    }

    private String randomEncouragement() {
        String[] msgs = {
            "An old traveler nods: 'Keep your eyes on the prize, young warrior.'",
            "You hear a bard sing of heroes who never gave up.",
            "A merchant smiles: 'Every step brings you closer to glory.'",
            "A child waves from a village: 'Do not falter, brave one!'",
            "The wind whispers: 'Courage is forged in the trials you face.'"
        };
        return msgs[rand.nextInt(msgs.length)];
    }
}