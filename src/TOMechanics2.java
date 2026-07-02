/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
import java.sql.*;
import java.text.*;
import java.util.*;
import java.io.*;

class TOMechanics2 {
  
    private TOMInterface2 gameHero2;
    public Scanner scanner = new Scanner(System.in);

    public TOMechanics2(TOMInterface2 gameHero2) {
        this.gameHero2 = gameHero2;
    }

    public void playGame2() {
        gameHero2.playGame();
    }
    
    static class Mage2 implements TOMInterface2 {
        private DecimalFormat df = new DecimalFormat("0");
        //Player Stats
        private int playerHP;
        private int armor;
        private int experiencePoints = 0;
        private String bossName;
        private String bossSkill;
        private int experienceThreshold = 30; 
        private int playerLevel = 1;
        private int levelDamageMultiplier = 1;
        //Mob/Enemy Stats
        private int monsterHP1 = 50; //goblin
        private int monsterHP2 = 70; //undead
        private double monsterArmor1;
        private double monsterArmor2;
        private int monsterDamage1;
        private int monsterDamage2;
        private int monsterCount = 0;
        //Boss
        private int bossHP;
        private double bossArmor;
        private int bossDamage;
        private int bossCounterAttackDamage;
        private int bossCount = 0;
        //Floor 
        private int currentFloor = 22;
        private double floorHPMultiplier = 0.2;
        private int floorDamage = 0;
        //Skill
        private int skillDamage;
        private int skillCooldown = 3;
        private int turnsSinceLastSkill = skillCooldown;
        private Random random = new Random();
        
        private int score = 0;
        private static List<ScoreEntry> highScores = new ArrayList<>();
    
        private static void saveHighScores() {
            try (PrintWriter writer = new PrintWriter(new FileWriter("high_scores.txt"))) {
                for (ScoreEntry entry : highScores) {
                    writer.println(entry.playerName + ":" + entry.score);
            }
        }   catch (IOException e) {
            e.printStackTrace();
        }
    }

