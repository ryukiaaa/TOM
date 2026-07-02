//TOMain2.java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
       System.out.println ("[ Welcome to Tower of Madness!! ] \nversion 1.0.0");
        System.out.println();
         System.out.println("In the mystical realm, a towering structure called the Tower of Madness holds a coveted artifact.\nThe character's search for the artifact came to a stop when they got nabbed and locked in a cage in the last and the 22nd floor.\nPlayers choose a Mage, Paladin, or Beast seeking power. \nTheir goal for now is to survive, after they face goblins, lancers, demons, orcs, gargoyles, and undead.\n" +
"\n" +
"As they descend, the tower's dark history unfolds. Each floor is a perilous dimension.\nThe goal: reach the 1st floor and confront the final boss, an ancient entity. \n" +
"\n" +
"The final battle tests the player's skills against dark powers\nand the spirits of fallen adventurers. Only the bravest will escape with the artifact, \nshaping the fate of the realm.\n");
        
        System.out.print("Press 1 if you're ready and 2 to if you're afraid: ");
        int choice = scanner.nextInt();
       
        System.out.println("\n==================================");
        if (choice == 1) {
     do{   
        
        System.out.println("Choose your Champion!");
        
        System.out.println("1. Mage");
        System.out.println("2. Paladin");
        System.out.println("3. Beast");
        int l;
        for( l =10; l>1;l++){
            System.out.println(l+" ");
            l+=1;
        }
        System.out.print("\nInput thy Choice: "+l);
        int heroChoice = scanner.nextInt();
        
        System.out.println("\n===============================");
        System.out.println("Choose your Difficulty:");
        System.out.println("[I] EASY");
        System.out.println("[II] STANDARD");
        System.out.println("[III] IMPOSSIBLE");
        
        System.out.print("\nInput your Decision, Adventurer: " );
        int difficulty = scanner.nextInt();
        
        
        
    
        TOMechanics game = null ;
        TOMechanics2 game2 = null ;
        TOMechanics3 game3 = null ;
        if (heroChoice == 1&&difficulty == 1) {
            System.out.println("");
            game = new TOMechanics(new TOMechanics.TOMage());
        } else if (heroChoice == 2&&difficulty == 1) {
            game = new TOMechanics(new TOMechanics.TOPaladin());
        } else if (heroChoice == 3&&difficulty ==1){
            game = new TOMechanics(new TOMechanics.TOBeast());
        }else if (heroChoice == 1&&difficulty == 2){
            game2 = new TOMechanics2(new TOMechanics2.Mage2());
        }else if (heroChoice == 1&&difficulty == 3){
            game3 = new TOMechanics3(new TOMechanics3.Mage3());
        }else if (heroChoice == 2&&difficulty == 2){
            game2 = new TOMechanics2(new TOMechanics2.Paladin2());
        }else if (heroChoice == 2&&difficulty == 3){
            game3 = new TOMechanics3(new TOMechanics3.Paladin3());
        }else if (heroChoice == 3&&difficulty == 2){
            game2 = new TOMechanics2(new TOMechanics2.Beast2());
        }else if (heroChoice == 3&&difficulty == 3){
            game3 = new TOMechanics3(new TOMechanics3.Beast3());
        }
        else {
            System.out.println("Invalid Choice!");
            return;
        }
        if(game!=null){
        game.playGame();
        } 
        if (game2!=null) {
            game2.playGame2();
        }
        if (game3!=null) {
            game3.playGame3();
        }
        
            System.out.println("Do you want to play again[1 for yes || 0 for no]?");
            int playAgain = scanner.nextInt();
            if(playAgain == 0){
                System.out.println("Goodbye!");
                break;

            }
            
        }while(true);
        } else if (choice == 2) {
           System.out.println("Thank you for checking out!");
        }   
        
      } 
    

}