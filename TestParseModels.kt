package com.test.testaccompanistwebview

data class Fixture(
    val description: String,
    val p1: String,
    val p1Image: String,
    val p2: String,
    val p2Image: String,
    val time: String,
    val location: String,
    val p1ChanceToWin: String,
    val p2ChanceToWin: String
)

data class DayFixture(
    val date: String,
    val fixtures: List<Fixture>
)

data class FixtureFootball(
    val date: String,
    val p1: String,
    val p2: String,
    val score: String,
    val time: String
)