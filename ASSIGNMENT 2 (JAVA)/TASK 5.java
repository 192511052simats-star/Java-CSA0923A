
import java.util.*;

class Team {
    String name;
    int points;
    int goalDifference;

    Team(String name, int points, int goalDifference) {
        this.name = name;
        this.points = points;
        this.goalDifference = goalDifference;
    }

    public String toString() {
        return name + " - Points: " + points
            + ", Goal Difference: " + goalDifference;
    }
}

class Leaderboard<T> {

    List<T> teams;

    Leaderboard(List<? extends T> list) {
        teams = new ArrayList<T>(list);
    }

    void rank(Comparator<? super T> comparator) {
        Collections.sort(teams, comparator);
    }

    void display() {
        for (T item : teams) {
            System.out.println(item);
        }
    }
}

class TASK_5 {

    public static void main(String[] args) {

        Comparator<Team> ranking =
            Comparator.comparingInt((Team t) -> t.points)
            .reversed()
            .thenComparing(
                Comparator.comparingInt(
                    (Team t) -> t.goalDifference
                ).reversed()
            )
            .thenComparing(t -> t.name);

        List<Team> teams = new ArrayList<Team>();

        teams.add(new Team("Panthers", 20, 8));
        teams.add(new Team("Bisons", 20, 12));
        teams.add(new Team("Tigers", 18, 10));
        teams.add(new Team("Lions", 20, 12));
        teams.add(new Team("Eagles", 18, 10));

        Leaderboard<Team> board =
            new Leaderboard<Team>(teams);

        System.out.println("SPORTS LEADERBOARD");
        board.rank(ranking);
        board.display();

        // Second test dataset
        List<Team> test2 = new ArrayList<Team>();

        test2.add(new Team("Zebras", 15, 5));
        test2.add(new Team("Apes", 15, 5));
        test2.add(new Team("Bears", 16, 2));

        Leaderboard<Team> board2 =
            new Leaderboard<Team>(test2);

        System.out.println("\nSECOND TEST");
        board2.rank(ranking);
        board2.display();
    }
}
