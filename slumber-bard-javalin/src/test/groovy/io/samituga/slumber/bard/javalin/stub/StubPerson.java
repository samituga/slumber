package io.samituga.slumber.bard.javalin.stub;

public class StubPerson {


    private String name;
    private int age;

    public StubPerson() {
    }

    // Setters and getters have this names so jackson doesn't recognize them automatically
    public void setP1(String p1) {
        this.name = p1;
    }

    public void setP2(int p2) {
        this.age = p2;
    }

    public String getP1() {
        return name;
    }

    public int getP2() {
        return age;
    }
}
