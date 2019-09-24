package byog.Core;

public class TestGame {
    public static void main(String[] args) {
        //Game g1 = new Game();
        //g1.playWithInputString("N19S");
        Game g2 = new Game();
        g2.playWithInputString("N9sddsdsd");
        //System.out.println(g1.initialMovement);
        System.out.println(g2.initialMovement);
        //g1.mapGen.drawMap();
        g2.mapGen.drawMap();

        //System.out.println(g1.mapGen.initialXPos + " " + g1.mapGen.initialYPos);
        System.out.println(g2.mapGen.initialXPos + " " + g2.mapGen.initialYPos);
        // g.playWithInputString("n7341909481878015308s");
    }

}
