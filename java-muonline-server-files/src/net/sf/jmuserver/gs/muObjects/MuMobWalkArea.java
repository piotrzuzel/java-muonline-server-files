/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jmuserver.gs.muObjects;

/**
 *
 * @author Miki i Linka
 */
class MuMobWalkArea {

    private int _x1;
    private int _x2;
    private int _y1;
    private int _y2;
    private int _radius;

    public MuMobWalkArea(int x1, int y1, int x2, int y2, int rad) {
        _x1 = x1;
        _x2 = x2;
        _y1 = y1;
        _y2 = y2;
        _radius = rad;
    }

    public int getRandX(int actX) {
        int tx = tx = actX + ((int) (Math.random() * (_radius * 2)) - (_radius));
//        while (!((_x1<tx)&&(tx<_x2))) {            
//            tx = actX + ((int) (Math.random() * (_radius * 2)) - (_radius));
//            System.out.println("newx "+_x1+"<"+tx+"<"+_x2);
//        }
        return tx;
    }

    public int getRandY(int actY) {
        int ty  = actY + ((int) (Math.random() * (_radius * 2)) - (_radius));
//        while (!((_y1<ty)&&(ty<_y2))) {
//            ty = actY + ((int) (Math.random() * (_radius * 2)) - (_radius));
//            System.out.println("newy "+_y1+">"+ty+"<"+_y2);
//        }
        return ty;
    }
}
