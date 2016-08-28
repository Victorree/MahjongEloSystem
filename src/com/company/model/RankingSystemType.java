package com.company.model;

public enum RankingSystemType {
	JOSH_SYSTEM(0),
	NEW_SYSTEM(1),
	BLOCK_SYSTEM(2);

	private final int rankingSystemType;

	RankingSystemType(int rankingSystemType) {
		this.rankingSystemType = rankingSystemType;
	}

	public int getRankingSystemType() {
		return rankingSystemType;
	}
}
