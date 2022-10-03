package com.test.testaccompanistwebview

import org.jsoup.Jsoup

fun main() {

    /*
    * build.gradle(:app):
    *
    *   dependencies {
    *       implementation("org.jsoup:jsoup:1.15.2")
    *   }
    *
    *  AndroidManifest:
    *       <uses-permission android:name="android.permission.INTERNET"/>
    * */


   /*
   * good web pages
   *    https://www.worldfootball.net/schedule/bundesliga-2022-2023-spieltag/1/
   *    https://sports.ndtv.com/cricket/schedules-fixtures
   *
   * bad web pages (bad for jsoup)
   *    https://shop.silpo.ua/category/alkogol-22
   *    https://www.flashscore.ua/soccer/germany/bundesliga/
   * */

    /*val baseUrl = "https://shop.silpo.ua/category/alkogol-22"
    val doc = Jsoup.connect(baseUrl)
        .userAgent("Mozilla/5.0 (Macintosh; anycodings_android-studio Intel Mac OS X 10_9_2) anycodings_android-studio AppleWebKit/537.36 (KHTML, anycodings_android-studio like Gecko) anycodings_android-studio Chrome/33.0.1750.152 Safari/537.36")
        .referrer("http://www.google.com")
        .ignoreContentType(true)
        .timeout(10000)
        .get()
    println(doc)*/



    //getElementsBy
    /*val gameWeek = 7
    val baseUrl =
        "https://www.worldfootball.net/schedule/bundesliga-2022-2023-spieltag/$gameWeek/"
    val doc = Jsoup.connect(baseUrl)
        .userAgent("Mozilla/5.0 (Macintosh; anycodings_android-studio Intel Mac OS X 10_9_2) anycodings_android-studio AppleWebKit/537.36 (KHTML, anycodings_android-studio like Gecko) anycodings_android-studio Chrome/33.0.1750.152 Safari/537.36")
        .referrer("http://www.google.com")
        .ignoreContentType(true)
        .timeout(10000)
        .get()
    val gameWeekFixture = doc.getElementsByClass("standard_tabelle").first()
        ?.getElementsByTag("tr")
        ?.map { it.getElementsByTag("td").map { cell -> cell.text() } }
    val fixturesList = gameWeekFixture?.map {
        FixtureFootball(
            date = it[0],
            p1 = it[2],
            p2 = it[4],
            score = it[5],
            time = it[1]
        )
    }
    fixturesList?.forEach { println(it) }*/



    //cssQuery Selector
    //  https://jsoup.org/cookbook/extracting-data/selector-syntax

    val baseUrl = "https://sports.ndtv.com/cricket/schedules-fixtures"
    val doc = Jsoup.connect(baseUrl)
        .userAgent("Mozilla/5.0 (Macintosh; anycodings_android-studio Intel Mac OS X 10_9_2) anycodings_android-studio AppleWebKit/537.36 (KHTML, anycodings_android-studio like Gecko) anycodings_android-studio Chrome/33.0.1750.152 Safari/537.36")
        .referrer("http://www.google.com")
        .ignoreContentType(true)
        .timeout(10000)
        .get()

    val scheduleDivs = doc.select("#scheulediv > div")
    val res = scheduleDivs.map { fixtureDayBlock ->
        val listOfEvents = mutableListOf<Fixture>()
        val date = fixtureDayBlock.getElementsByTag("h2").text()
        fixtureDayBlock.select("> div").map { event ->
            listOfEvents.add(
                Fixture(
                    description = event.getElementsByClass("FL-flp_lt").text(),
                    p1 = event.getElementsByClass("scr_tm-wrp")[0].text(),
                    p1Image = event.getElementsByTag("img")[0].attr("data-src"),
                    p2 = event.getElementsByClass("scr_tm-wrp")[1].text(),
                    p2Image = event.getElementsByTag("img")[1].attr("data-src"),
                    time = event.getElementsByClass("scr_dt-red").text(),
                    location = event.getElementsByClass("scr_txt-ony").eachText()[1],
                    p1ChanceToWin = event.getElementsByClass("scr_chn-lft").text(),
                    p2ChanceToWin = event.getElementsByClass("scr_chn-rgt").text()
                )
            )

        }
        DayFixture(date = date, fixtures = listOfEvents)
    }

    res.forEach {
        println(
            "DayFixture(\ndate=\"" + it.date + "\", \nfixtures= listOf("
        )
        it.fixtures.forEach { f ->
            println(
                "Fixture(" +
                        "\ndescription = \"${f.description}\"," +
                        "\np1 = \"${f.p1}\"," +
                        "\np1Image = \"${f.p1Image}\"," +
                        "\np2 = \"${f.p2}\"," +
                        "\np2Image = \"${f.p2Image}\"," +
                        "\ntime = \"${f.time}\"," +
                        "\nlocation = \"${f.location}\"," +
                        "\np1ChanceToWin = \"${f.p1ChanceToWin}\"," +
                        "\np2ChanceToWin = \"${f.p2ChanceToWin}\"" +
                        "\n),"
            )
        }
        println(")),")
    }



    /*
    * suspend fun dataLayerStorage(): List<SomeData> = withContext(Dispatchers.IO) {
    *   try {
    *       ...
    *          parsing
    *       ...
    *       return@withContext resultList.ifEmpty { throw IllegalArgumentException() }
    *   } catch (e: Exception) {
    *           Log.e("aaaa", e.message, e)
    *           listOf<SomeData>(...)
    *   }
    * }
    * */
}