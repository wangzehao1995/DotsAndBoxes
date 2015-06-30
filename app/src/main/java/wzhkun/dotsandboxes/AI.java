package wzhkun.dotsandboxes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class AI {
	int difficuty;
	private boolean[][] horizontal;
	private boolean[][] vertical;
	private Box[][] box;
	private Vector<Move> safeMove;
	private Vector<Move> goodMove;
	private Vector<Move> badMove;
	private HashMap<Move, Integer> goodMoveValue;
	private HashMap<Move, Integer> badMoveValue;
	private HashMap<Move, Integer> goodMoveType;

	class Box {
		boolean left;
		boolean top;
		boolean right;
		boolean bottom;
		boolean ocpd;

		Box(boolean l, boolean t, boolean r, boolean b) {
			this.left = l;
			this.top = t;
			this.right = r;
			this.bottom = b;
			if (l && t && r && b)
				this.ocpd = true;
			else
				this.ocpd = false;
		}

		int contain() {
			int counter = 0;
			if (this.left)
				counter++;
			if (this.right)
				counter++;
			if (this.top)
				counter++;
			if (this.bottom)
				counter++;
			return counter;
		}

	}

	class VBoard {
		boolean[][] horizontal;
		boolean[][] vertical;
		boolean[][] occupied;
		private Box[][] box;

		boolean keepgoing;

		VBoard(boolean[][] h, boolean[][] v) {
			horizontal = new boolean[6][5];
			vertical = new boolean[5][6];
			occupied = new boolean[5][5];
			box = new Box[5][5];
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 5; j++) {
					horizontal[i][j] = h[i][j];
					vertical[j][i] = v[j][i];
				}
			}

		}

		void ini() {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					box[i][j] = new Box(vertical[i][j], horizontal[i][j],
							vertical[i][j + 1], horizontal[i + 1][j]);
					if (box[i][j].ocpd)
						occupied[i][j] = true;
				}
			}
		}

		void add(Move move) {
			int type = move.direction;
			int a = move.a;
			int b = move.b;
			keepgoing = false;

			switch (type) {
			case 0:
				if (!horizontal[a][b]) {
					horizontal[a][b] = true;
					if (a == 0) {
						if (horizontal[a + 1][b] && vertical[a][b]
								&& vertical[a][b + 1]) {
							occupied[a][b] = true;
							keepgoing = true;
						}
					} else if (a == 5) {
						if (horizontal[a - 1][b] && vertical[a - 1][b]
								&& vertical[a - 1][b + 1]) {
							occupied[a - 1][b] = true;
							keepgoing = true;
						}
					} else {
						if (horizontal[a + 1][b] && vertical[a][b]
								&& vertical[a][b + 1]) {
							occupied[a][b] = true;
							keepgoing = true;
						}
						if (horizontal[a - 1][b] && vertical[a - 1][b]
								&& vertical[a - 1][b + 1]) {
							occupied[a - 1][b] = true;
							keepgoing = true;
						}
					}
				} else {
					keepgoing = true;
				}
				break;
			case 1:
				if (!vertical[a][b]) {
					vertical[a][b] = true;
					if (b == 0) {
						if (vertical[a][b + 1] && horizontal[a][b]
								&& horizontal[a + 1][b]) {
							occupied[a][b] = true;
							keepgoing = true;
						}
					} else if (b == 5) {
						if (vertical[a][b - 1] && horizontal[a][b - 1]
								&& horizontal[a + 1][b - 1]) {
							occupied[a][b - 1] = true;
							keepgoing = true;
						}
					} else {
						if (vertical[a][b + 1] && horizontal[a][b]
								&& horizontal[a + 1][b]) {
							occupied[a][b] = true;
							keepgoing = true;
						}
						if (vertical[a][b - 1] && horizontal[a][b - 1]
								&& horizontal[a + 1][b - 1]) {
							occupied[a][b - 1] = true;
							keepgoing = true;
						}
					}
				} else {
					keepgoing = true;
				}
				break;
			}
		}

		int getOccupiedNumber() {
			int counter = 0;
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (occupied[i][j])
						counter++;
				}
			}
			return counter;
		}

		int getBadMoveValue(Move move) {
			int start, end;
			if (move.direction == 0)
				this.horizontal[move.a][move.b] = true;
			else
				this.vertical[move.a][move.b] = true;
			AI ai = new AI(0);
			ini();
			start = this.getOccupiedNumber();
			keepgoing = true;
			while (keepgoing) {
				add(ai.move(this));
				if (this.getOccupiedNumber() == 25)
					break;
			}
			end = this.getOccupiedNumber();
			return (end - start);
		}

		int getGoodMoveValue(Move move) {
			int start, end;
			if (move.direction == 0)
				this.horizontal[move.a][move.b] = true;
			else
				this.vertical[move.a][move.b] = true;
			AI ai = new AI(0);
			ini();
			start = this.getOccupiedNumber();
			keepgoing = true;
			while (keepgoing) {
				add(ai.move(this));
				if (this.getOccupiedNumber() == 25)
					break;
			}
			end = this.getOccupiedNumber() + 1;
			return (end - start);
		}

		int getCanWinValue(Move move) {
			ini();
			int ocpdAlready = getOccupiedNumber();
			if (move.direction == 0)
				this.horizontal[move.a][move.b] = true;
			else
				this.vertical[move.a][move.b] = true;
			int He = 0;
			int Me = 0;	
			ini();
			Me = getOccupiedNumber() - ocpdAlready;
			AI ai = new AI(2);
			int playernow;
			if (Me == 1)
				playernow = 0;
			else
				playernow = 1;
			
			while (this.getOccupiedNumber() != 25) {
				if (playernow == 0) {
					int temp = getOccupiedNumber();
					keepgoing = true;
					while (keepgoing) {
						add(ai.move(this));
						if (this.getOccupiedNumber() == 25)
							break;
					}
					temp = getOccupiedNumber() - temp;
					Me = Me + temp;
					playernow=1;
				} else {
					int temp = getOccupiedNumber();
					keepgoing = true;
					while (keepgoing) {
						add(ai.move(this));
						if (this.getOccupiedNumber() == 25)
							break;
					}
					temp = getOccupiedNumber() - temp;
					He = He + temp;
					playernow=0;
				}
			}
			return Me-He;
		}
	}

	AI(int difficuty) {
		this.difficuty = difficuty;
		horizontal = new boolean[6][5];
		vertical = new boolean[5][6];
		box = new Box[5][5];
		safeMove = new Vector<Move>();
		goodMove = new Vector<Move>();
		badMove = new Vector<Move>();
		badMoveValue = new HashMap<Move, Integer>();
		goodMoveValue = new HashMap<Move, Integer>();
		goodMoveType = new HashMap<Move, Integer>();

	}

	public Move move() {
		initialiseBoard();
		initialiseSafeMove();
		initialiseGoodMove();
		initialiseBadMove();

		if (difficuty == 1) {
			return normal();
		} else if (difficuty == 2) {
			return hard();
		} else if (difficuty == 3) {
			return ultra();
		} else
			return random();
	}

	private Move move(VBoard vboard) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				horizontal[i][j] = vboard.horizontal[i][j];
				vertical[j][i] = vboard.vertical[j][i];
			}
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				box[i][j] = new Box(vertical[i][j], horizontal[i][j],
						vertical[i][j + 1], horizontal[i + 1][j]);
			}
		}
		initialiseSafeMove();
		initialiseGoodMove();
		initialiseBadMove();

		if (difficuty == 1) {
			return normal();
		} else if (difficuty == 2) {
			return hard();
		} else if (difficuty == 3) {
			return ultra();
		} else
			return random();
	}

	private Move normal() {
		if (goodMove.size() != 0)
			return goodMove.get((int) ((goodMove.size()) * Math.random()));
		if (safeMove.size() != 0)
			return safeMove.get((int) ((safeMove.size()) * Math.random()));
		else {
			Move min = null;
			int minValue = 26;
			initialiseBadMoveValue();
			for (Move move : badMove) {
				if (badMoveValue.get(move) < minValue) {
					min = move;
					minValue = badMoveValue.get(move);
				}
			}
			return min;
		}
	}

	private Move hard() {
		if (safeMove.size() != 0) {
			if (goodMove.size() != 0)
				return goodMove.get((int) ((goodMove.size()) * Math.random()));
			else
				return safeMove.get((int) ((safeMove.size()) * Math.random()));
		} else if (goodMove.size() != 0) {
			if (badMove.size() == 0)
				return goodMove.get((int) ((goodMove.size()) * Math.random()));
			initialiseGoodMoveValue();
			initialiseBadMoveValue();

			ArrayList<Move> bad = new ArrayList<Move>();
			ArrayList<Move> bad2 = new ArrayList<Move>();
			ArrayList<Move> good = new ArrayList<Move>();
			ArrayList<Move> good2 = new ArrayList<Move>();
			int badValue = 26;
			int bad2Value = 26;
			int goodValue = 0;
			int goodValue2 = 0;
			for (Move move : badMove) {
				if (badMoveValue.get(move) < badValue) {
					bad2.clear();
					for (Move m : bad) {
						bad2.add(m);
					}
					bad2Value = badValue;
					bad.clear();
					bad.add(move);
					badValue = badMoveValue.get(move);
				} else if (badMoveValue.get(move) == badValue) {
					bad.add(move);
				}
			}
			for (Move move : goodMove) {
				if (goodMoveValue.get(move) > goodValue) {
					good2.clear();
					for (Move m : good) {
						good2.add(m);
					}
					goodValue2 = goodValue;
					good.clear();
					good.add(move);
					goodValue = goodMoveValue.get(move);
				} else if (goodMoveValue.get(move) == goodValue) {
					good.add(move);
				}
			}

			if (goodValue == 2 && badValue == 2 && bad.size() == 1
					&& bad2.size() != 0 && bad2Value > 6) {
				if (goodValue2 > 2) {
					return good2.get((int) ((good2.size()) * Math.random()));
				}
				initialiseGoodMoveType();
				for (Move move : good) {
					if (goodMoveType.get(move) == 2) {
						return move;
					}
				}
				return bad.get(0);
			} else
				return good.get((int) ((good.size()) * Math.random()));

		} else {
			Move min = null;
			int minValue = 26;
			initialiseBadMoveValue();
			for (Move move : badMove) {
				if (badMoveValue.get(move) < minValue) {
					min = move;
					minValue = badMoveValue.get(move);
				}
			}
			return min;
		}

	}

	private Move ultra() {if (safeMove.size() != 0) {
		if (goodMove.size() != 0)
			return goodMove.get((int) ((goodMove.size()) * Math.random()));
		else
			return safeMove.get((int) ((safeMove.size()) * Math.random()));
	} else if (goodMove.size() != 0) {
		if (badMove.size() == 0)
			return goodMove.get((int) ((goodMove.size()) * Math.random()));
		initialiseGoodMoveValue();
		initialiseBadMoveValue();

		ArrayList<Move> bad = new ArrayList<Move>();
		ArrayList<Move> bad2 = new ArrayList<Move>();
		ArrayList<Move> good = new ArrayList<Move>();
		ArrayList<Move> good2 = new ArrayList<Move>();
		int badValue = 26;
		int bad2Value = 26;
		int goodValue = 0;
		int goodValue2 = 0;
		for (Move move : badMove) {
			if (badMoveValue.get(move) < badValue) {
				bad2.clear();
				for (Move m : bad) {
					bad2.add(m);
				}
				bad2Value = badValue;
				bad.clear();
				bad.add(move);
				badValue = badMoveValue.get(move);
			} else if (badMoveValue.get(move) == badValue) {
				bad.add(move);
			}
		}
		for (Move move : goodMove) {
			if (goodMoveValue.get(move) > goodValue) {
				good2.clear();
				for (Move m : good) {
					good2.add(m);
				}
				goodValue2 = goodValue;
				good.clear();
				good.add(move);
				goodValue = goodMoveValue.get(move);
			} else if (goodMoveValue.get(move) == goodValue) {
				good.add(move);
			}
		}

		if (goodValue == 2 && badValue == 2 && bad.size() == 1
				&& bad2.size() != 0 && bad2Value > 6) {
			if (goodValue2 > 2) {
				return good2.get((int) ((good2.size()) * Math.random()));
			}
			initialiseGoodMoveType();
			for (Move move : good) {
				if (goodMoveType.get(move) == 2) {
					return move;
				}
			}
			return bad.get(0);
		} else
			return good.get((int) ((good.size()) * Math.random()));

	} else {
		Move min = null;
		int minValue = 26;
		initialiseBadMoveValue();
		for (Move move : badMove) {
			if (badMoveValue.get(move) < minValue) {
				min = move;
				minValue = badMoveValue.get(move);
			}
		}
		return min;
	}

}

	private Move random() {
		if (goodMove.size() != 0)
			return goodMove.get((int) ((goodMove.size()) * Math.random()));
		if (safeMove.size() != 0)
			return safeMove.get((int) ((safeMove.size()) * Math.random()));
		Vector<Move> temp = new Vector<Move>();
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				if (!horizontal[i][j])
					temp.add(new Move(0, i, j));
				if (!vertical[j][i])
					temp.add(new Move(1, j, i));
			}
		}
		return temp.get((int) ((temp.size()) * Math.random()));
	}

	private void initialiseGoodMove() {
		int counter = 0;
		boolean t1 = false;
		boolean t2 = false;
		goodMove.clear();
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				if (!horizontal[i][j]) {
					if (i == 0) {
						counter = 0;
						if (box[i][j].left)
							counter++;
						if (box[i][j].bottom)
							counter++;
						if (box[i][j].right)
							counter++;
						if (box[i][j].contain() == 3)
							goodMove.add(new Move(0, i, j));
					} else if (i == 5) {

						counter = 0;
						if (box[i - 1][j].left)
							counter++;
						if (box[i - 1][j].top)
							counter++;
						if (box[i - 1][j].right)
							counter++;
						if (counter == 3)
							goodMove.add(new Move(0, i, j));
					} else {
						counter = 0;
						if (box[i][j].left)
							counter++;
						if (box[i][j].bottom)
							counter++;
						if (box[i][j].right)
							counter++;
						if (counter == 3)
							t1 = true;
						counter = 0;
						if (box[i - 1][j].left)
							counter++;
						if (box[i - 1][j].top)
							counter++;
						if (box[i - 1][j].right)
							counter++;
						if (counter == 3)
							t2 = true;
						if (t1 || t2)
							goodMove.add(new Move(0, i, j));
						t1 = false;
						t2 = false;
					}
				}
				if (!vertical[j][i]) {
					if (i == 0) {
						counter = 0;
						if (box[j][i].right)
							counter++;
						if (box[j][i].bottom)
							counter++;
						if (box[j][i].top)
							counter++;
						if (counter == 3)
							goodMove.add(new Move(1, j, i));
					} else if (i == 5) {
						counter = 0;
						if (box[j][i - 1].left)
							counter++;
						if (box[j][i - 1].top)
							counter++;
						if (box[j][i - 1].bottom)
							counter++;
						if (counter == 3)
							goodMove.add(new Move(1, j, i));
					} else {
						counter = 0;
						if (box[j][i].right)
							counter++;
						if (box[j][i].bottom)
							counter++;
						if (box[j][i].top)
							counter++;
						if (counter == 3)
							t1 = true;
						counter = 0;
						if (box[j][i - 1].left)
							counter++;
						if (box[j][i - 1].top)
							counter++;
						if (box[j][i - 1].bottom)
							counter++;
						if (counter == 3)
							t2 = true;
						if (t1 || t2)
							goodMove.add(new Move(1, j, i));
						t1 = false;
						t2 = false;
					}
				}
			}
		}

	}

	private void initialiseBadMove() {
		int counter = 0;
		boolean t1 = false;
		boolean t2 = false;
		badMove.clear();
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				if (!horizontal[i][j]) {
					if (i == 0) {
						counter = 0;
						if (box[i][j].left)
							counter++;
						if (box[i][j].bottom)
							counter++;
						if (box[i][j].right)
							counter++;
						if (counter == 2)
							badMove.add(new Move(0, i, j));
					} else if (i == 5) {

						counter = 0;
						if (box[i - 1][j].left)
							counter++;
						if (box[i - 1][j].top)
							counter++;
						if (box[i - 1][j].right)
							counter++;
						if (counter == 2)
							badMove.add(new Move(0, i, j));
					} else {
						counter = 0;
						if (box[i][j].left)
							counter++;
						if (box[i][j].bottom)
							counter++;
						if (box[i][j].right)
							counter++;
						if (counter == 2)
							t1 = true;
						counter = 0;
						if (box[i - 1][j].left)
							counter++;
						if (box[i - 1][j].top)
							counter++;
						if (box[i - 1][j].right)
							counter++;
						if (counter == 2)
							t2 = true;
						if (t1 || t2)
							badMove.add(new Move(0, i, j));
						t1 = false;
						t2 = false;
					}
				}
				if (!vertical[j][i]) {
					if (i == 0) {
						counter = 0;
						if (box[j][i].right)
							counter++;
						if (box[j][i].bottom)
							counter++;
						if (box[j][i].top)
							counter++;
						if (counter == 2)
							badMove.add(new Move(1, j, i));
					} else if (i == 5) {
						counter = 0;
						if (box[j][i - 1].left)
							counter++;
						if (box[j][i - 1].top)
							counter++;
						if (box[j][i - 1].bottom)
							counter++;
						if (counter == 2)
							badMove.add(new Move(1, j, i));
					} else {
						counter = 0;
						if (box[j][i].right)
							counter++;
						if (box[j][i].bottom)
							counter++;
						if (box[j][i].top)
							counter++;
						if (counter == 2)
							t1 = true;
						counter = 0;
						if (box[j][i - 1].left)
							counter++;
						if (box[j][i - 1].top)
							counter++;
						if (box[j][i - 1].bottom)
							counter++;
						if (counter == 2)
							t2 = true;
						if (t1 || t2)
							badMove.add(new Move(1, j, i));
						t1 = false;
						t2 = false;
					}
				}
			}
		}
		for (Move a : goodMove) {
			try {
				for (Move b : badMove) {
					if (a.direction == b.direction && a.a == b.a && a.b == b.b) {
						badMove.remove(b);
					}
				}
			} catch (Exception e) {
			}
		}
	}

	private void initialiseSafeMove() {
		int counter = 0;
		boolean t1 = false;
		boolean t2 = false;
		safeMove.clear();

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				if (!horizontal[i][j]) {
					if (i == 0) {
						counter = 0;
						if (box[i][j].left)
							counter++;
						if (box[i][j].bottom)
							counter++;
						if (box[i][j].right)
							counter++;
						if (counter < 2)
							safeMove.add(new Move(0, i, j));
					} else if (i == 5) {

						counter = 0;
						if (box[i - 1][j].left)
							counter++;
						if (box[i - 1][j].top)
							counter++;
						if (box[i - 1][j].right)
							counter++;
						if (counter < 2)
							safeMove.add(new Move(0, i, j));
					} else {
						counter = 0;
						if (box[i][j].left)
							counter++;
						if (box[i][j].bottom)
							counter++;
						if (box[i][j].right)
							counter++;
						if (counter < 2)
							t1 = true;
						counter = 0;
						if (box[i - 1][j].left)
							counter++;
						if (box[i - 1][j].top)
							counter++;
						if (box[i - 1][j].right)
							counter++;
						if (counter < 2)
							t2 = true;
						if (t1 && t2)
							safeMove.add(new Move(0, i, j));
						t1 = false;
						t2 = false;
					}
				}
				if (!vertical[j][i]) {
					if (i == 0) {
						counter = 0;
						if (box[j][i].right)
							counter++;
						if (box[j][i].bottom)
							counter++;
						if (box[j][i].top)
							counter++;
						if (counter < 2)
							safeMove.add(new Move(1, j, i));
					} else if (i == 5) {
						counter = 0;
						if (box[j][i - 1].left)
							counter++;
						if (box[j][i - 1].top)
							counter++;
						if (box[j][i - 1].bottom)
							counter++;
						if (counter < 2)
							safeMove.add(new Move(1, j, i));
					} else {
						counter = 0;
						if (box[j][i].right)
							counter++;
						if (box[j][i].bottom)
							counter++;
						if (box[j][i].top)
							counter++;
						if (counter < 2)
							t1 = true;
						counter = 0;
						if (box[j][i - 1].left)
							counter++;
						if (box[j][i - 1].top)
							counter++;
						if (box[j][i - 1].bottom)
							counter++;
						if (counter < 2)
							t2 = true;
						if (t1 && t2)
							safeMove.add(new Move(1, j, i));
						t1 = false;
						t2 = false;
					}
				}
			}
		}
	}

	private void initialiseBoard() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				if (SBoard.horizontal[i][j] == 0)
					horizontal[i][j] = false;
				else
					horizontal[i][j] = true;
				if (SBoard.vertical[j][i] == 0)
					vertical[j][i] = false;
				else
					vertical[j][i] = true;
			}
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				box[i][j] = new Box(vertical[i][j], horizontal[i][j],
						vertical[i][j + 1], horizontal[i + 1][j]);
			}
		}
	}

	private void initialiseGoodMoveValue() {
		if (goodMove.isEmpty())
			return;
		goodMoveValue.clear();
		for (Move move : goodMove) {
			VBoard vboard = new VBoard(horizontal, vertical);
			goodMoveValue.put(move, vboard.getGoodMoveValue(move));
		}
	}

	private void initialiseBadMoveValue() {
		if (badMove.isEmpty())
			return;
		badMoveValue.clear();
		for (Move move : badMove) {
			VBoard vboard = new VBoard(horizontal, vertical);
			badMoveValue.put(move, vboard.getBadMoveValue(move));
		}
	}

	private void initialiseGoodMoveType() {
		if (goodMove.isEmpty())
			return;
		goodMoveType.clear();
		for (Move move : goodMove) {
			if (move.direction == 0) {
				if (move.a == 0)
					goodMoveType.put(move, 1);
				else if (move.a == 5)
					goodMoveType.put(move, 1);
				else {
					int counter = 0;
					if (box[move.a][move.b].contain() == 3)
						counter++;
					if (box[move.a - 1][move.b].contain() == 3)
						counter++;
					goodMoveType.put(move, counter);
				}
			} else {
				if (move.b == 0)
					goodMoveType.put(move, 1);
				else if (move.b == 5)
					goodMoveType.put(move, 1);
				else {
					int counter = 0;
					if (box[move.a][move.b].contain() == 3)
						counter++;
					if (box[move.a][move.b - 1].contain() == 3)
						counter++;
					goodMoveType.put(move, counter);
				}

			}

		}
	}

}
