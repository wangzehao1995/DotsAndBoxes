package wzhkun.dotsandboxes.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import wzhkun.dotsandboxes.view.DoubleActivity;

@SuppressLint("ClickableViewAccessibility") 
public class Board extends View {
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	private static final float radius = (float) 14 / 824;
	private static final float start = (float) 6 / 824;
	private static final float stop = (float) 819 / 824;
	private static final float add1 = (float) 18 / 824;
	private static final float add2 = (float) 2 / 824;
	private static final float add3 = (float) 14 / 824;
	private static final float add4 = (float) 141 / 824;
	private static final float add5 = (float) 159 / 824;
	private static final float add6 = (float) 9 / 824;
	public int[][] occupied;
	public int[][] horizontal;
	public int[][] vertical;
	public int playernow;
	private DoubleActivity activity;
	private Move move;
	private Paint paint;
	private int width;
	private int hight;

	public Board(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		paint = new Paint();
		paint.setAntiAlias(true);
		horizontal = new int[6][5];
		vertical = new int[5][6];
		occupied = new int[5][5];

	}

	public void add(Move move) {
		int type = move.direction;
		int a = move.a;
		int b = move.b;
		int player = playernow;
		if (playernow == 2)
			playernow = 1;
		else
			playernow = 2;
		switch (type) {
		case 0:
			if (horizontal[a][b] == 0) {
				horizontal[a][b] = 2;
				if (a == 0) {
					if (horizontal[a + 1][b] != 0 && vertical[a][b] != 0
							&& vertical[a][b + 1] != 0) {
						occupied[a][b] = player;
						playernow = player;
					}
				} else if (a == 5) {
					if (horizontal[a - 1][b] != 0 && vertical[a - 1][b] != 0
							&& vertical[a - 1][b + 1] != 0) {
						occupied[a - 1][b] = player;
						playernow = player;
					}
				} else {
					if (horizontal[a + 1][b] != 0 && vertical[a][b] != 0
							&& vertical[a][b + 1] != 0) {
						occupied[a][b] = player;
						playernow = player;
					}
					if (horizontal[a - 1][b] != 0 && vertical[a - 1][b] != 0
							&& vertical[a - 1][b + 1] != 0) {
						occupied[a - 1][b] = player;
						playernow = player;
					}
				}
			} else {
				playernow = player;
			}
			break;
		case 1:
			if (vertical[a][b] == 0) {
				vertical[a][b] = 2;
				if (b == 0) {
					if (vertical[a][b + 1] != 0 && horizontal[a][b] != 0
							&& horizontal[a + 1][b] != 0) {
						occupied[a][b] = player;
						playernow = player;
					}
				} else if (b == 5) {
					if (vertical[a][b - 1] != 0 && horizontal[a][b - 1] != 0
							&& horizontal[a + 1][b - 1] != 0) {
						occupied[a][b - 1] = player;
						playernow = player;
					}
				} else {
					if (vertical[a][b + 1] != 0 && horizontal[a][b] != 0
							&& horizontal[a + 1][b] != 0) {
						occupied[a][b] = player;
						playernow = player;
					}
					if (vertical[a][b - 1] != 0 && horizontal[a][b - 1] != 0
							&& horizontal[a + 1][b - 1] != 0) {
						occupied[a][b - 1] = player;
						playernow = player;
					}
				}
			} else {
				playernow = player;
			}
			break;

		}
		activity.updateState();
		invalidate();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(0x00FFFFFF);
		width = this.getWidth();
		hight = this.getHeight();
		int min = Math.min(width, hight);
		float radius = Board.radius * min;
		float start = Board.start * min;
		float stop = Board.stop * min;
		float add1 = Board.add1 * min;
		float add2 = Board.add2 * min;
		float add4 = Board.add4 * min;
		float add5 = Board.add5 * min;
		float add6 = Board.add6 * min;

		if (playernow == 1)
			paint.setColor(0xFF6C69FF);
		else if (playernow == 2)
			paint.setColor(0x88E5004F);
		else
			paint.setColor(0xFF000000);
		float temp=add2/2;
		for(int i=1;i<6;i++){
		canvas.drawLine(temp*i, temp*i, min-temp*(i-1), temp*i, paint);
		canvas.drawLine(temp*i, temp*i, temp*i,min-temp*(i-1), paint);
		canvas.drawLine(min-temp*(i-1), temp*i, min-temp*(i-1), min-temp*(i-1), paint);
		canvas.drawLine(temp*i, min-temp*(i-1), min-temp*(i-1), min-temp*(i-1), paint);
		}

		paint.setColor(0xFF777777);
		for (int i = 0; i < 6; i++) {
			canvas.drawLine(start + add5 * i, start, start + add5 * i, stop,
					paint);
			canvas.drawLine(start + add5 * i + add1, start, start + add5 * i
					+ add1, stop, paint);
			canvas.drawLine(start, start + add5 * i, stop, start + add5 * i,
					paint);
			canvas.drawLine(start, start + add5 * i + add1, stop, start + add5
					* i + add1, paint);
		}

		paint.setColor(0xFF000000);
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				switch (horizontal[i][j]) {
				case 1:
					paint.setColor(0xFF000000);
					break;
				case 2:
					paint.setColor(0xFFFF7700);
					horizontal[i][j] = 1;
					break;
				default:
					paint.setColor(0xFFFFFFFF);
				}
				canvas.drawRect(start + add5 * j + add1, start + add5 * i
						+ add2, start + add5 * (j + 1), start + add5 * i + add1
						- add2, paint);
				switch (vertical[j][i]) {
				case 1:
					paint.setColor(0xFF000000);
					break;
				case 2:
					paint.setColor(0xFFFF7700);
					vertical[j][i] = 1;
					break;
				default:
					paint.setColor(0xFFFFFFFF);
				}
				canvas.drawRect(start + add5 * i + add2, start + add5 * j
						+ add1, start + add5 * i + add1 - add2, start + add5
						* (j + 1), paint);
			}
		}

		paint.setColor(0x88E5004F);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (occupied[j][i] == 2)
					canvas.drawRect(start + add5 * i + add1 + add2, start
							+ add5 * j + add1 + add2, start + add5 * i + add1
							+ add4 - add2, start + add5 * j + add1 + add4
							- add2, paint);
			}
		}

		paint.setColor(0xFF6C69FF);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (occupied[j][i] == 1)
					canvas.drawRect(start + add5 * i + add1 + add2, start
							+ add5 * j + add1 + add2, start + add5 * i + add1
							+ add4 - add2, start + add5 * j + add1 + add4
							- add2, paint);
			}
		}

		paint.setColor(0xFF666666);
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				canvas.drawCircle(start + add6 + j * add5 + 1, start + add6 + i
						* add5 + 1, radius, paint);
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float touchX = event.getX();
		float touchY = event.getY();
		int min = Math.min(width, hight);
		float start = Board.start * min;
		float add1 = Board.add1 * min;
		float add2 = Board.add2 * min;
		float add3 = Board.add3 * min;
		float add5 = Board.add5 * min;
		int d = -1, a = -1, b = -1;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				if ((start + add5 * j + add1 - add3) <= touchX
						&& touchX <= (start + add5 * (j + 1) + add3)
						&& touchY >= start + add5 * i + add2 - add3
						&& touchY <= start + add5 * i + add1 - add2 + add3) {
					d = 0;
					a = i;
					b = j;
				}
				if (start + add5 * i + add2 - add3 <= touchX
						&& touchX <= start + add5 * i + add1 - add2 + add3
						&& touchY >= start + add5 * j + add1 - add3
						&& touchY <= start + add5 * (j + 1) + add3) {
					d = 1;
					a = j;
					b = i;
				}
			}
		}

		if (a != -1 && b != -1 && d != -1) {
			move = new Move(d, a, b);
			add(move);
			activity.touch();
			activity.checkWinner();
			

		}

		
		return super.onTouchEvent(event);
	}

	public void startGame(DoubleActivity temp) {
		playernow = DoubleActivity.mover;
		invalidate();
		postInvalidate();
		activity = temp;
		activity.updateState();

	}
	
	

}
