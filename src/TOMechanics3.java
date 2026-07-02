/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */

//TOMechanics3.java
import com.sun.jdi.connect.spi.Connection;
import java.util.Random;
import java.util.Scanner;

class TOMechanics3 {
  
      private TOMInterface3 gameHero;
    public Scanner scanner = new Scanner(System.in);

    public TOMechanics3(TOMInterface3 gameHero) {
        this.gameHero = gameHero;
    }

    public void playGame3() {
        gameHero.playGame();
    }
    
    static class Mage3 implements TOMInterface3 {
        private int playerHP;
        private int armor;
        private int demonHP = 60;
        private int orcHP = 90;
        private double demonArmor;
        private double orcArmor;
        private int demonDamage;
        private int orcDamage;
        private int currentFloor = 10;
        private int experiencePoints = 0;
        private int experienceThreshold = 40; 
        private int playerLevel = 1;
        private double floorHPMultiplier = 0.5;
        private int floorDamage = 0;

        private Random random = new Random();
        
        public Mage3() {
            playerHP = 50;
            armor = random.nextInt(20);
            demonArmor = random.nextInt(31);
            orcArmor = random.nextInt(21);

            System.out.println("\nMage's Armor Activated. Mage gained " + armor + " Additional HP. ");
            playerHP += armor;

            System.out.println("Demon's Armor Activated. Goblin gained " + demonArmor + " Additional HP.");
            System.out.println("Orc's Armor Activated. Undead gained " + orcArmor + " Additional HP.");

            demonHP += demonArmor;
            orcHP += orcArmor;
            System.out.println("You are on floor " + currentFloor);
        }
        
        public void levelUp() {
  
            experienceThreshold += 40;
            playerHP += 20;
            playerLevel++;

            System.out.println("Congratulations! You leveled up to level " + playerLevel +
            ". Stats are increased!");

            // Reset experiencePoints
            experiencePoints = 0;
        }

        public void resetOpponents() {
            demonHP = 60;
            orcHP = 90;

            demonArmor = random.nextDouble(31) * floorHPMultiplier;
            orcArmor = random.nextDouble(21) * floorHPMultiplier;

            System.out.println("Demon's Armor Activated. Goblin gained " + (int)demonArmor + " Additional HP.");
            System.out.println("Orc's Armor Activated. Undead gained " + (int)orcArmor + " Additional HP.");

            demonHP += demonArmor;
            orcHP += orcArmor;
}
        
