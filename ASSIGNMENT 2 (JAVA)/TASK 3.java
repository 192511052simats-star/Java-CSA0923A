
import java.util.HashSet;

class Student {
    String regNo;
    String name;

    Student(String regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Student))
            return false;

        Student s = (Student) obj;
        return regNo.equals(s.regNo);
    }

    // TODO 1: Complete this method
    public int hashCode() {
        return regNo.hashCode();
    }
}

class TASK_3 {

    public static void main(String[] args) {

        HashSet<Student> set1 = new HashSet<Student>();

        Student s1 = new Student("101", "Arun");

        set1.add(s1);
        set1.add(s1);

        System.out.println("TEST 1: Same Object");
        System.out.println("Set Size: " + set1.size());

        HashSet<Student> set2 = new HashSet<Student>();

        set2.add(new Student("102", "Bala"));
        set2.add(new Student("102", "Bala"));

        System.out.println("\nTEST 2: Duplicate Student");
        System.out.println("Set Size: " + set2.size());

        HashSet<Student> set3 = new HashSet<Student>();

        Student s3 = new Student("103", "Charan");
        set3.add(s3);

        System.out.println("\nTEST 3: Change Registration Number");

        s3.regNo = "104";

        System.out.println("Contains after change: "
                + set3.contains(s3));

        System.out.println("Set Size: " + set3.size());
    }
}
