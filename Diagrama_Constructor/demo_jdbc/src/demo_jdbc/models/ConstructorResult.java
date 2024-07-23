package demo_jdbc.models;

public class ConstructorResult {
    private String constructorName;
    private int wins;
    private int totalPoints;
    private int rank;

    public ConstructorResult(String constructorName, int wins, int totalPoints, int rank) {
        this.constructorName = constructorName;
        this.wins = wins;
        this.totalPoints = totalPoints;
        this.rank = rank;
    }

    public String getConstructorName() {
        return constructorName;
    }

    public int getWins() {
        return wins;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getRank() {
        return rank;
    }
}