    // New method to load high scores from a text file
        private static void loadHighScores() {
            highScores.clear(); // Clear existing high scores
            try (Scanner scanner = new Scanner(new File("high_scores.txt"))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String playerName = parts[0];
                        int score = Integer.parseInt(parts[1]);
                        highScores.add(new ScoreEntry(playerName, score));
                }
            }
            }catch (FileNotFoundException e) {
            // File not found , ignore
        }
    }
    // Modify the existing loadHighScores() block to load high scores from the file
        static {
            loadHighScores();
    }

        static class ScoreEntry {
            String playerName;
            int score;

            ScoreEntry(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }
    }
        
        
        public Mage2() {
            playerHP = 70;
            armor = random.nextInt(25)+15;
            monsterArmor1 = random.nextInt(21);
            monsterArmor2 = random.nextInt(11);
            

            System.out.println("\n"+getHeroType()+"'s Armor Activated. Mage gained " + armor + " Additional HP. ");
            playerHP += armor;

            System.out.println(getMonsterName1()+"'s Armor Activated. "+getMonsterName1()+" gained " + df.format(monsterArmor1) + " Additional HP.");
            System.out.println(getMonsterName2()+"'s Armor Activated. "+getMonsterName2()+" gained " + df.format(monsterArmor2) + " Additional HP.");

            monsterHP1 += monsterArmor1;
            monsterHP2 += monsterArmor2;
             System.out.println("");
            System.out.println(" - - - You are on floor " + currentFloor+" - - - ");
        }
        

        @Override
        public void playGame() {
            Scanner scan = new Scanner(System.in);

            do {
            System.out.println("\n" + getHeroType() + "'s HP: " + getPlayerHP() +
                " |"+getMonsterName1()+ ": " + df.format(getMonsterHP1()) +
                " |"+getMonsterName2()+ ": " + df.format(getMonsterHP2()) +
                " | Floor: " + currentFloor);

            System.out.print("Press 1 to Attack || Press 2 to use skill: ");
            int choice = scan.nextInt();
            System.out.println("");
            if (choice == 1) {
            playerAttack();
            if (areOpponentsAlive() == 1) {
                opponentsAttack();
            }
        }
            else if(choice == 2){
                useSkill();
                if (areOpponentsAlive() == 1) {
                    opponentsAttack();
                }
            }
             turnsSinceLastSkill++;
             if (experiencePoints >= experienceThreshold) {
                levelUp();
        }
             
            if (areOpponentsAlive() == 0) {
                if(currentFloor != 1){
                floorHPMultiplier += 0.1;
                resetOpponents();
                descendFloor();
            }
        } 
        // Check for level 


        
        
            if(currentFloor > 17 && currentFloor < 10){ //floor 17 to 10
                floorDamage = 10; //damage modifier for the enemies
        }
            
             
            if(currentFloor > 10 && currentFloor < 1){ //floor 10 to 1
                floorDamage = 20;
        }
            
            if(currentFloor == 17){
                bossName = "Warlord Grommash Ironfist";
                bossSkill = "Brutal Onslaught";
                bossHP = 300;
                bossArmor = random.nextDouble(31) * floorHPMultiplier;
                bossHP += bossArmor;
                bossDamage = random.nextInt(60) + playerLevel * 10;
                bossCounterAttackDamage = random.nextInt(51) + floorDamage;
                bossBattle();
            }
            
            if(currentFloor == 10){
                bossName="Archon Valthorn the Unyielding";
                bossSkill ="Divine Aegis";
                bossHP = 500;
                bossArmor = random.nextDouble(50) + 40 * floorHPMultiplier;
                bossHP += bossArmor;
                bossDamage = random.nextInt(60) + playerLevel * 15;
                bossCounterAttackDamage = random.nextInt(51) + floorDamage;
                bossBattle();
            }
            
            if(currentFloor == 1){
                bossName="Dread Sovereign Malachar the Indomitable";
                bossSkill="Abyssal Cataclysm";
                bossHP = 700;
                bossArmor = random.nextDouble(60) + 50 * floorHPMultiplier;
                bossHP += bossArmor;
                bossDamage = random.nextInt(60) + playerLevel * 20;
                bossCounterAttackDamage = random.nextInt(51) + floorDamage;
                bossBattle();
            }

    } while (currentFloor >= 1 && isPlayerAlive() == 1 && areOpponentsAlive() == 1);
//Database
            if (currentFloor == 0) {
    System.out.println("\nYou Escaped the Dungeon!");
    System.out.print("Enter name for leaderboard: ");
    String name = scan.nextLine();  // Only one call to nextLine is needed if you manage input correctly elsewhere

    String url = "jdbc:mysql://localhost:3306/leaderboard";
    String username = "root";
    String password = "";
    String insertQuery = "INSERT INTO leaderboard (name, stage) VALUES (?, ?)";

    try {
        // Load and register the JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish a connection
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {  // Using try-with-resources to auto close

            // Set the parameters for the insert query
            insertStatement.setString(1, name);
            insertStatement.setInt(2, currentFloor);

            // Execute the insert query
            int rowsAffected = insertStatement.executeUpdate();
            System.out.println("Leaderboard updated, rows affected: " + rowsAffected);
        }  // Auto close resources
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
}


}


@Override
public void playerAttack() {

    monsterDamage1 = random.nextInt(50) + playerLevel * levelDamageMultiplier;
    monsterDamage2 = random.nextInt(50) + playerLevel * levelDamageMultiplier;

    // Flag to check if each monster has already been defeated
    boolean defeatedMonster1 = false;
    boolean defeatedMonster2 = false;

    if (monsterHP1 > 0) {
        monsterHP1 = (monsterHP1 > monsterDamage1) ? monsterHP1 - monsterDamage1 : 0;
        System.out.println(getHeroType() + " dealt " + monsterDamage1 + " damage to " + getMonsterName1() + ".");
        if (monsterHP1 <= 0 && !defeatedMonster1) {
            System.out.println("\n" + getMonsterName1() + " is defeated!");
            defeatedMonster1 = true;
            monsterCount++;
            experiencePoints += 30;
            System.out.println("Gained 30 experience!\n");
        }
    }

    if (monsterHP2 > 0) {
        monsterHP2 = (monsterHP2 > monsterDamage2) ? monsterHP2 - monsterDamage2 : 0;
        System.out.println(getHeroType() + " dealt " + monsterDamage2 + " damage to " + getMonsterName2() + ".");
        if (monsterHP2 <= 0 && !defeatedMonster2) {
            System.out.println("\n" + getMonsterName2() + " is defeated!");
            defeatedMonster2 = true;
            monsterCount++;
            experiencePoints += 40;
            System.out.println("Gained 40 experience!\n");
        }
    }
}


        @Override
        public void opponentsAttack() {
            if (monsterHP1 > 0 && monsterHP2 > 0) {
                System.out.println("\n"+getMonsterName1()+" and "+getMonsterName2()+" attack "+getHeroType()+"!\n");
            } else if (monsterHP1 > 0) {
                System.out.println("\n"+getMonsterName1()+ " attacks "+getHeroType()+"!");
            } else if (monsterHP2 > 0) {
                System.out.println("\n"+getMonsterName2()+" attacks "+getHeroType()+"!");
            }

            if (monsterHP1 > 0) {
                int monsterDamage1 = random.nextInt( 21) + floorDamage;
                playerHP -= monsterDamage1;
                System.out.println(getMonsterName1()+" dealt " + monsterDamage1 + " damage to "+getHeroType()+".");
            }

            if (monsterHP2 > 0) {
                int monsterDamage2 = random.nextInt(  41) + floorDamage;
                playerHP -= monsterDamage2;
                System.out.println(getMonsterName2()+" dealt " + monsterDamage2 + " damage to "+getHeroType()+".");
            }
        }
        
        public void useSkill() {
            if (turnsSinceLastSkill >= skillCooldown) {
                System.out.println("Mage used ELEMENTAL NOVA!");
                skillDamage = random.nextInt(100) + 50;
                opponentsTakeDamage(skillDamage);
                turnsSinceLastSkill = 0;  // Reset the cooldown
            }else {
                System.out.println("ELEMENTAL NOVA is on cooldown. Choose another option.");
            }
        }
            
        public void opponentsTakeDamage(int damage) {
            if (monsterHP1 > 0) {
                monsterHP1 = (monsterHP1 > damage) ? monsterHP1 - damage : 0;
                System.out.println(getHeroType()+" dealt " + damage + " damage to "+getMonsterName1());
                if (monsterHP1 == 0) {
                    System.out.println(getMonsterName1()+" is defeated!");
                    monsterCount++;
                    experiencePoints += 30;
                    System.out.println("Gained 30 experience\n");
                }
            }

            if (monsterHP2 > 0) {
                monsterHP2 = (monsterHP2 > damage) ? monsterHP2 - damage : 0;
                System.out.println(getHeroType()+" dealt " + damage + " damage to "+getMonsterName2());
                if (monsterHP2 == 0) {
                    System.out.println(getMonsterName2()+" is defeated!");
                    monsterCount++;
                    experiencePoints += 40;
                    System.out.println("Gained 40 experience!\n");
                }
            }
        }
                    
        public void levelUp() {
            experienceThreshold += 30;
            playerHP += 25;
            skillDamage += 20;
            levelDamageMultiplier += 2;           
            playerLevel++;

            System.out.println(" \n- - - Congratulations! You leveled up to level " + playerLevel +
            ". Stats are increased! - - -\n ");

            // Reset experiencePoints
            experiencePoints = 0;
        }

        public void resetOpponents() {
            monsterHP1 = 50;
            monsterHP2 = 70;

            monsterArmor1 = random.nextDouble(21) * floorHPMultiplier;
            monsterArmor2 = random.nextDouble(11) * floorHPMultiplier;

            System.out.println(getMonsterName1()+"'s Armor Activated. "+getMonsterName1()+" gained " + df.format(monsterArmor1) + " Additional HP.");
            System.out.println(getMonsterName2()+"'s Armor Activated. "+getMonsterName2()+" gained " +df.format(monsterArmor2) + " Additional HP.");

            monsterHP1 += monsterArmor1;
            monsterHP2 += monsterArmor2;
}
         
         public void descendFloor() {
            if (currentFloor >= 1) {
                currentFloor--;
                System.out.println("\n- - - You descended to floor " + currentFloor+" - - -");
            } else {
                System.out.println("You are on the lowest floor. No more descending.");
            }
        }
         
public void bossBattle() {
    System.out.println("\n[ Boss Battle - Floor " + currentFloor + " ]");
    System.out.println("Boss: " + bossName);
    System.out.println("Boss HP: " + df.format(bossHP));

    Scanner scan = new Scanner(System.in);

    do {
        printPlayerStats();

        System.out.print("Press 1 to Attack || Press 2 to use skill: ");
        int choice = scan.nextInt();

        if (choice == 1) {
            playerAttackBoss();
            if (isBossAlive()) {
                bossCounterAttack();
            }
        } else if (choice == 2) {
            useSkill();
            if (isBossAlive()) {
                bossCounterAttack();
            }
        }
        turnsSinceLastSkill++;

        if (!isBossAlive()) {
            System.out.println("\n" + bossName + " is defeated!");
            break;
        }

        // Boss attacks
        bossAttack();
    } while (isPlayerAlive() == 1 && isBossAlive());

    currentFloor--;
}

public void playerAttackBoss() {
    int damageDealt = random.nextInt(60) + 60; // playerLevel * 10;
    bossHP = (bossHP > damageDealt) ? bossHP - damageDealt : 0;
    System.out.println("\n" + getHeroType() + " dealt " + damageDealt + " damage to " + bossName + ".");
}

public void bossAttack() {
    if (isPlayerAlive() == 1) {
        playerHP -= bossCounterAttackDamage;
        System.out.println("\n" + bossName + " used "+bossSkill+" to attack! " + getHeroType() + " takes " + bossCounterAttackDamage + " damage.");
    }
}

public void bossCounterAttack() {
    System.out.println("\n" + bossName + " counterattacks!");
    int damage = random.nextInt(51) + floorDamage;
    playerHP -= damage;
    System.out.println("\n" + getHeroType() + " takes " + damage + " damage.");
}

        private void printPlayerStats() {
            System.out.println("\n"+getHeroType()+"'s HP: " + df.format(playerHP) +
                " | Boss HP: " + df.format(bossHP) +
                " | Floor: " + currentFloor);
    }
        
        private void calculateAndDisplayScore() {
            Scanner scan = new Scanner(System.in);

        // Calculate the score 
            int randomScoreMonster = random.nextInt(60)+1 * monsterCount;
            int randomScoreBoss = random.nextInt(250) + 100 * bossCount;
            int finalScore = playerLevel * randomScoreMonster + experiencePoints + currentFloor + randomScoreBoss;

        // Update the total score
            score += finalScore;

        // Display the score
            System.out.println("\nScore: " + finalScore);
            System.out.println("Total Score: " + score);

        // Check for high score and add to the high scores list
            if (isHighScore(finalScore)) {
                System.out.println("Congratulations! You achieved a high score!");

            // Prompt the user to enter their name for the high score list
                System.out.print("Enter your name: ");
                String playerName = scan.next();

            // Add the score to the high scores list
                highScores.add(new ScoreEntry(playerName, finalScore));

            // Display the top scores
                displayTopScores();
                saveHighScores();
            }
        }

        private boolean isHighScore(int currentScore) {
            return highScores.isEmpty() || currentScore > highScores.get(0).score;
        }

        private void displayTopScores() {
            System.out.println("\nTop Scores:");

        // Sort the high scores in descending order
            highScores.sort((entry1, entry2) -> Integer.compare(entry2.score, entry1.score));

        // Display the top 5 scores
            int count = Math.min(5, highScores.size());
            for (int i = 0; i < count; i++) {
            ScoreEntry entry = highScores.get(i);
            System.out.println((i + 1) + ". " + entry.playerName + ": " + entry.score);
        }
    }
    

        private boolean isBossAlive() {
            return bossHP > 0;
        }
     
        @Override
        public int isPlayerAlive() {
            return playerHP > 0 ? 1 : 0;
        }

        @Override
        public int areOpponentsAlive() {
            return (monsterHP1 > 0 || monsterHP2 > 0) ? 1 : 0;
        }

        @Override
        public int getPlayerHP() {
            return playerHP;
        }

        @Override
        public int getMonsterHP1() {
            return monsterHP1;
        }

        @Override
        public int getMonsterHP2() {
            return monsterHP2;
        }

        @Override
        public String getHeroType() {
            return "Mage";
        }
        
        @Override
        public String getMonsterName1(){
            return "Goblin";
        }
        
        @Override
        public String getMonsterName2(){
            return "Undead";
        }

        
    }
    
    
        
    static class Paladin2 implements TOMInterface2 {
        
        private DecimalFormat df = new DecimalFormat("0");
        //Player Stats
        private int playerHP;
        private int armor;
        private String bossName;
        private String bossSkill;
        private int experiencePoints = 0;
        private int experienceThreshold = 30; 
        private int playerLevel = 1;
        private int levelDamageMultiplier = 1;
        //Mob/Enemy Stats
        private int monsterHP1 = 50; //goblin
        private int monsterHP2 = 70; //undead
        private double monsterArmor1;
        private double monsterArmor2;
        private int monsterDamage1;
        private int monsterDamage2;
        private int monsterCount = 0;
        //Boss
        private int bossHP;
        private double bossArmor;
        private int bossDamage;
        private int bossCounterAttackDamage;
        private int bossCount = 0;
        //Floor 
        private int currentFloor = 22;
        private double floorHPMultiplier = 0.2;
        private int floorDamage = 0;
        //Skill
        private int skillDamage;
        private int skillCooldown = 3;
        private int turnsSinceLastSkill = skillCooldown;
        boolean defeatedMonster1 = false;
        boolean defeatedMonster2 = false;
        private Random random = new Random();
        
        private int score = 0;
        private static List<ScoreEntry> highScores = new ArrayList<>();
    
        private static void saveHighScores() {
            try (PrintWriter writer = new PrintWriter(new FileWriter("high_scores.txt"))) {
                for (ScoreEntry entry : highScores) {
                    writer.println(entry.playerName + ":" + entry.score);
            }
        }   catch (IOException e) {
            e.printStackTrace();
        }
    }

    // New method to load high scores from a text file
        private static void loadHighScores() {
            highScores.clear(); // Clear existing high scores
            try (Scanner scanner = new Scanner(new File("high_scores.txt"))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String playerName = parts[0];
                        int score = Integer.parseInt(parts[1]);
                        highScores.add(new ScoreEntry(playerName, score));
                }
            }
            }catch (FileNotFoundException e) {
            // File not found , ignore
        }
    }
    // Modify the existing loadHighScores() block to load high scores from the file
        static {
            loadHighScores();
    }

        static class ScoreEntry {
            String playerName;
            int score;

            ScoreEntry(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }
    }
        
        
        public Paladin2() {
            playerHP = 110;
            armor = random.nextInt(31)+15;
            monsterArmor1 = random.nextInt(21);
            monsterArmor2 = random.nextInt(11);
            

            System.out.println("\n"+getHeroType()+"'s Armor Activated. "+getHeroType()+" gained " + armor + " Additional HP. ");
            playerHP += armor;

            System.out.println(getMonsterName1()+"'s Armor Activated. " +getMonsterName1()+" gained " + df.format(monsterArmor1) + " Additional HP.");
            System.out.println(getMonsterName2()+"'s Armor Activated. " +getMonsterName2()+" gained " + df.format(monsterArmor2) + " Additional HP.");

            monsterHP1 += monsterArmor1;
            monsterHP2 += monsterArmor2;
             System.out.println("");
            System.out.println(" - - - You are on floor " + currentFloor+" - - - ");
        }
        

        @Override
        public void playGame() {
            Scanner scan = new Scanner(System.in);

            do {
            System.out.println("\n" + getHeroType() + "'s HP: " + getPlayerHP() +
                " |"+getMonsterName1()+ ": " + df.format(getMonsterHP1()) +
                " |"+getMonsterName2()+ ": " + df.format(getMonsterHP2()) +
                " | Floor: " + currentFloor);

            System.out.print("Press 1 to Attack || Press 2 to use skill: ");
            int choice = scan.nextInt();
            System.out.println("");
            if (choice == 1) {
            playerAttack();
            if (areOpponentsAlive() == 1) {
                opponentsAttack();
            }
        }
            else if(choice == 2){
                useSkill();
                if (areOpponentsAlive() == 1) {
                    opponentsAttack();
                }
            }
             turnsSinceLastSkill++;
             
             
            if (areOpponentsAlive() == 0) {
                if(currentFloor != 1){
                floorHPMultiplier += 0.1;
                resetOpponents();
                descendFloor();
            }
        } 
        // Check for level 


        
        
            if(currentFloor > 17 && currentFloor < 10){ //floor 17 to 10
                floorDamage = 10; //damage modifier for the enemies
        }
            
             
            if(currentFloor > 10 && currentFloor < 1){ //floor 10 to 1
                floorDamage = 20;
        }
            
                 if(currentFloor == 17){
                bossName = "Warlord Grommash Ironfist";
                bossSkill = "Brutal Onslaught";
                bossHP = 300;
                bossArmor = random.nextDouble(31) * floorHPMultiplier;
                bossHP += bossArmor;
                bossDamage = random.nextInt(60) + playerLevel * 10;
                bossCounterAttackDamage = random.nextInt(51) + floorDamage;
                bossBattle();
            }
            
            if(currentFloor == 10){
                bossName="Archon Valthorn the Unyielding";
                bossSkill ="Divine Aegis";
                bossHP = 500;
                bossArmor = random.nextDouble(50) + 40 * floorHPMultiplier;
                bossHP += bossArmor;
                bossDamage = random.nextInt(60) + playerLevel * 15;
                bossCounterAttackDamage = random.nextInt(51) + floorDamage;
                bossBattle();
            }
            
            if(currentFloor == 1){
                bossName="Dread Sovereign Malachar the Indomitable";
                bossSkill="Abyssal Cataclysm";
                bossHP = 700;
                bossArmor = random.nextDouble(60) + 50 * floorHPMultiplier;
                bossHP += bossArmor;
                bossDamage = random.nextInt(60) + playerLevel * 20;
                bossCounterAttackDamage = random.nextInt(51) + floorDamage;
                bossBattle();
            }

    } while (currentFloor >= 1 && isPlayerAlive() == 1 && areOpponentsAlive() == 1);

            if (currentFloor == 0) {
                System.out.println("\nYou Escaped the Dungeon!");
            System.out.print("Enter name for leaderboard: ");
            scan.nextLine();
            String name = scan.nextLine();
            String url = "jdbc:mysql://localhost:3306/leaderboard";
            String username = "root";
            String password = "";
            String insertQuery = "INSERT INTO leaderboard (name ,stage) VALUES (?,?)";
            String selectQuery = "SELECT stage FROM leaderboard";
            try {
            // Load and register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish a connection
            Connection connection = DriverManager.getConnection(url, username, password);
            
            // Create a PreparedStatement for insert
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            
            // Set the name parameter for insert
            insertStatement.setString(1, name);
            insertStatement.setInt(2, currentFloor);
            
            // Execute the insert query
            int rowsAffected = insertStatement.executeUpdate();
            
            // Close the insert statement
            insertStatement.close();
            
            // Create a Statement for select
            Statement selectStatement = connection.createStatement();
            
            // Execute the select query
            ResultSet resultSet = selectStatement.executeQuery(selectQuery);
            
            
            // Close resources
            resultSet.close();
            selectStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        }   else {
            System.out.println("\nGame Over! "+getHeroType()+" has been defeated.");
            System.out.print("Enter name for leaderboard: ");
            scan.nextLine();
            String name = scan.nextLine();
            String url = "jdbc:mysql://localhost:3306/leaderboard";
            String username = "root";
            String password = "";
            String insertQuery = "INSERT INTO leaderboard (name ,stage) VALUES (?,?)";
            String selectQuery = "SELECT stage FROM leaderboard";
            try {
            // Load and register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish a connection
            Connection connection = DriverManager.getConnection(url, username, password);
            
            // Create a PreparedStatement for insert
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            
            // Set the name parameter for insert
            insertStatement.setString(1, name);
            insertStatement.setInt(2, currentFloor);
            
            // Execute the insert query
            int rowsAffected = insertStatement.executeUpdate();
            
            // Close the insert statement
            insertStatement.close();
            
            // Create a Statement for select
            Statement selectStatement = connection.createStatement();
            
            // Execute the select query
            ResultSet resultSet = selectStatement.executeQuery(selectQuery);
            
            
            // Close resources
            resultSet.close();
            selectStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
}
        }


@Override
public void playerAttack() {

    monsterDamage1 = random.nextInt(25) + playerLevel * levelDamageMultiplier;
    monsterDamage2 = random.nextInt(25) + playerLevel * levelDamageMultiplier;

    // Flag to check if each monster has already been defeated
    

    if (monsterHP1 > 0) {
        monsterHP1 = (monsterHP1 > monsterDamage1) ? monsterHP1 - monsterDamage1 : 0;
        System.out.println(getHeroType() + " dealt " + monsterDamage1 + " damage to " + getMonsterName1() + ".");
        if (monsterHP1 <= 0 && !defeatedMonster1) {
            System.out.println("\n" + getMonsterName1() + " is defeated!");
            defeatedMonster1 = true;
            monsterCount++;
            experiencePoints += 30;
            System.out.println("Gained 30 experience!\n");
        }
    }

    if (monsterHP2 > 0) {
        monsterHP2 = (monsterHP2 > monsterDamage2) ? monsterHP2 - monsterDamage2 : 0;
        System.out.println(getHeroType() + " dealt " + monsterDamage2 + " damage to " + getMonsterName2() + ".");
        if (monsterHP2 <= 0 && !defeatedMonster2) {
            System.out.println("\n" + getMonsterName2() + " is defeated!");
            defeatedMonster2 = true;
            monsterCount++;
            experiencePoints += 40;
            System.out.println("Gained 40 experience!\n");
        }
    }
}


        @Override
        public void opponentsAttack() {
            if (monsterHP1 > 0 && monsterHP2 > 0) {
                System.out.println("\n"+getMonsterName1()+" and "+getMonsterName2()+" attack "+getHeroType()+"!\n");
            } else if (monsterHP1 > 0) {
                System.out.println("\n"+getMonsterName1()+ " attacks "+getHeroType()+"!");
            } else if (monsterHP2 > 0) {
                System.out.println("\n"+getMonsterName2()+" attacks "+getHeroType()+"!");
            }

            if (monsterHP1 > 0) {
                int monsterDamage1 = random.nextInt( 21) + floorDamage;
                playerHP -= monsterDamage1;
                System.out.println(getMonsterName1()+" dealt " + monsterDamage1 + " damage to "+getHeroType()+".");
            }

            if (monsterHP2 > 0) {
                int monsterDamage2 = random.nextInt(  41) + floorDamage;
                playerHP -= monsterDamage2;
                System.out.println(getMonsterName2()+" dealt " + monsterDamage2 + " damage to "+getHeroType()+".");
            }
        }
        
        public void useSkill() {
            if (turnsSinceLastSkill >= skillCooldown) {
                System.out.println("Paladin used AVENGING STRIKE!");
                skillDamage = random.nextInt(30) + 25;
                opponentsTakeDamage(skillDamage);
                turnsSinceLastSkill = 0;  // Reset the cooldown
            }else {
                System.out.println("AVENGING STRIKE is on cooldown. Choose another option.");
            }
        }
            
        public void opponentsTakeDamage(int damage) {
            if (monsterHP1 > 0) {
                monsterHP1 = (monsterHP1 > damage) ? monsterHP1 - damage : 0;
                System.out.println(getHeroType()+" dealt " + damage + " damage to "+getMonsterName1());
                if (monsterHP1 == 0) {
                    System.out.println(getMonsterName1()+" is defeated!");
                    monsterCount++;
                    experiencePoints += 30;
                    System.out.println("Gained 30 experience\n");
                }
            }

            if (monsterHP2 > 0) {
                monsterHP2 = (monsterHP2 > damage) ? monsterHP2 - damage : 0;
                System.out.println(getHeroType()+" dealt " + damage + " damage to "+getMonsterName2());
                if (monsterHP2 == 0) {
                    System.out.println(getMonsterName2()+" is defeated!");
                    monsterCount++;
                    experiencePoints += 40;
                    System.out.println("Gained 40 experience!\n");
                }
            }
        }
                    
        public void levelUp() {
            experienceThreshold += 30;
            playerHP += 40;
            skillDamage += 20;
            levelDamageMultiplier += 2;           
            playerLevel++;

            System.out.println(" \n- - - Congratulations! You leveled up to level " + playerLevel +
            ". Stats are increased! - - -\n ");

            // Reset experiencePoints
            experiencePoints = 0;
        }

        public void resetOpponents() {
            monsterHP1 = 50;
            monsterHP2 = 70;

            monsterArmor1 = random.nextDouble(21) * floorHPMultiplier;
            monsterArmor2 = random.nextDouble(11) * floorHPMultiplier;

            System.out.println(getMonsterName1()+"'s Armor Activated. "+getMonsterName1()+" gained " + df.format(monsterArmor1) + " Additional HP.");
            System.out.println(getMonsterName2()+"'s Armor Activated. "+getMonsterName2()+" gained " +df.format(monsterArmor2) + " Additional HP.");

            monsterHP1 += monsterArmor1;
            monsterHP2 += monsterArmor2;
}
         
         public void descendFloor() {
            if (currentFloor >= 1) {
                currentFloor--;
                System.out.println("\n- - - You descended to floor " + currentFloor+" - - -");
            } else {
                System.out.println("You are on the lowest floor. No more descending.");
            }
        }
         
         public void bossBattle() {
    System.out.println("\n[ Boss Battle - Floor " + currentFloor + " ]");
    System.out.println("Boss: " + bossName);
    System.out.println("Boss HP: " + df.format(bossHP));

    Scanner scan = new Scanner(System.in);

    do {
        printPlayerStats();

        System.out.print("Press 1 to Attack || Press 2 to use skill: ");
        int choice = scan.nextInt();

        if (choice == 1) {
            playerAttackBoss();
            if (isBossAlive()) {
                bossCounterAttack();
            }
        } else if (choice == 2) {
            useSkill();
            if (isBossAlive()) {
                bossCounterAttack();
            }
        }
        turnsSinceLastSkill++;

        if (!isBossAlive()) {
            System.out.println("\n" + bossName + " is defeated!");
            break;
        }

        // Boss attacks
        bossAttack();
    } while (isPlayerAlive() == 1 && isBossAlive());

    currentFloor--;
}

public void playerAttackBoss() {
    int damageDealt = random.nextInt(60) + 60; // playerLevel * 10;
    bossHP = (bossHP > damageDealt) ? bossHP - damageDealt : 0;
    System.out.println("\n" + getHeroType() + " dealt " + damageDealt + " damage to " + bossName + ".");
}

public void bossAttack() {
    if (isPlayerAlive() == 1) {
        playerHP -= bossCounterAttackDamage;
        System.out.println("\n" + bossName + " used "+bossSkill+" to attack! " + getHeroType() + " takes " + bossCounterAttackDamage + " damage.");
    }
}

public void bossCounterAttack() {
    System.out.println("\n" + bossName + " counterattacks!");
    int damage = random.nextInt(51) + floorDamage;
    playerHP -= damage;
    System.out.println("\n" + getHeroType() + " takes " + damage + " damage.");
}
         
  
    
        private void printPlayerStats() {
            System.out.println("\n"+getHeroType()+"'s HP: " + df.format(playerHP) +
                " | Boss HP: " + df.format(bossHP) +
                " | Floor: " + currentFloor);
    }
        
        private void calculateAndDisplayScore() {
            Scanner scan = new Scanner(System.in);

        // Calculate the score 
            int randomScoreMonster = random.nextInt(60)+1 * monsterCount;
            int randomScoreBoss = random.nextInt(250) + 100 * bossCount;
            int finalScore = playerLevel * randomScoreMonster + experiencePoints + currentFloor + randomScoreBoss;

        // Update the total score
            score += finalScore;

        // Display the score
            System.out.println("\nScore: " + finalScore);
            System.out.println("Total Score: " + score);

        // Check for high score and add to the high scores list
            if (isHighScore(finalScore)) {
                System.out.println("Congratulations! You achieved a high score!");

            // Prompt the user to enter their name for the high score list
                System.out.print("Enter your name: ");
                String playerName = scan.next();

            // Add the score to the high scores list
                highScores.add(new ScoreEntry(playerName, finalScore));

            // Display the top scores
                displayTopScores();
                saveHighScores();
            }
        }

        private boolean isHighScore(int currentScore) {
            return highScores.isEmpty() || currentScore > highScores.get(0).score;
        }

        private void displayTopScores() {
            System.out.println("\nTop Scores:");

        // Sort the high scores in descending order
            highScores.sort((entry1, entry2) -> Integer.compare(entry2.score, entry1.score));

        // Display the top 5 scores
            int count = Math.min(5, highScores.size());
            for (int i = 0; i < count; i++) {
            ScoreEntry entry = highScores.get(i);
            System.out.println((i + 1) + ". " + entry.playerName + ": " + entry.score);
        }
    }
    

        private boolean isBossAlive() {
            return bossHP > 0;
        }
     
        @Override
        public int isPlayerAlive() {
            return playerHP > 0 ? 1 : 0;
        }

        @Override
        public int areOpponentsAlive() {
            return (monsterHP1 > 0 || monsterHP2 > 0) ? 1 : 0;
        }

        @Override
        public int getPlayerHP() {
            return playerHP;
        }

        @Override
        public int getMonsterHP1() {
            return monsterHP1;
        }

        @Override
        public int getMonsterHP2() {
            return monsterHP2;
        }

        @Override
        public String getHeroType() {
            return "Paladin";
        }
        
        @Override
        public String getMonsterName1(){
            return "Goblin";
        }
        
        @Override
        public String getMonsterName2(){
            return "Undead";
        }

        
    }
    
    static class Beast2 implements TOMInterface2 {
        private DecimalFormat df = new DecimalFormat("0");
        //Player Stats
        private int playerHP;
        private int armor;
        private String bossName;
        private String bossSkill;
        private int experiencePoints = 0;
        private int experienceThreshold = 30; 
        private int playerLevel = 1;
        private int levelDamageMultiplier = 1;
        //Mob/Enemy Stats
        private int monsterHP1 = 50; //goblin
        private int monsterHP2 = 70; //undead
        private double monsterArmor1;
        private double monsterArmor2;
        private int monsterDamage1;
        private int monsterDamage2;
        private int monsterCount = 0;
        //Boss
        private int bossHP;
        private double bossArmor;
        private int bossDamage;
        private int bossCounterAttackDamage;
        private int bossCount = 0;
        //Floor 
        private int currentFloor = 22;
        private double floorHPMultiplier = 0.2;
        private int floorDamage = 0;
        //Skill
        private int skillDamage;
        private int skillCooldown = 3;
        private int turnsSinceLastSkill = skillCooldown;
        private Random random = new Random();
        
        private int score = 0;
        private static List<ScoreEntry> highScores = new ArrayList<>();
    
        private static void saveHighScores() {
            try (PrintWriter writer = new PrintWriter(new FileWriter("high_scores.txt"))) {
                for (ScoreEntry entry : highScores) {
                    writer.println(entry.playerName + ":" + entry.score);
            }
        }   catch (IOException e) {
            e.printStackTrace();
        }
    }

    // New method to load high scores from a text file
        private static void loadHighScores() {
            highScores.clear(); // Clear existing high scores
            try (Scanner scanner = new Scanner(new File("high_scores.txt"))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String playerName = parts[0];
                        int score = Integer.parseInt(parts[1]);
                        highScores.add(new ScoreEntry(playerName, score));
                }
            }
            }catch (FileNotFoundException e) {
            // File not found , ignore
        }
    }
    // Modify the existing loadHighScores() block to load high scores from the file
        static {
            loadHighScores();
    }

        static class ScoreEntry {
            String playerName;
            int score;

            ScoreEntry(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }
    }
        
        
        public Beast2() {
            playerHP = 90;
            armor = random.nextInt(31)+15;
            monsterArmor1 = random.nextInt(21);
            monsterArmor2 = random.nextInt(11);
            

            System.out.println("\n"+getHeroType()+"'s Armor Activated. "+getHeroType()+" gained " + armor + " Additional HP. ");
            playerHP += armor;

            System.out.println(getMonsterName1()+"'s Armor Activated. "+getMonsterName1()+" gained " + df.format(monsterArmor1) + " Additional HP.");
            System.out.println(getMonsterName2()+"'s Armor Activated. "+getMonsterName2()+" gained " + df.format(monsterArmor2) + " Additional HP.");

            monsterHP1 += monsterArmor1;
            monsterHP2 += monsterArmor2;
             System.out.println("");
            System.out.println(" - - - You are on floor " + currentFloor+" - - - ");
        }
        

        @Override
        public void playGame() {
            Scanner scan = new Scanner(System.in);

            do {
            System.out.println("\n" + getHeroType() + "'s HP: " + getPlayerHP() +
                " |"+getMonsterName1()+ ": " + df.format(getMonsterHP1()) +
                " |"+getMonsterName2()+ ": " + df.format(getMonsterHP2()) +
                " | Floor: " + currentFloor);

            System.out.print("Press 1 to Attack || Press 2 to use skill: ");
            int choice = scan.nextInt();
            System.out.println("");
            if (choice == 1) {
            playerAttack();
            if (areOpponentsAlive() == 1) {
                opponentsAttack();
            }
        }
            else if(choice == 2){
                useSkill();
                if (areOpponentsAlive() == 1) {
                    opponentsAttack();
                }
            }
             turnsSinceLastSkill++;
             if (experiencePoints >= experienceThreshold) {
                levelUp();
        }
             
            if (areOpponentsAlive() == 0) {
                if(currentFloor != 1){
                floorHPMultiplier += 0.1;
                resetOpponents();
                descendFloor();
            }
        } 
        // Check for level 


        
        
            if(currentFloor > 17 && currentFloor < 10){ //floor 17 to 10
                floorDamage = 10; //damage modifier for the enemies
        }
            
             
            if(currentFloor > 10 && currentFloor < 1){ //floor 10 to 1
                floorDamage = 20;
        }
            
                if(currentFloor == 17){
                bossName = "Warlord Grommash Ironfist";
                bossSkill = "Brutal Onslaught";
                bossHP = 300;
                bossArmor = random.nextDouble(31) * floorHPMultiplier;
                bossHP += bossArmor;
                bossDamage = random.nextInt(60) + playerLevel * 10;
                bossCounterAttackDamage = random.nextInt(51) + floorDamage;
                bossBattle();
            }
            
            if(currentFloor == 10){
                bossName="Archon Valthorn the Unyielding";
                bossSkill ="Divine Aegis";
                bossHP = 500;
                bossArmor = random.nextDouble(50) + 40 * floorHPMultiplier;
                bossHP += bossArmor;
                bossDamage = random.nextInt(60) + playerLevel * 15;
                bossCounterAttackDamage = random.nextInt(51) + floorDamage;
                bossBattle();
            }
            
            if(currentFloor == 1){
                bossName="Dread Sovereign Malachar the Indomitable";
                bossSkill="Abyssal Cataclysm";
                bossHP = 700;
                bossArmor = random.nextDouble(60) + 50 * floorHPMultiplier;
                bossHP += bossArmor;
                bossDamage = random.nextInt(60) + playerLevel * 20;
                bossCounterAttackDamage = random.nextInt(51) + floorDamage;
                bossBattle();
            }
    } while (currentFloor >= 1 && isPlayerAlive() == 1 && areOpponentsAlive() == 1);

            if (currentFloor == 0) {
    System.out.println("\nYou Escaped the Dungeon!");
    System.out.print("Enter name for leaderboard: ");
    String name = scan.nextLine();  // Only one call to nextLine is needed if you manage input correctly elsewhere

    String url = "jdbc:mysql://localhost:3306/leaderboard";
    String username = "root";
    String password = "";
    String insertQuery = "INSERT INTO leaderboard (name, stage) VALUES (?, ?)";

    try {
        // Load and register the JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish a connection
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {  // Using try-with-resources to auto close

            // Set the parameters for the insert query
            insertStatement.setString(1, name);
            insertStatement.setInt(2, currentFloor);

            // Execute the insert query
            int rowsAffected = insertStatement.executeUpdate();
            System.out.println("Leaderboard updated, rows affected: " + rowsAffected);
        }  // Auto close resources
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
}
  else {
            System.out.println("\nGame Over! "+getHeroType()+" has been defeated.");
    }
}


@Override
public void playerAttack() {

    monsterDamage1 = random.nextInt(40) + playerLevel * levelDamageMultiplier;
    monsterDamage2 = random.nextInt(40) + playerLevel * levelDamageMultiplier;

    // Flag to check if each monster has already been defeated
    boolean defeatedMonster1 = false;
    boolean defeatedMonster2 = false;

    if (monsterHP1 > 0) {
        monsterHP1 = (monsterHP1 > monsterDamage1) ? monsterHP1 - monsterDamage1 : 0;
        System.out.println(getHeroType() + " dealt " + monsterDamage1 + " damage to " + getMonsterName1() + ".");
        if (monsterHP1 <= 0 && !defeatedMonster1) {
            System.out.println("\n" + getMonsterName1() + " is defeated!");
            defeatedMonster1 = true;
            monsterCount++;
            experiencePoints += 30;
            System.out.println("Gained 30 experience!\n");
        }
    }

    if (monsterHP2 > 0) {
        monsterHP2 = (monsterHP2 > monsterDamage2) ? monsterHP2 - monsterDamage2 : 0;
        System.out.println(getHeroType() + " dealt " + monsterDamage2 + " damage to " + getMonsterName2() + ".");
        if (monsterHP2 <= 0 && !defeatedMonster2) {
            System.out.println("\n" + getMonsterName2() + " is defeated!");
            defeatedMonster2 = true;
            monsterCount++;
            experiencePoints += 40;
            System.out.println("Gained 40 experience!\n");
        }
    }
}


        @Override
        public void opponentsAttack() {
            if (monsterHP1 > 0 && monsterHP2 > 0) {
                System.out.println("\n"+getMonsterName1()+" and "+getMonsterName2()+" attack "+getHeroType()+"!\n");
            } else if (monsterHP1 > 0) {
                System.out.println("\n"+getMonsterName1()+ " attacks "+getHeroType()+"!");
            } else if (monsterHP2 > 0) {
                System.out.println("\n"+getMonsterName2()+" attacks "+getHeroType()+"!");
            }

            if (monsterHP1 > 0) {
                int monsterDamage1 = random.nextInt( 21) + floorDamage;
                playerHP -= monsterDamage1;
                System.out.println(getMonsterName1()+" dealt " + monsterDamage1 + " damage to "+getHeroType()+".");
            }

            if (monsterHP2 > 0) {
                int monsterDamage2 = random.nextInt(  41) + floorDamage;
                playerHP -= monsterDamage2;
                System.out.println(getMonsterName2()+" dealt " + monsterDamage2 + " damage to "+getHeroType()+".");
            }
        }
        
        public void useSkill() {
            if (turnsSinceLastSkill >= skillCooldown) {
                System.out.println("Beast used BEASTIAL FURY!");
                skillDamage = random.nextInt(50) + 45;
                opponentsTakeDamage(skillDamage);
                turnsSinceLastSkill = 0;  // Reset the cooldown
            }else {
                System.out.println("BEASTIAL FURY is on cooldown. Choose another option.");
            }
        }
            
        public void opponentsTakeDamage(int damage) {
            if (monsterHP1 > 0) {
                monsterHP1 = (monsterHP1 > damage) ? monsterHP1 - damage : 0;
                System.out.println(getHeroType()+" dealt " + damage + " damage to "+getMonsterName1());
                if (monsterHP1 == 0) {
                    System.out.println(getMonsterName1()+" is defeated!");
                    monsterCount++;
                    experiencePoints += 30;
                    System.out.println("Gained 30 experience\n");
                }
            }

            if (monsterHP2 > 0) {
                monsterHP2 = (monsterHP2 > damage) ? monsterHP2 - damage : 0;
                System.out.println(getHeroType()+" dealt " + damage + " damage to "+getMonsterName2());
                if (monsterHP2 == 0) {
                    System.out.println(getMonsterName2()+" is defeated!");
                    monsterCount++;
                    experiencePoints += 40;
                    System.out.println("Gained 40 experience!\n");
                }
            }
        }
                    
        public void levelUp() {
            experienceThreshold += 30;
            playerHP += 40;
            skillDamage += 20;
            levelDamageMultiplier += 2;           
            playerLevel++;

            System.out.println(" \n- - - Congratulations! You leveled up to level " + playerLevel +
            ". Stats are increased! - - -\n ");

            // Reset experiencePoints
            experiencePoints = 0;
        }

        public void resetOpponents() {
            monsterHP1 = 50;
            monsterHP2 = 70;

            monsterArmor1 = random.nextDouble(21) * floorHPMultiplier;
            monsterArmor2 = random.nextDouble(11) * floorHPMultiplier;

            System.out.println(getMonsterName1()+"'s Armor Activated. "+getMonsterName1()+" gained " + df.format(monsterArmor1) + " Additional HP.");
            System.out.println(getMonsterName2()+"'s Armor Activated. "+getMonsterName2()+" gained " +df.format(monsterArmor2) + " Additional HP.");

            monsterHP1 += monsterArmor1;
            monsterHP2 += monsterArmor2;
}
         
         public void descendFloor() {
            if (currentFloor >= 1) {
                currentFloor--;
                System.out.println("\n- - - You descended to floor " + currentFloor+" - - -");
            } else {
                System.out.println("You are on the lowest floor. No more descending.");
            }
        }
         
       public void bossBattle() {
    System.out.println("\n[ Boss Battle - Floor " + currentFloor + " ]");
    System.out.println("Boss: " + bossName);
    System.out.println("Boss HP: " + df.format(bossHP));

    Scanner scan = new Scanner(System.in);

    do {
        printPlayerStats();

        System.out.print("Press 1 to Attack || Press 2 to use skill: ");
        int choice = scan.nextInt();

        if (choice == 1) {
            playerAttackBoss();
            if (isBossAlive()) {
                bossCounterAttack();
            }
        } else if (choice == 2) {
            useSkill();
            if (isBossAlive()) {
                bossCounterAttack();
            }
        }
        turnsSinceLastSkill++;

        if (!isBossAlive()) {
            System.out.println("\n" + bossName + " is defeated!");
            break;
        }

        // Boss attacks
        bossAttack();
    } while (isPlayerAlive() == 1 && isBossAlive());

    currentFloor--;
}

public void playerAttackBoss() {
    int damageDealt = random.nextInt(60) + 60; // playerLevel * 10;
    bossHP = (bossHP > damageDealt) ? bossHP - damageDealt : 0;
    System.out.println("\n" + getHeroType() + " dealt " + damageDealt + " damage to " + bossName + ".");
}

public void bossAttack() {
    if (isPlayerAlive() == 1) {
        playerHP -= bossCounterAttackDamage;
        System.out.println("\n" + bossName + " used "+bossSkill+" to attack! " + getHeroType() + " takes " + bossCounterAttackDamage + " damage.");
    }
}

public void bossCounterAttack() {
    System.out.println("\n" + bossName + " counterattacks!");
    int damage = random.nextInt(51) + floorDamage;
    playerHP -= damage;
    System.out.println("\n" + getHeroType() + " takes " + damage + " damage.");
}
         
    
        private void printPlayerStats() {
            System.out.println("\n"+getHeroType()+"'s HP: " + df.format(playerHP) +
                " | Boss HP: " + df.format(bossHP) +
                " | Floor: " + currentFloor);
    }
        
        private void calculateAndDisplayScore() {
            Scanner scan = new Scanner(System.in);

        // Calculate the score 
            int randomScoreMonster = random.nextInt(60)+1 * monsterCount;
            int randomScoreBoss = random.nextInt(250) + 100 * bossCount;
            int finalScore = playerLevel * randomScoreMonster + experiencePoints + currentFloor + randomScoreBoss;

        // Update the total score
            score += finalScore;

        // Display the score
            System.out.println("\nScore: " + finalScore);
            System.out.println("Total Score: " + score);

        // Check for high score and add to the high scores list
            if (isHighScore(finalScore)) {
                System.out.println("Congratulations! You achieved a high score!");

            // Prompt the user to enter their name for the high score list
                System.out.print("Enter your name: ");
                String playerName = scan.next();

            // Add the score to the high scores list
                highScores.add(new ScoreEntry(playerName, finalScore));

            // Display the top scores
                displayTopScores();
                saveHighScores();
            }
        }

        private boolean isHighScore(int currentScore) {
            return highScores.isEmpty() || currentScore > highScores.get(0).score;
        }

        private void displayTopScores() {
            System.out.println("\nTop Scores:");

        // Sort the high scores in descending order
            highScores.sort((entry1, entry2) -> Integer.compare(entry2.score, entry1.score));

        // Display the top 5 scores
            int count = Math.min(5, highScores.size());
            for (int i = 0; i < count; i++) {
            ScoreEntry entry = highScores.get(i);
            System.out.println((i + 1) + ". " + entry.playerName + ": " + entry.score);
        }
    }
    

        private boolean isBossAlive() {
            return bossHP > 0;
        }
     
        @Override
        public int isPlayerAlive() {
            return playerHP > 0 ? 1 : 0;
        }

        @Override
        public int areOpponentsAlive() {
            return (monsterHP1 > 0 || monsterHP2 > 0) ? 1 : 0;
        }

        @Override
        public int getPlayerHP() {
            return playerHP;
        }

        @Override
        public int getMonsterHP1() {
            return monsterHP1;
        }

        @Override
        public int getMonsterHP2() {
            return monsterHP2;
        }

        @Override
        public String getHeroType() {
            return "Beast";
        }
        
        @Override
        public String getMonsterName1(){
            return "Goblin";
        }
        
        @Override
        public String getMonsterName2(){
            return "Undead";
        }  
    }
}
