package com.napier.coursework;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerActivity extends AppCompatActivity {
    private ImageView close;
    private ArrayList<Location> locations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        close = (ImageView) findViewById(R.id.ic_close);

        RecyclerView attractionsListView = (RecyclerView) findViewById(R.id.attractions_list);

        attractionsListView.setHasFixedSize(true);

        loadLocations();

        LocationsAdapter adapter = new LocationsAdapter(this, R.layout.location_entry, locations);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        attractionsListView.setLayoutManager(layoutManager);

        attractionsListView.setAdapter(adapter);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
    }

    private void closeActivity(){
        super.onBackPressed();
    }


    private void loadLocations() {
        locations = new ArrayList<Location>();

        Location edinburgh_castle = new Location(
                "Edinburgh Castle",
                R.drawable.edinburgh_castle,
                "Castlehill, Edinburgh EH1 2NG",
                "Open 9:00am to 5:30pm, every day of the week.",
                "Edinburgh Castle is a historic fortress which dominates the skyline of Edinburgh, the capital city of Scotland, from its position on the Castle Rock." +
                        " Archaeologists have established human occupation of the rock since at least the Iron Age (2nd century AD), although the nature of the early settlement is unclear." +
                        " There has been a royal castle on the rock since at least the reign of David I in the 12th century, and the site continued at times to be a royal residence until 1633. " +
                        "From the 15th century, the castle's residential role declined, and by the 17th century it was principally used as military barracks with a large garrison. " +
                        "Its importance as a part of Scotland's national heritage was recognised increasingly from the early 19th century onwards, and various restoration programmes have been carried out over the past century and a half. " +
                        "As one of the most important strongholds in the Kingdom of Scotland, Edinburgh Castle was involved in many historical conflicts from the Wars of Scottish Independence in the 14th century to the Jacobite rising of 1745." +
                        " Research undertaken in 2014 identified 26 sieges in its 1100-year-old history, giving it a claim to having been \"the most besieged Location in Great Britain and one of the most attacked in the world\".");

        Location scott_monument = new Location(
                "Scott Monument",
                R.drawable.scott_monument,
                "Princes St Gardens, Edinburgh EH2 2EJ",
                "N/A",
                "The Scott Monument is a Victorian Gothic monument to Scottish author Sir Walter Scott." +
                        " It is the second largest monument to a writer in the world after the José Martí monument in Havana. " +
                        "The tower is 200 feet 6 inches (61.11 m) high and has viewing platforms reached by a series of spiral staircases giving panoramic views of central Edinburgh and its surroundings." +
                        " The highest platform is reached by a total of 288 steps.");

        Location stirling_castle = new Location(
                "Stirling Castle",
                R.drawable.stirling_castle,
                "Castle Esplanade, Stirling FK8 1EJ",
                "N/A",
                "Stirling Castle, located in Stirling, is one of the largest and most important castles in Scotland, both historically and architecturally. The castle sits atop Castle Hill, an intrusive crag, which forms part of the Stirling Sill geological formation. It is surrounded on three sides by steep cliffs, giving it a strong defensive position. Its strategic location, guarding what was, until the 1890s, the farthest downstream crossing of the River Forth, has made it an important fortification in the region from the earliest times.\n" +
                        "\n" +
                        "Most of the principal buildings of the castle date from the fifteenth and sixteenth centuries. A few structures of the fourteenth century remain, while the outer defences fronting the town date from the early eighteenth century.\n" +
                        "\n" +
                        "Before the union with England, Stirling Castle was also one of the most used of the many Scottish royal residences, very much a palace as well as a fortress. Several Scottish Kings and Queens have been crowned at Stirling, including Mary, Queen of Scots, in 1542, and others were born or died there.\n" +
                        "\n" +
                        "There have been at least eight sieges of Stirling Castle, including several during the Wars of Scottish Independence, with the last being in 1746, when Bonnie Prince Charlie unsuccessfully tried to take the castle. Stirling Castle is a Scheduled Ancient Monument, and is now a tourist attraction managed by Historic Environment Scotland.");

        Location loch_lomond = new Location(
                "Loch Lomomd",
                R.drawable.loch_lomond,
                "West Dunbartonshire/Argyll and Bute/Stirling, Scotland",
                "N/A",
                "Loch Lomond is a freshwater Scottish loch which crosses the Highland Boundary Fault, often considered the boundary between the lowlands of Central Scotland and the Highlands. Traditionally forming part of the boundary between the counties of Stirlingshire and Dunbartonshire, Loch Lomond is split between the council areas of Stirling, Argyll and Bute and West Dunbartonshire. Its southern shores are about 23 kilometres (14 mi) northwest of the centre of Glasgow, Scotland's largest city. The Loch forms part of the Loch Lomond and The Trossachs National Park which was established in 2002.\n" +
                        "\n" +
                        "Loch Lomond is 36.4 kilometres (22.6 mi) long and between 1 and 8 kilometres (0.62–4.97 mi) wide, with a surface area of 71 km2 (27.5 sq mi). It is the largest lake in Great Britain by surface area; in the United Kingdom, it is surpassed only by Lough Neagh and Lough Erne in Northern Ireland. In the British Isles as a whole there are several larger loughs in the Republic of Ireland. The loch has a maximum depth of about 153 metres (502 ft) in the deeper northern portion, although the southern part of the loch rarely exceeds 30 metres (98 ft) in depth. The total volume of Loch Lomond is 2.6 km3 (0.62 cu mi), making it the second largest lake in Great Britain, after Loch Ness, by water volume."
        );

        Location ben_nevis = new Location(
                "Ben Nevis",
                R.drawable.ben_nevis,
                "Lochaber, Highland, Scotland",
                "N/A",
                "Ben Nevis is the highest mountain in the British Isles, the summit rises to 1,345 metres (4,413 ft) above sea level. Ben Nevis stands at the western end of the Grampian Mountains in the Lochaber area of the Scottish Highlands, close to the town of Fort William. The summit is the highest land in any direction for 459 miles (739 km).\n" +
                        "\n" +
                        "The mountain is a popular destination, attracting an estimated 100,000 ascents a year, around three-quarters of which use the Pony Track from Glen Nevis. The 700-metre (2,300 ft) cliffs of the north face are among the highest in Scotland, providing classic scrambles and rock climbs of all difficulties for climbers and mountaineers. They are also the principal locations in Scotland for ice climbing."
        );
        Location loch_ness = new Location(
                "Loch Ness",
                R.drawable.loch_ness,
                "Scottish Highlands",
                "N/A",
                "Loch Ness is a large, deep, freshwater loch in the Scottish Highlands extending for approximately 37 kilometres (23 miles) southwest of Inverness. Its surface is 16 metres (52 feet) above sea level. Loch Ness is best known for alleged sightings of the cryptozoological Loch Ness Monster, also known affectionately as \"Nessie\". It is connected at the southern end by the River Oich and a section of the Caledonian Canal to Loch Oich. At the northern end there is the Bona Narrows which opens out into Loch Dochfour, which feeds the River Ness and a further section of canal to Inverness, ultimately leading to the North Sea via the Moray Firth. It is one of a series of interconnected, murky bodies of water in Scotland; its water visibility is exceptionally low due to a high peat content in the surrounding soil."
        );
        Location edinburgh_oldtown = new Location(
                "Edinburgh OldTown",
                R.drawable.edinburgh_oldtown,
                "Edinburgh",
                "N/A",
                "The Old Town is the name popularly given to the oldest part of Scotland's capital city of Edinburgh. The area has preserved much of its medieval street plan and many Reformation-era buildings. Together with the 18th/19th-century New Town, it forms part of a protected UNESCO World Heritage Site."
        );
        Location palace_holyroodhouse = new Location(
                "Palace of Holyroodhouse",
                R.drawable.palace_holyroodhouse,
                "Canongate, Edinburgh EH8 8DX",
                "Open 9:30am to 4:30pm, every day of the week.",
                "The Palace of Holyroodhouse, commonly referred to as Holyrood Palace, is the official residence of the British monarch in Scotland, Queen Elizabeth II. Located at the bottom of the Royal Mile in Edinburgh, at the opposite end to Edinburgh Castle, Holyrood Palace has served as the principal residence of the Kings and Queens of Scots since the 16th century, and is a setting for state occasions and official entertaining.\n" +
                        "\n" +
                        "Queen Elizabeth spends one week in residence at Holyrood Palace at the beginning of each summer, where she carries out a range of official engagements and ceremonies. The 16th-century Historic Apartments of Mary, Queen of Scots and the State Apartments, used for official and state entertaining, are open to the public throughout the year, except when members of the Royal Family are in residence."
        );


        locations.add(edinburgh_castle);
        locations.add(scott_monument);
        locations.add(loch_lomond);
        locations.add(stirling_castle);
        locations.add(ben_nevis);
        locations.add(loch_ness);
        locations.add(edinburgh_oldtown);
        locations.add(palace_holyroodhouse);

    }
}

