package com.example.a1.conq.GameObject;

import android.content.Context;
import android.widget.ImageView;
import com.example.a1.conq.GameActivity;

import java.io.*;
import java.util.ArrayList;


public class Map implements Serializable {
    ArrayList<GrafObject> areas;
    ArrayList<GrafObject> areasFroOwnUse;
    int height;
    int width;

    public ArrayList<GrafObject> getAreas() {
        return areas;
    }
    public void setChipsArea(ArrayList<ImageView> chipsArea){

        for(int i=0;i<areas.size();i++){
            areas.get(i).setChip(chipsArea.get(i));
        }
    }
    public Map() {
        this.areas = new ArrayList<>();
        Point p1 =new Point(225,245);
        Point p2 =new Point(527 ,350);
        Point p3 =new Point(530 ,427);
        Point p4 =new Point(370 ,419);
        Point p5 =new Point(213 ,513);
        Point p6 =new Point(116 ,450);
        Point p7 =new Point(162 ,413);
        Point p8 =new Point(96 ,370);
        Point p9 =new Point(747 ,382);
        Point p10 =new Point(830 ,200);

        Point p11 =new Point(393 ,111);
        Point p12 =new Point(316 ,271);
        Point p13 =new Point(1027 ,228);
        Point p14 =new Point(941 ,419);
        Point p15 =new Point(1141 ,465);
        Point p16 =new Point(1332 ,239);
        Point p17 =new Point(1554 ,148);
        Point p18 =new Point(1583 ,502);
        Point p19 =new Point(1278 ,499);
        Point p20 =new Point(1743 ,502);

        Point p21 =new Point(1706 ,148);
        Point p22 =new Point(1817 ,499);
        Point p23 =new Point(1820 ,678);
        Point p24 =new Point(1500 ,690);
        Point p25 =new Point(1486 ,502);
        Point p26 =new Point(1509 ,810 );
        Point p27 =new Point(1278 ,818);
        Point p28 =new Point(1278 ,927);
        Point p29 =new Point(1029 ,918);
        Point p30 =new Point(1021 ,718);

        Point p31 =new Point(1032 ,527);
        Point p32 =new Point(770 ,696);
        Point p33 =new Point(730 ,701);
        Point p34 =new Point(730 ,738);
        Point p35 =new Point(738 ,892);
        Point p36 =new Point(604 ,884);
        Point p37 =new Point(482 ,898);
        Point p38 =new Point(453 ,716 );
        Point p39 =new Point(564 ,710);
        Point p40 =new Point(251 ,718);

        Point p41 =new Point(222 ,639);
        Point p42 =new Point(136 ,562);

        ArrayList<Point> points1 = new ArrayList<>();
        points1.add(p1);
        points1.add(p2);
        points1.add(p3);
        points1.add(p4);
        points1.add(p5);
        points1.add(p6);
        points1.add(p7);
        points1.add(p8);
        GrafObject grafObject1 =new GrafObject(points1,1);

        ArrayList<Point> points2 = new ArrayList<>();
        points2.add(p9);
        points2.add(p10);
        points2.add(p11);
        points2.add(p12);
        GrafObject grafObject2 =new GrafObject(points2,2);

        ArrayList<Point> points3 = new ArrayList<>();
        points3.add(p9);
        points3.add(p10);
        points3.add(p13);
        points3.add(p14);
        GrafObject grafObject3 =new GrafObject(points3,3);

        ArrayList<Point> points4 = new ArrayList<>();
        points4.add(p15);
        points4.add(p16);
        points4.add(p13);
        points4.add(p14);
        GrafObject grafObject4 =new GrafObject(points4,4);

        ArrayList<Point> points5 = new ArrayList<>();
        points5.add(p15);
        points5.add(p16);
        points5.add(p17);
        points5.add(p18);
        points5.add(p19);
        GrafObject grafObject5 =new GrafObject(points5,5);

        ArrayList<Point> points6 = new ArrayList<>();
        points6.add(p17);
        points6.add(p18);
        points6.add(p20);
        points6.add(p21);
        GrafObject grafObject6 =new GrafObject(points6,6);

        ArrayList<Point> points7 = new ArrayList<>();
        points7.add(p22);
        points7.add(p23);
        points7.add(p24);
        points7.add(p25);
        GrafObject grafObject7 =new GrafObject(points7,7);

        ArrayList<Point> points8 = new ArrayList<>();
        points8.add(p19);
        points8.add(p25);
        points8.add(p26);
        points8.add(p27);
        GrafObject grafObject8 =new GrafObject(points8,8);

        ArrayList<Point> points9 = new ArrayList<>();
        points9.add(p15);
        points9.add(p19);
        points9.add(p28);
        points9.add(p29);
        points9.add(p30);
        points9.add(p31);
        GrafObject grafObject9 =new GrafObject(points9,9);

        ArrayList<Point> points10 = new ArrayList<>();
        points10.add(p30);
        points10.add(p31);
        points10.add(p32);
        points10.add(p33);
        points10.add(p34);
        GrafObject grafObject10 =new GrafObject(points10,10);

        ArrayList<Point> points11 = new ArrayList<>();
        points11.add(p29);
        points11.add(p30);
        points11.add(p34);
        points11.add(p35);
        GrafObject grafObject11 =new GrafObject(points11,11);

        ArrayList<Point> points12 = new ArrayList<>();
        points12.add(p33);
        points12.add(p35);
        points12.add(p36);
        points12.add(p37);
        points12.add(p38);
        GrafObject grafObject12 =new GrafObject(points12,12);

        ArrayList<Point> points13 = new ArrayList<>();
        points13.add(p9);
        points13.add(p14);
        points13.add(p15);
        points13.add(p32);
        GrafObject grafObject13 =new GrafObject(points13,13);

        ArrayList<Point> points14 = new ArrayList<>();
        points14.add(p2);
        points14.add(p9);
        points14.add(p32);
        points14.add(p39);
        GrafObject grafObject14 =new GrafObject(points14,14);

        ArrayList<Point> points15 = new ArrayList<>();
        points15.add(p3);
        points15.add(p4);
        points15.add(p5);
        points15.add(p42);
        points15.add(p41);
        points15.add(p40);
        points15.add(p38);
        points15.add(p39);
        GrafObject grafObject15 =new GrafObject(points15,15);

        grafObject1.setCenter(new Point(288,356));
        grafObject2.setCenter(new Point(581 ,236));
        grafObject3.setCenter(new Point(904 ,296));
        grafObject4.setCenter(new Point(1126 ,336));
        grafObject5.setCenter(new Point(1409 ,373));
        grafObject6.setCenter(new Point(1646 ,313));
        grafObject7.setCenter(new Point(1646 ,587));
        grafObject8.setCenter(new Point(1380 ,681));
        grafObject9.setCenter(new Point(1158 ,721));
        grafObject10.setCenter(new Point(932 ,696));
        grafObject11.setCenter(new Point(892 ,853));
        grafObject12.setCenter(new Point(601 ,790));
        grafObject13.setCenter(new Point(881 ,542));
        grafObject14.setCenter(new Point(647 ,522));
        grafObject15.setCenter(new Point(382 ,553));

        grafObject1.addNearbyArea(grafObject2);
        grafObject1.addNearbyArea(grafObject14);
        grafObject1.addNearbyArea(grafObject15);

        grafObject2.addNearbyArea(grafObject1);
        grafObject2.addNearbyArea(grafObject14);
        grafObject2.addNearbyArea(grafObject3);

        grafObject3.addNearbyArea(grafObject2);
        grafObject3.addNearbyArea(grafObject14);
        grafObject3.addNearbyArea(grafObject13);
        grafObject3.addNearbyArea(grafObject14);

        grafObject4.addNearbyArea(grafObject3);
        grafObject4.addNearbyArea(grafObject13);
        grafObject4.addNearbyArea(grafObject5);

        grafObject5.addNearbyArea(grafObject4);
        grafObject5.addNearbyArea(grafObject9);
        grafObject5.addNearbyArea(grafObject8);
        grafObject5.addNearbyArea(grafObject7);
        grafObject5.addNearbyArea(grafObject6);

        grafObject6.addNearbyArea(grafObject5);
        grafObject6.addNearbyArea(grafObject7);

        grafObject7.addNearbyArea(grafObject5);
        grafObject7.addNearbyArea(grafObject6);
        grafObject7.addNearbyArea(grafObject8);

        grafObject8.addNearbyArea(grafObject5);
        grafObject8.addNearbyArea(grafObject7);
        grafObject8.addNearbyArea(grafObject9);

        grafObject9.addNearbyArea(grafObject8);
        grafObject9.addNearbyArea(grafObject5);
        grafObject9.addNearbyArea(grafObject13);
        grafObject9.addNearbyArea(grafObject10);
        grafObject9.addNearbyArea(grafObject11);

        grafObject10.addNearbyArea(grafObject9);
        grafObject10.addNearbyArea(grafObject11);
        grafObject10.addNearbyArea(grafObject12);
        grafObject10.addNearbyArea(grafObject14);
        grafObject10.addNearbyArea(grafObject13);

        grafObject11.addNearbyArea(grafObject9);
        grafObject11.addNearbyArea(grafObject10);
        grafObject11.addNearbyArea(grafObject12);

        grafObject12.addNearbyArea(grafObject11);
        grafObject12.addNearbyArea(grafObject10);
        grafObject12.addNearbyArea(grafObject13);
        grafObject12.addNearbyArea(grafObject14);
        grafObject12.addNearbyArea(grafObject15);

        grafObject13.addNearbyArea(grafObject3);
        grafObject13.addNearbyArea(grafObject4);
        grafObject13.addNearbyArea(grafObject9);
        grafObject13.addNearbyArea(grafObject10);
        grafObject13.addNearbyArea(grafObject14);

        grafObject14.addNearbyArea(grafObject1);
        grafObject14.addNearbyArea(grafObject2);
        grafObject14.addNearbyArea(grafObject3);
        grafObject14.addNearbyArea(grafObject13);
        grafObject14.addNearbyArea(grafObject10);
        grafObject14.addNearbyArea(grafObject12);
        grafObject14.addNearbyArea(grafObject15);

        grafObject15.addNearbyArea(grafObject1);
        grafObject15.addNearbyArea(grafObject14);
        grafObject15.addNearbyArea(grafObject12);
        areas.add(grafObject1);
        areas.add(grafObject2);
        areas.add(grafObject3);
        areas.add(grafObject4);
        areas.add(grafObject5);
        areas.add(grafObject6);
        areas.add(grafObject7);
        areas.add(grafObject8);
        areas.add(grafObject9);
        areas.add(grafObject10);
        areas.add(grafObject11);
        areas.add(grafObject12);
        areas.add(grafObject13);
        areas.add(grafObject14);
        areas.add(grafObject15);

        areasFroOwnUse = new ArrayList<>() ;
        areasFroOwnUse.add(grafObject1);
        areasFroOwnUse.add(grafObject2);
        areasFroOwnUse.add(grafObject3);
        areasFroOwnUse.add(grafObject4);
        areasFroOwnUse.add(grafObject5);
        areasFroOwnUse.add(grafObject6);
        areasFroOwnUse.add(grafObject7);
        areasFroOwnUse.add(grafObject8);
        areasFroOwnUse.add(grafObject9);
        areasFroOwnUse.add(grafObject10);
        areasFroOwnUse.add(grafObject11);
        areasFroOwnUse.add(grafObject12);
        areasFroOwnUse.add(grafObject13);
        areasFroOwnUse.add(grafObject14);
        areasFroOwnUse.add(grafObject15);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int touch(int x, int y){
        for(int i=0; i<areasFroOwnUse.size();i++){
            int result = areasFroOwnUse.get(i).ifIn(x,y);
            if (result>0) return result;
        }
        return  0;
    }
}
