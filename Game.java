import java.util.*;

public class Game {
    private Character player;
    private Scanner sc;
    private Random rand = new Random();
    private int winsSinceShop = 0;
    private int totalFights = 0;
    private GameUI ui = new GameUI();


    public Game(Character player, Scanner sc) {
        this.player = player; this.sc = sc;
    }

    public boolean travelToCastle() {
        // INTRO SA ATONG STORY
        storyPrint("------------------------------------");
        storyPrint("In the shadowed alleys of Lapu-Lapu, a hooded figure approaches.");
        storyPrint("He stops beside you, voice low and razor-thin.");
        storyPrint("'The King's demise…' he whispers, glancing over his shoulder as if the walls are listening.");
        storyPrint("'It shall bring coin, power.. and a name that echoes through every town'");
        storyPrint("His hood tilts up just enough for you to see the glint of a smirk.");
        storyPrint("'But such glory is bought with blood.' he says.");
        storyPrint("'Will you strike the Crown and claim your fate...");
        storyPrint("or turn away and pretend you never heard my offer?'");
        storyPrint("His hand rests lightly on the dagger at his belt, waiting and testing.");
        storyPrint("The alley grows colder as his words hang between you like a blade.");
        System.out.println("------------------------------------");

        int choice = -1;
        do {
            System.out.print("1 = Accept | 2 = Decline: ");
            choice = safeRead();
        } while (choice !=1 && choice !=2);

        if(choice==2) { storyPrint("You choose the quiet path. The shadows fade. GAME OVER."); return false; }

        storyPrint("You accept. Your dagger gleams under the moonlight.");
        storyPrint("'I have to do this... for them.. ");
        storyPrint("for my family.. I can't fail them now. Not when they are counting on me.");
        storyPrint("The wind rustles through the trees, as if echoing your resolve.");
        storyPrint("Every footstep feels heavier, yet stronger, carrying the weight of hope and duty.");
        storyPrint("'No turning back', you whisper to yourself.");
        storyPrint("'The night is dark, but I will be the light.'");
        storyPrint("The road ahead awaits...");

       
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
        storyPrint("'My blade is ready. Whatever waits beyond those gates… I will face it.");
        storyPrint("The castle gates won't hold me back.'");
        return true;
    }

    public boolean assaultCastle() {
        storyPrint("-------------------------------");
        storyPrint("At last, the towering gates of the King’s castle loom. Torches flicker along stone walls.");
        for(int i=1;i<=3;i++) {
            Enemy knight = new Enemy("A Royal Knight charges!", 60, 8, 14, 20,30,false);
            if(i == 1) {
                storyPrint("\nA Knight approaches you!");
            } else if(i == 2) {
                storyPrint("\nAnother Knight approaches and attacks you!");
            } else {
                storyPrint("\nA Dark Knight stares at you menacely and strikes!");
            }
            if(!fightEnemy(knight)) return false;
        }

        storyPrint("\nThe King awaits atop the dais. His eyes cold, his armor shining.");
        Enemy king = new Enemy("The King stands before you.",1,15,25,150,200,true);
        boolean kingDefeated = fightEnemy(king);
        if(!kingDefeated) return false;

        storyPrint("\nThe King collapses silently.");
        storyPrint("The rogue steps back, breathing heavily.");
        storyPrint("The King's blood staining the cold stone floor.");
        storyPrint("Silence fills the grand hall.");
        storyPrint("A voice, smooth and familiar, breaks the quiet");
        storyPrint("'Well done… you did exactly as I asked.'");
        storyPrint("The rogue spins around, eyes narrowing.");
        storyPrint("From the shadows, the figure of the King's personal wizard emerge.");
        storyPrint("robes flowing, a faint smile playing on his lips.");
        storyPrint("'You…' the rogue says, anger and disbelief flashing in his eyes.");
        storyPrint("'You… sent me… to do this?'");
        storyPrint("The wizard steps closer, calm and composed.");
        storyPrint("'Yes. I served him for years… but his reign… it was suffocating the kingdom.'");
        storyPrint("I needed someone outside his circle. Someone who could act without hesitation.");
        storyPrint("That someone was you.'");
        storyPrint("The rogue's dagger trembles in his hand. 'You used me… you manipulated me!'");
        storyPrint("'I gave you the choice.' The wizard says.");
        storyPrint("The rogue swallows. 'and the Kingdom?'");
        storyPrint("'We take what remains and forge it anew… without mercy,' the wizard replies.");
        storyPrint("And in that moment, he realized that the hand that changed the kingdom");
        storyPrint("was never seen... but its shadow would haunt history forever.");
        player.showStats();
        return true;
    }

