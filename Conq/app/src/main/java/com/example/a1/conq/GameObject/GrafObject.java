package  com.example.a1.conq.GameObject;


import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

public class GrafObject implements Serializable {

	boolean castle;
	ImageView chip;
	private ArrayList<Line> lines;
	private ArrayList<GrafObject> nearbyAreas;
	private int number;
	private Point center;
	public GrafObject(){
		number =-1;
		lines=new ArrayList<>();
		nearbyAreas=new ArrayList<>();

	}

	public ImageView getChip() {
		return chip;
	}

	public void setChip(ImageView chip) {
		this.chip = chip;

	}

	public int getNumber() {
		return number;
	}

	public boolean isCastle() {
		return castle;
	}

	public void setCastle(boolean castle) {
		this.castle = castle;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public void addNearbyArea(GrafObject g){
		if(!nearbyAreas.contains(g)) nearbyAreas.add(g);
	}

	public void removeNearvyArea(GrafObject g){
		nearbyAreas.remove(g);
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		GrafObject that = (GrafObject) o;

		if (number != that.number) return false;
		return lines != null ? lines.equals(that.lines) : that.lines == null;
	}

	@Override
	public int hashCode() {
		int result = lines != null ? lines.hashCode() : 0;
		result = 31 * result + number;
		return result;
	}

	public GrafObject(ArrayList<Point> points, int num){
		number =num;
		lines=new ArrayList<>();
		nearbyAreas=new ArrayList<>();
		if(points.size()>2){
			points.add(points.get(0));
			for(int i =0;i<points.size()-1;i++){
				lines.add(new Line(points.get(i),points.get(i+1)));
			}
		}

	}

	public int ifIn(int x1, int y1){
		ArrayList<Line> forDraw=masPer(y1);
		for(int i=0;i<forDraw.size();i+=1){
			if ((x1>forDraw.get(i).getX1())&&(x1<forDraw.get(i).getX2())) return number;
		}

		return 0;
	}

	public ArrayList<Line> masPer(double k){

		ArrayList <Point> mas=new ArrayList<Point>();

		double temp;
		for(int i=0;i<lines.size();i++){
			temp=lines.get(i).interHor(k);
			if (temp!=0.5) mas.add(new Point(temp,k));
		}

		ArrayList<Line>  toRet=new ArrayList<Line>();

		Point t;
		for(int i =0;i<mas.size();i++)
			for(int j=i+1;j<mas.size();j++)
				if(mas.get(i).getX()>mas.get(j).getX()){
					t = mas.get(i);
					mas.set(i, mas.get(j));
					mas.set(j, t);
				}

		for(int i=0;i<mas.size();i+=2)
			toRet.add(new Line(mas.get(i),mas.get(i+1)));



		return toRet;
	}




}
