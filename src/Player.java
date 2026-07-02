/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Kiara
 */
public class Player {
    
    private String name;
    private int stage;
    private int rank;

    public Player(String name, int stage) {
        this.name = name;
        this.stage = stage;
    
}
    public String getName() {
        return name;
    }

    public int getStage() {
        return stage;
    }
     public int getRank() {
        return rank;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
    
     public void setRank(int rank) {
        this.rank = rank;
    }
    
}
