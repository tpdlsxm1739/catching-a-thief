package Student;
import java.awt.Point;
import javax.swing.ImageIcon;
import java.net.*;
import java.awt.image.*;
import java.awt.*;

public class PosImageIcon extends ImageIcon {
	public int x;				// 모양의 위치 좌표
	public int y;				// 모양의 위치 좌표
	private int initX, initY; 	// 초기시작 x,y좌표
	protected int xDirection;
	protected int yDirection;
	protected int xBoundary;    //그림이 이동할 수 있는 좌표의 최대값
	protected int yBoundary;    //그림이 이동할 수 있는 좌표의 최대값
    protected double steps;
	protected int margin;		//이 모양의 영역이 포함되는 영역을 나타내기 위함
	
	public PosImageIcon(String imgURL, int x, int y, int margin, double steps, int xBoundary, int yBoundary) {
		// imgPath : 그림 파일의 경로명
		// x, y : 이미지의 위치 좌표
		// margin : 이 이미지의 영역을 나타내는 범위(이 영역안에 있으면 충돌 한 것으로 판단 하기 위함)		
		// steps : 이미지가 움직일때 이동하는 좌표 단위
		// xBoundary, yBoundary : 그림이 이동할 수 있는 좌표의 최대값
		super(imgURL);
		this.x = x;
		this.initX = x;
		this.y = y;
		this.initY = y;
		this.margin = margin;
		this.xDirection = 1;
		this.yDirection = 1;
		this.steps = steps;
		this.xBoundary = xBoundary;
		this.yBoundary = yBoundary;
	}

	// 시작 위치를 임의의 포인트로 주는 구성자
	public PosImageIcon(String imgURL, int margin, double  steps, int xBoundary, int yBoundary) {
		this (imgURL, 0, 0, margin, steps, xBoundary, yBoundary);
		x= (int) (Math.random() * xBoundary+1);
		y= (int) (Math.random() * yBoundary+1);
	}
	
	//시작위치를 주어진 바운더리 중앙에 위치시키는 구성자
	public PosImageIcon(String imgURL, int margin, int xBoundary, int yBoundary) {
		this (imgURL, xBoundary/2, yBoundary, margin, margin, xBoundary, yBoundary);
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public void setMargin(int margin) {
		this.margin = margin;
	}
	
	public int getMargin() {
		return margin;
	}
	
	//하나의 점이 이 모양과 충돌하였는지(모양의 margin 거리안에 있는지)를 판단하는 함수
	public boolean collide (Point p2) {
		Point p = new Point(this.x, this.y);
		if (p.distance(p2) <= margin-20) return true;
		return false;
	}
	
	public void reset() {
		x = initX; y= initY;
	}
	
	//해당 모양을 g에 출력해주는 메소드
	public void draw(Graphics g, ImageObserver io) {
		((Graphics2D)g).drawImage(this.getImage(), x, y, margin, margin, io);
	}

	//이 부분을 상속한 다양한 객체의 모션이 일어날 수 있도록 조정하기
	public void move() {};
}
