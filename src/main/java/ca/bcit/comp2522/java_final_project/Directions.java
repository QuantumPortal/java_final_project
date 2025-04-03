package ca.bcit.comp2522.java_final_project;

public enum Directions
{
    UP(0.0),
    DOWN(Math.PI),
    LEFT(Math.PI / 2),
    RIGHT(3 * Math.PI / 2);

    public final Double value;

    private Directions(Double value)
    {
        this.value = value;
    }
}
