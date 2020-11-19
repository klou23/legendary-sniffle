import java.awt.*;

public class Node {
    int id;
    int val;
    int x;
    int y;
    Color c;

    public Node(int id, int val, int x, int y, Color c) {
        this.id = id;
        this.val = val;
        this.x = x;
        this.y = y;
        this.c = c;
    }

    public void draw(Graphics g){
        g.setColor(c);
        g.fillOval(x-25, y-25, 50, 50);
        if(val == Integer.MAX_VALUE){
            g.drawString("Inf", x-7, y-30);
        }else{
            g.drawString(Integer.toString(val), x-7, y-30);
        }
        g.setColor(getContrastColor(c));
        g.drawString(Integer.toString(id), x-5, y+5);
    }

    public Color getContrastColor(Color color) {
        double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
        return y >= 128 ? Color.black : Color.white;
    }
}
