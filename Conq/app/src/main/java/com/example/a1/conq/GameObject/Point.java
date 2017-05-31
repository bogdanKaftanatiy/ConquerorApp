package com.example.a1.conq.GameObject;


import java.io.Serializable;

public class Point  implements Serializable {

	private double x;
	private double y;
	
	public Point (double x,double y ){
		this.x=x;
		this.y=y;		
	}
	public Point() {
		x=0;
		y=0;
	}


	public double getX() {
		return x;
	}
	
	public void setX(double d) {
		x=d;
	}

	public double getY() {
		return y;
	}
	
	public void setY(double d) {
		y=d;
	}

	public void setLocation(double arg0, double arg1) {
		x=arg0;
		y =arg1;
		
	}
	public String toString(){
		String ss="X= "+x+" Y= "+y;
		return ss;
	}
}
