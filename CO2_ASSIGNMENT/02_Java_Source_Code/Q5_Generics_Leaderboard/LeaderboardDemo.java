import java.util.*;

class Team {
    final String name; final int points; final int goalDiff;
    Team(String n,int p,int g){name=n;points=p;goalDiff=g;}
    public String toString(){return name+"("+points+","+goalDiff+")";}
}
class Leaderboard<T> {
    private final List<T> items=new ArrayList<>();
    private final Comparator<? super T> order;
    Leaderboard(Comparator<? super T> c){order=c;}
    void add(T item){items.add(item);}
    List<T> ranking(){
        List<T> copy=new ArrayList<>(items);
        copy.sort(order);
        return copy;
    }
}
public class LeaderboardDemo {
    public static void main(String[] args) {
        Comparator<Team> byRank =
            Comparator.comparingInt((Team t)->t.points).reversed()
                .thenComparing(Comparator.comparingInt((Team t)->t.goalDiff).reversed())
                .thenComparing(t->t.name);
        Leaderboard<Team> board=new Leaderboard<>(byRank);
        board.add(new Team("Panthers",9,4));
        board.add(new Team("Eagles",9,6));
        board.add(new Team("Cobras",6,1));
        board.add(new Team("Bisons",9,4));
        System.out.println("=== Q5 GENERIC LEADERBOARD ===");
        System.out.println("Ranking: "+board.ranking());
        System.out.println("Bisons is above Panthers because points and goal difference tie, then Bisons comes first alphabetically.");

        List<Team> tieHeavy=Arrays.asList(
            new Team("Zebras",12,5),new Team("Aces",12,5),
            new Team("Rockets",12,7),new Team("Falcons",10,9));
        List<Team> sorted=new ArrayList<>(tieHeavy);
        sorted.sort(byRank);
        System.out.println("Tie-heavy ranking: "+sorted);

        Comparator<Team> faulty=(a,b)->b.points-a.points; // ignores later tie-breakers; subtraction can overflow
        List<Team> faultySorted=new ArrayList<>(tieHeavy);
        faultySorted.sort(faulty);
        System.out.println("Faulty comparator sample (ignores goal difference/name): "+faultySorted);
    }
}
