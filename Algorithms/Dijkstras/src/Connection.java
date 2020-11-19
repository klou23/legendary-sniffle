import java.awt.*;

public class Connection {
    Node node1;
    Node node2;
    int weight;
    Color c;

    public Connection(Node node1, Node node2, int weight, Color c) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
        this.c = c;
    }

    public void draw(Graphics g){
        g.setColor(c);
        g.drawLine(node1.x, node1.y, node2.x, node2.y);
        int textX = (int) (0.75*node1.x + 0.25*node2.x);
        int textY = (int) (0.75*node1.y + 0.25*node2.y);
        if(Math.abs(node1.x - node2.x) < 10){
            textX += 5;
        }else if(node2.y > node1.y && node2.x < node1.x){
            textY += 15;
        }else{
            textY -= 5;
        }

        g.drawString(Integer.toString(weight), textX, textY);
        g.setColor(Color.RED);
        g.fillRect((int) (0.75*node1.x + 0.25*node2.x), (int) (0.75*node1.y + 0.25*node2.y), 2, 2);
    }
}
