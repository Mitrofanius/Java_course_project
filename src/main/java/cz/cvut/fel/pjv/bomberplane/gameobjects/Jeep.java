package cz.cvut.fel.pjv.bomberplane.gameobjects;

import cz.cvut.fel.pjv.bomberplane.Main;

import java.awt.*;

/** Enemy's vehicle, can't shoot, moves fast
 * @see Vehicle
*/
 public class Jeep extends Vehicle{
  private int transferOffset = 30;


    public Jeep(Image pic, int x, int y, int speed){
   this.setPicture(pic);
   this.setPositionX(x);
   this.setPositionY(y);
   this.setSpeedX(speed);
   this.setSpeedY(0);
  }

  @Override
 public void move(){
   this.setPositionX(this.getPositionX() + this.getSpeedX());
   this.setPositionY(this.getPositionY() + this.getSpeedY());
   if ((this.getPositionX() + transferOffset) >= Main.panelWidth) {
    this.setSpeedX(-this.getSpeedX());
   }
   if ((this.getPositionX() + transferOffset) <= 0) {
    this.setSpeedX(-this.getSpeedX());
   }
  }
}