    private boolean fightEnemy(Enemy enemy) {
        int enemyHp = enemy.getHp();
        while(enemyHp>0) {
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.printf("║               HERO: %-20s   ║\n", player.getName());
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.printf("║ HP:   %-35s       ║\n", 
                ui.getHealthBar(player.getHp(), player.getMaxHp(), 20));
            System.out.printf("║ Mana: %-35s         ║\n", 
                ui.getManaBar(player.getMana(), player.getMaxMana(), 20));
            System.out.printf("║ Gold: %-35d  ║\n", player.getCurrency());
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║                   ENEMY                    ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.printf("║ HP:   %-35s         ║\n", 
                ui.getHealthBar(enemyHp, enemy.getMaxHp(), 20), enemyHp);
            System.out.println("╚════════════════════════════════════════════╝");

            System.out.println();
            System.out.println(" Available Actions:");
            System.out.println("──────────────────────────────────────────────");
            System.out.println("0) Normal Attack (0 mana)");
            player.showSkills();
            System.out.println("4) Attempt to Run");
            System.out.println();
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
            "A sly thief attempts to ambush you.",
            "Two skeletons rattle toward you from the ruins.",
            "A goblin archer takes aim from a branch above.",
            "A rogue mage mutters incantations, ready to strike.",
            "A swarm of bats erupts from a nearby cave.",
            "A skeletal knight clinks forward, armor rattling.",
            "A hungry hyena pack snarls at the edge of the clearing.",
            "A shadowy figure darts between the trees."
        };
        return new Enemy(encounters[rand.nextInt(encounters.length)],hp,7,12,10,15,false); 
    }

    private Enemy createBoss() {
        int hp = rand.nextInt(26) + 65; 
        String[] bosses = {
            "BOSS ENCOUNTERED: A hulking bandit captain named Tamago snarls.",
            "BOSS APPEARED: A forest troll known as Llanos stomps into the clearing.",
            "BOSS ENCOUNTERED: An orc war-chief glares at you. 'I am Auralim and I will crush you!'",
            "BOSS APPEARED: A massive direwolf with immense aura bars your path.",
            "BOSS ENCOUNTERED: A necromancer raises skeletons from the soil."
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
            "Birds scatter in alarm as you make your way forward...",
            "The wind whispers through the trees, carrying secrets you cannot yet hear...",
            "Your footsteps crunch on frost-covered leaves, each one echoing in the silence...",
            "Shadows dance in the corners of your vision, daring you to look closer...",
            "Branches scratch at your cloak as if warning you to turn back...",
            "A chill runs down your spine as the path narrows and darkness thickens..."
        };
        return msgs[rand.nextInt(msgs.length)];
    }

    private String randomEncouragement() {
        String[] msgs = {
            "An old traveler nods: 'Keep your eyes on the prize, young warrior.'",
            "You hear a bard sing of heroes who never gave up.",
            "A merchant smiles: 'Every step brings you closer to glory.'",
            "A child waves from a village: 'Do not falter, brave one!'",
            "The wind whispers: 'Courage is forged in the trials you face.'",
            "A wandering monk bows: 'Patience and strength walk hand in hand.'",
            "A villager offers bread: 'May this give you strength for what lies ahead.'",
            "A hunter pauses and nods: 'Focus sharpens with every challenge you meet.'",
            "A caravan master smiles: 'Fortune favors those who press on, no matter the road.'",
            "An old fisherman waves: 'Even the longest journey begins with one step.'",
            "A wolf's eyes gleam from the darkness: 'Face fear, and it will serve you.'"
        };
        return msgs[rand.nextInt(msgs.length)];
    }
}