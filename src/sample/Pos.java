package sample;

public class Pos {
    private int x;
    private int y;
    private int[] coord;
    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
        this.coord = new int[]{x, y};
    }

    public Pos(int[] p) {
        coord = new int[2];
        coord[0] = p[0];
        coord[1] = p[1];
        x = p[0];
        y = p[1];
    }

    public int[] getPos() {
        return coord;
    }

    public void setPos(int[] p) {
        coord[0] = p[0];
        coord[1] = p[1];
        this.x = p[0];
        this.y = p[1];
    }

    public void setPos(int x, int y) {
        coord[0] = x;
        coord[1] = y;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
