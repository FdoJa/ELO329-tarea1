public class Person {

    public Person(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void moveX(float direction){
        if (this.x + direction >= 0.0) {
            this.x += direction;
        }
    }

    public void moveY(float direction){
        if (this.y + direction >= 0.0){
            this.y += direction;
        }
    }

    private float x,y;
}
