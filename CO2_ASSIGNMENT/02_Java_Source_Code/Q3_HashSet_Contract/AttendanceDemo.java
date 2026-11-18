import java.util.*;

class BrokenStudent {
    final String regNo, name;
    BrokenStudent(String r, String n) { regNo=r; name=n; }
    public boolean equals(Object o) {
        return o instanceof BrokenStudent && regNo.equals(((BrokenStudent)o).regNo);
    }
    // hashCode deliberately missing
}
class Student {
    private final String regNo, name;
    Student(String r, String n) { regNo=r; name=n; }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s=(Student)o;
        return Objects.equals(regNo,s.regNo);
    }
    public int hashCode() { return Objects.hash(regNo); }
    public String toString() { return regNo+" "+name; }
}
class MutableStudent {
    String regNo;
    MutableStudent(String r) { regNo=r; }
    public boolean equals(Object o) {
        return o instanceof MutableStudent && Objects.equals(regNo,((MutableStudent)o).regNo);
    }
    public int hashCode() { return Objects.hash(regNo); }
}
public class AttendanceDemo {
    public static void main(String[] args) {
        System.out.println("=== Q3 HASHSET CONTRACT EXPERIMENT ===");
        Set<BrokenStudent> broken = new HashSet<>();
        broken.add(new BrokenStudent("192311001","Arun"));
        broken.add(new BrokenStudent("192311002","Divya"));
        broken.add(new BrokenStudent("192311001","Arun"));
        System.out.println("Broken class set size (hashCode missing): "+broken.size());
        System.out.println("Prediction: may retain duplicate logical students because equal objects can hash differently.");

        Set<Student> fixed = new HashSet<>();
        fixed.add(new Student("192311001","Arun"));
        fixed.add(new Student("192311002","Divya"));
        fixed.add(new Student("192311001","Arun"));
        System.out.println("Corrected class set size: "+fixed.size());
        System.out.println("Expected corrected size: 2");

        Set<Student> custom = new HashSet<>();
        custom.add(new Student("A100","Maya"));
        custom.add(new Student("A100","Maya repeated"));
        custom.add(new Student("A101","Nikhil"));
        custom.add(new Student("A102","Sara"));
        System.out.println("Custom duplicate-scan set size: "+custom.size()+" (expected 3)");

        Set<MutableStudent> changing = new HashSet<>();
        MutableStudent m = new MutableStudent("OLD");
        changing.add(m);
        m.regNo = "NEW";
        System.out.println("Mutable key found after changing regNo: "+changing.contains(m));
        System.out.println("Explanation: changing hash-relevant state after insertion can make lookup fail.");
    }
}