        @Override
        public void playGame() {
            Scanner scan = new Scanner(System.in);

            do {
            System.out.println("\n" + getHeroType() + "'s HP: " + getPlayerHP() +
                " | Demon HP: " + (int)getDemonHP() +
                " | Orc HP: " + (int)getOrcHP() +
                " | Floor: " + currentFloor);

            System.out.print("Press 1 to Attack: ");
            int choice = scan.nextInt();

            if (choice == 1) {
            playerAttack();
            if (areOpponentsAlive() == 1) {
                opponentsAttack();
            }
        } 
          
            if (areOpponentsAlive() == 0) {
                if(currentFloor != 1){
                floorHPMultiplier += 0.1;
                resetOpponents();
                descendFloor();
                }
        } 
        // Check for level 
            if (experiencePoints >= experienceThreshold) {
                levelUp();
        }
        
            if(currentFloor >= 17 && currentFloor <= 10){ //floor 17 to 10
                floorDamage = 10; //damage modifier for the enemies
        }
        
            if(currentFloor >= 10 && currentFloor <= 1){ //floor 10 to 1
                floorDamage = 20;
        }

    } while (currentFloor >= 1 && isPlayerAlive() == 1 && areOpponentsAlive() == 1);
//Database
            if (currentFloor == 1) {
                System.out.println("You Escaped the Dungeon!");
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

        @Override
        public void playerAttack() {

            demonDamage = random.nextInt(40) + playerLevel * 5;
            orcDamage = random.nextInt(40) + playerLevel * 5;

            if (demonHP > 0) {
                demonHP = (demonHP > demonDamage) ? demonHP - demonDamage : 0;
                System.out.println("Mage dealt " + demonDamage + " damage to Demon.");
                if (demonHP == 0) {
                    System.out.println("Demon is defeated!");
                    experiencePoints += 30;
                    System.out.println("Gained 30 experience");
                }
            }

            if (orcHP > 0) {
                orcHP = (orcHP > orcDamage) ? orcHP - orcDamage : 0;
                System.out.println("Mage dealt " + orcDamage + " damage to Orc.");
                if (orcHP == 0) {
                    System.out.println("Orc is defeated!");
                    experiencePoints += 40;
                    System.out.println("Gained 40 experience!");
                }
            }
        }

        @Override
        public void opponentsAttack() {
            if (demonHP > 0 && orcHP > 0) {
                System.out.println("\nDemon and Orc attack Mage!");
            } else if (demonHP > 0) {
                System.out.println("\nDemon attacks Mage!");
            } else if (orcHP > 0) {
                System.out.println("\nOrc attacks Mage!");
            }

            if (demonHP > 0) {
                int demonDamage = random.nextInt( 40) + floorDamage;
                playerHP -= demonDamage;
                System.out.println("Demon dealt " + demonDamage + " damage to Mage.");
            }

            if (orcHP > 0) {
                int orcDamage = random.nextInt(  61) + floorDamage;
                playerHP -= orcDamage;
                System.out.println("Orc dealt " + orcDamage + " damage to Mage.");
            }
        }
        
         
         
         public void descendFloor() {
            if (currentFloor >= 1) {
                currentFloor--;
                System.out.println("You descended to floor " + currentFloor);
            } else {
                System.out.println("You are on the lowest floor. No more descending.");
            }
        }
        
//do not modify - default attributes    
        @Override
        public int isPlayerAlive() {
            return playerHP > 0 ? 1 : 0;
        }

        @Override
        public int areOpponentsAlive() {
            return (demonHP > 0 || orcHP > 0) ? 1 : 0;
        }

        @Override
        public int getPlayerHP() {
            return playerHP;
        }

        @Override
        public int getDemonHP() {
            return demonHP;
        }

        @Override
        public int getOrcHP() {
            return orcHP;
        }

        @Override
        public String getHeroType() {
            return "Mage";
        } 
    }
    
    static class Paladin3 implements TOMInterface3 {
        private int playerHP;
        private int armor;
        private int demonHP = 70;
        private int orcHP = 100;
        private double demonArmor;
        private double orcArmor;
        private int demonDamage;
        private int orcDamage;
        private int currentFloor = 10;
        private int experiencePoints = 0;
        private int experienceThreshold = 40; 
        private int playerLevel = 1;
        private double floorHPMultiplier = 0.5;
        private int floorDamage = 0;

        private Random random = new Random();
        
        public Paladin3() {
            playerHP = 100;
            armor = random.nextInt(31)+5;
            demonArmor = random.nextInt(31);
            orcArmor = random.nextInt(21);

            System.out.println("\nPaladin's Armor Activated. Mage gained " + armor + " Additional HP. ");
            playerHP += armor;

            System.out.println("Demon's Armor Activated. Goblin gained " + demonArmor + " Additional HP.");
            System.out.println("Orc's Armor Activated. Undead gained " + orcArmor + " Additional HP.");

            demonHP += demonArmor;
            orcHP += orcArmor;
            System.out.println("You are on floor " + currentFloor);
        }
        
        public void levelUp() {
  
            experienceThreshold += 30;
            playerHP += 20;
            playerLevel++;

            System.out.println("Congratulations! You leveled up to level " + playerLevel +
            ". Stats are increased!");

            // Reset experiencePoints
            experiencePoints = 0;
        }

        public void resetOpponents() {
            demonHP = 60;
            orcHP = 90;

            demonArmor = random.nextDouble(21) * floorHPMultiplier;
            orcArmor = random.nextDouble(11) * floorHPMultiplier;

            System.out.println("Demon's Armor Activated. Goblin gained " + (int)demonArmor + " Additional HP.");
            System.out.println("Orc's Armor Activated. Undead gained " + (int)orcArmor + " Additional HP.");

            demonHP += demonArmor;
            orcHP += orcArmor;
}
        
        @Override
        public void playGame() {
            Scanner scan = new Scanner(System.in);

            do {
            System.out.println("\n" + getHeroType() + "'s HP: " + getPlayerHP() +
                " | Demon HP: " + (int)getDemonHP() +
                " | Orc HP: " + (int)getOrcHP() +
                " | Floor: " + currentFloor);

            System.out.print("Press 1 to Attack: ");
            int choice = scan.nextInt();

            if (choice == 1) {
            playerAttack();
            if (areOpponentsAlive() == 1) {
                opponentsAttack();
            }
        } 
          
            if (areOpponentsAlive() == 0) {
                if(currentFloor != 1){
                floorHPMultiplier += 0.1;
                resetOpponents();
                descendFloor();
                }
        } 
        // Check for level 
            if (experiencePoints >= experienceThreshold) {
                levelUp();
        }
        
            if(currentFloor >= 17 && currentFloor <= 10){ //floor 17 to 10
                floorDamage = 10; //damage modifier for the enemies
        }
        
            if(currentFloor >= 10 && currentFloor <= 1){ //floor 10 to 1
                floorDamage = 20;
        }

    } while (currentFloor >= 1 && isPlayerAlive() == 1 && areOpponentsAlive() == 1);

            if (currentFloor == 1) {
                System.out.println("You Escaped the Dungeon!");
        }   else {
            System.out.println("\nGame Over!");
    }
}

        @Override
        public void playerAttack() {

            demonDamage = random.nextInt(20) + playerLevel * 2;
            orcDamage = random.nextInt(20) + playerLevel * 2;

            if (demonHP > 0) {
                demonHP = (demonHP > demonDamage) ? demonHP - demonDamage : 0;
                System.out.println("Paladin dealt " + demonDamage + " damage to Demon.");
                if (demonHP == 0) {
                    System.out.println("Demon is defeated!");
                    experiencePoints += 30;
                    System.out.println("Gained 30 experience");
                }
            }

            if (orcHP > 0) {
                orcHP = (orcHP > orcDamage) ? orcHP - orcDamage : 0;
                System.out.println("Paladin dealt " + orcDamage + " damage to Orc.");
                if (orcHP == 0) {
                    System.out.println("Orc is defeated!");
                    experiencePoints += 40;
                    System.out.println("Gained 40 experience!");
                }
            }
        }

        @Override
        public void opponentsAttack() {
            if (demonHP > 0 && orcHP > 0) {
                System.out.println("\nDemon and Orc attack Paladin!");
            } else if (demonHP > 0) {
                System.out.println("\nDemon attacks Paladin!");
            } else if (orcHP > 0) {
                System.out.println("\nOrc attacks Paladin!");
            }

            if (demonHP > 0) {
                int demonDamage = random.nextInt( 20) + floorDamage;
                playerHP -= demonDamage;
                System.out.println("Demon dealt " + demonDamage + " damage to Paladin.");
            }

            if (orcHP > 0) {
                int orcDamage = random.nextInt(  31) + floorDamage;
                playerHP -= orcDamage;
                System.out.println("Orc dealt " + orcDamage + " damage to Mage.");
            }
        }
        
         
         
         public void descendFloor() {
            if (currentFloor >= 1) {
                currentFloor--;
                System.out.println("You descended to floor " + currentFloor);
            } else {
                System.out.println("You are on the lowest floor. No more descending.");
            }
        }
        
//do not modify - default attributes    
        @Override
        public int isPlayerAlive() {
            return playerHP > 0 ? 1 : 0;
        }

        @Override
        public int areOpponentsAlive() {
            return (demonHP > 0 || orcHP > 0) ? 1 : 0;
        }

        @Override
        public int getPlayerHP() {
            return playerHP;
        }

        @Override
        public int getDemonHP() {
            return demonHP;
        }

        @Override
        public int getOrcHP() {
            return orcHP;
        }

        @Override
        public String getHeroType() {
            return "Paladin";
        } 
    }
    
    static class Beast3 implements TOMInterface3 {
        private int playerHP;
        private int armor;
        private int demonHP = 60;
        private int orcHP = 90;
        private double demonArmor;
        private double orcArmor;
        private int demonDamage;
        private int orcDamage;
        private int currentFloor = 10;
        private int experiencePoints = 0;
        private int experienceThreshold = 40; 
        private int playerLevel = 1;
        private double floorHPMultiplier = 0.5;
        private int floorDamage = 0;

        private Random random = new Random();
        
        public Beast3() {
            playerHP = 70;
            armor = random.nextInt(31);
            demonArmor = random.nextInt(31);
            orcArmor = random.nextInt(21);

            System.out.println("\nBeast's Armor Activated. Mage gained " + armor + " Additional HP. ");
            playerHP += armor;

            System.out.println("Demon's Armor Activated. Goblin gained " + demonArmor + " Additional HP.");
            System.out.println("Orc's Armor Activated. Undead gained " + orcArmor + " Additional HP.");

            demonHP += demonArmor;
            orcHP += orcArmor;
            System.out.println("You are on floor " + currentFloor);
        }
        
        public void levelUp() {
  
            experienceThreshold += 40;
            playerHP += 20;
            playerLevel++;

            System.out.println("Congratulations! You leveled up to level " + playerLevel +
            ". Stats are increased!");

            // Reset experiencePoints
            experiencePoints = 0;
        }

        public void resetOpponents() {
            demonHP = 60;
            orcHP = 90;

            demonArmor = random.nextDouble(31) * floorHPMultiplier;
            orcArmor = random.nextDouble(21) * floorHPMultiplier;

            System.out.println("Demon's Armor Activated. Goblin gained " + (int)demonArmor + " Additional HP.");
            System.out.println("Orc's Armor Activated. Undead gained " + (int)orcArmor + " Additional HP.");

            demonHP += demonArmor;
            orcHP += orcArmor;
}
        
        @Override
        public void playGame() {
            Scanner scan = new Scanner(System.in);

            do {
            System.out.println("\n" + getHeroType() + "'s HP: " + getPlayerHP() +
                " | Demon HP: " + (int)getDemonHP() +
                " | Orc HP: " + (int)getOrcHP() +
                " | Floor: " + currentFloor);

            System.out.print("Press 1 to Attack: ");
            int choice = scan.nextInt();

            if (choice == 1) {
            playerAttack();
            if (areOpponentsAlive() == 1) {
                opponentsAttack();
            }
        } 
          
            if (areOpponentsAlive() == 0) {
                if(currentFloor != 1){
                floorHPMultiplier += 0.1;
                resetOpponents();
                descendFloor();
                }
        } 
        // Check for level 
            if (experiencePoints >= experienceThreshold) {
                levelUp();
        }
        
            if(currentFloor >= 17 && currentFloor <= 10){ //floor 17 to 10
                floorDamage = 10; //damage modifier for the enemies
        }
        
            if(currentFloor >= 10 && currentFloor <= 1){ //floor 10 to 1
                floorDamage = 20;
        }

    } while (currentFloor >= 1 && isPlayerAlive() == 1 && areOpponentsAlive() == 1);

            if (currentFloor == 1) {
                System.out.println("You Escaped the Dungeon!");
        }   else {
            System.out.println("\nGame Over!");
    }
}

        @Override
        public void playerAttack() {

            demonDamage = random.nextInt(30) + playerLevel * 3;
            orcDamage = random.nextInt(30) + playerLevel * 3;

            if (demonHP > 0) {
                demonHP = (demonHP > demonDamage) ? demonHP - demonDamage : 0;
                System.out.println("Beast dealt " + demonDamage + " damage to Demon.");
                if (demonHP == 0) {
                    System.out.println("Demon is defeated!");
                    experiencePoints += 30;
                    System.out.println("Gained 30 experience");
                }
            }

            if (orcHP > 0) {
                orcHP = (orcHP > orcDamage) ? orcHP - orcDamage : 0;
                System.out.println("Beast dealt " + orcDamage + " damage to Orc.");
                if (orcHP == 0) {
                    System.out.println("Orc is defeated!");
                    experiencePoints += 40;
                    System.out.println("Gained 40 experience!");
                }
            }
        }

        @Override
        public void opponentsAttack() {
            if (demonHP > 0 && orcHP > 0) {
                System.out.println("\nDemon and Orc attack Beast!");
            } else if (demonHP > 0) {
                System.out.println("\nDemon attacks Beast!");
            } else if (orcHP > 0) {
                System.out.println("\nOrc attacks Beast!");
            }

            if (demonHP > 0) {
                int demonDamage = random.nextInt( 40) + floorDamage;
                playerHP -= demonDamage;
                System.out.println("Demon dealt " + demonDamage + " damage to Beast.");
            }

            if (orcHP > 0) {
                int orcDamage = random.nextInt(  61) + floorDamage;
                playerHP -= orcDamage;
                System.out.println("Orc dealt " + orcDamage + " damage to Beast.");
            }
        }
        
         
         
         public void descendFloor() {
            if (currentFloor >= 1) {
                currentFloor--;
                System.out.println("You descended to floor " + currentFloor);
            } else {
                System.out.println("You are on the lowest floor. No more descending.");
            }
        }
        
//do not modify - default attributes    
        @Override
        public int isPlayerAlive() {
            return playerHP > 0 ? 1 : 0;
        }

        @Override
        public int areOpponentsAlive() {
            return (demonHP > 0 || orcHP > 0) ? 1 : 0;
        }

        @Override
        public int getPlayerHP() {
            return playerHP;
        }

        @Override
        public int getDemonHP() {
            return demonHP;
        }

        @Override
        public int getOrcHP() {
            return orcHP;
        }

        @Override
        public String getHeroType() {
            return "Beast";
        } 
    }

}
  