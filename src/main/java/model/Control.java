package model;

import javafx.scene.input.KeyCode;

public enum Control {
	ArrowKey(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT),
	ASDW(KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D);
	private final KeyCode up, down, left, right;

	Control(KeyCode up, KeyCode down, KeyCode left, KeyCode right) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
	}

	public KeyCode getUp() {
		return up;
	}

	public KeyCode getDown() {
		return down;
	}

	public KeyCode getLeft() {
		return left;
	}

	public KeyCode getRight() {
		return right;
	}


	@Override
	public String toString() {
		if (this == ArrowKey) return "Arrow Keys";
		return "A-S-D-W";
	}
}
