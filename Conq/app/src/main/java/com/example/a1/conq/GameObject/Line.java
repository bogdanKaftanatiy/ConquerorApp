package  com.example.a1.conq.GameObject;


import java.io.Serializable;

public class Line implements Serializable {

	private Point p1,p2;
	
	public Line(){
		p1 = new Point();
		p2 =new Point();
	}
	public Line(Point pq1,Point pq2){
		p1 = pq1;
		p2 = pq2;
	}
	public void setp1(Point p ){p1=p;}
	public void setp2(Point p ){p1=p;}
	public Line copy(){
		return new Line(p1.getX(),p1.getX(),p2.getX(),p2.getY());
	}
	public Line(double x1, double y1, double x2, double y2) {
		p1 =new Point(x1, y1);
		p2=new Point(x2, y2);
	}
	
	public double interHor(double y){
		y+=0.5;
		
		if(((this.getY1()>=y)&&(this.getY2()<=y))||((this.getY1()<=y)&&(this.getY2()>=y)))
			return (int)((y-this.getY1())/(this.getY2()-this.getY1())*(this.getX2()-this.getX1()) + this.getX1()+0.5);
		
		return 0.5;
		
	}
	

	public boolean clickON(int x, int y , int disp){
		int k;
		
		if((x-this.getX1())*(x-this.getX2())>0)
			if((Math.abs(x-this.getX1())>disp)&&(Math.abs(x-this.getX2())>disp)) return false;
		
		if((y-this.getY1())*(y-this.getY2())>0)
			if((Math.abs(y-this.getY1())>disp)&&(Math.abs(y-this.getY2())>disp)) return false;
		
		k =(int)((y-this.getY1())*(this.getX2()-this.getX1())/(this.getY2()-this.getY1()+0.000001) + this.getX1()+0.5);
		if ((Math.abs(x-k)<5)) return true;
		k =(int)((x-this.getX1())*(this.getY2()-this.getY1())/(this.getX2()-this.getX1()+0.000001) + this.getY1()+0.5);
		if (Math.abs(y-k)<5) return true;
			
		return false;
	}

	public Point getP1() {
		return p1;
	}
	public void setP1( Point p) {
		p1=p;
	}

	public Point getP2() {
		return p2;
	}
	public void setP2(Point p) {
		p2=p;
	}

	public double getX1() {
		return p1.getX();
	}


	public double getX2() {
		return p2.getX();
	}


	public double getY1() {
		return p1.getY();
	}


	public double getY2() {
		return p2.getY();
	}


	public void setLine(double x1, double y1, double x2, double y2) {
		p1.setLocation(x1, y1);
		p2.setLocation(x2, y2);
	}
	public void setLine2(Point p1,Point  p2) {
		this.p1=p1;
		this.p2=p2;
	}
	public String toString(){
		String s = p1.getX()+" "+p1.getY()+" "+p2.getX()+" "+p2.getY()+" ";
		return s;
	}

}
