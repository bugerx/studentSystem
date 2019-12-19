package com.xtyuns.studentSystem;

public class Student {
	private long sNo;
	private String sName;
	private int mathScore;
	private int cNScore;
	private int csScore;
	private int totalScore;

	public Student(long sNo, String sName, int mathScore, int cNScore, int csScore) {
		super();
		this.sNo = sNo;
		this.sName = sName;
		this.mathScore = mathScore;
		this.cNScore = cNScore;
		this.csScore = csScore;
		this.totalScore = mathScore + cNScore + csScore;
	}

	@Override
	public String toString() {
		return sNo + "\t" + sName + "\t" + mathScore + "\t" + cNScore
				+ "\t" + csScore + "\t" + totalScore;
	}

	public long getsNo() {
		return sNo;
	}

	public void setsNo(long sNo) {
		this.sNo = sNo;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public int getMathScore() {
		return mathScore;
	}

	public void setMathScore(int mathScore) {
		this.mathScore = mathScore;
	}

	public int getcNScore() {
		return cNScore;
	}

	public void setcNScore(int cNScore) {
		this.cNScore = cNScore;
	}

	public int getCsScore() {
		return csScore;
	}

	public void setCsScore(int csScore) {
		this.csScore = csScore;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

}
