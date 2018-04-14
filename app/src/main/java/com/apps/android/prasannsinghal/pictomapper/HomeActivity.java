package com.apps.android.prasannsinghal.pictomapper;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    private Button playbutton;
    private Button shopbutton;
    private Button settingsbutton;

    protected Context context = null; //getApplicationContext();


    public class GetWikiURLsAsync extends AsyncTask<Void, Void, ArrayList<MonumentModel>> {

        public String[] allMonuments = {"Great Wall of China",
                "Statue of Liberty",
                "Eiffel Tower",
                "Big Ben",
                "Sydney Opera House",
                "Hollywood Sign",
                "Colosseum",
                "White House",
                "Sagrada Familia",
                "Little Mermaid",
                "Taj Mahal",
                "Burj Al Arab Hotel",
                "The Pyramids of Giza",
                "Grand Canyon",
                "Arc de Triomphe",
                "Times Square",
                "Acropolis",
                "Tokyo Tower",
                "Mannekin-Pis",
                "Christ the Redeemer",
                "Uluru",
                "London Eye",
                "Empire State Building",
                "Machu Picchu",
                "The Forbidden City",
                "Burj Khalifa",
                "Louvre Museum",
                "Petra",
                "Saint Basil's Cathedral",
                "Prague Castle",
                "Oriental Pearl Tower",
                "Golden Gate Bridge",
                "Hagia Sophia",
                "Mount Rushmore",
                "Stonehenge",
                "Versailles",
                "Kilimanjaro",
                "Capitol Hill",
                "Berlin Wall",
                "Ancient City Walls",
                "Tower of London",
                "Moai",
                "Neuschwanstein Castle",
                "Milan Cathedral",
                "Vesuvio",
                "Rock of Gibraltar",
                "Willis Tower",
                "Hermitage Museum",
                "Tower Bridge",
                "Chichen Itza",
                "The Great Sphinx",
                "Niagara Falls",
                "Sacre-Coeur Basilica",
                "Space Needle",
                "Mont St. Michel",
                "St. Peter's Basilica",
                "Angkor Wat",
                "Brooklyn Bridge",
                "Kremlin",
                "Pantheon",
                "Trafalgar Square",
                "Pond Du Garre",
                "Alhambra",
                "Gamla Stan",
                "Monument Valley Navajo Tribal Park",
                "Potala Palace",
                "Matterhorn",
                "St. Mark's Square Venice",
                "Buckingham Palace",
                "Ellis Island",
                "Trevi Fountain",
                "Rockefeller Center",
                "Auschwitz",
                "Iguazu Falls",
                "Knossos",
                "Windsor Castle",
                "The Summer Palace",
                "Fuji",
                "Festung Hohensalzburg",
                "Great Barrier Reef",
                "Yellowstone National Park",
                "Luxor",
                "Grand Bazaar",
                "Great Geysir",
                "Berlin Museum Island",
                "Galapagos Islands",
                "Lake Titicaca",
                "Newgrange",
                "Cappadocia",
                "Everglades National Park",
                "Rijksmuseum",
                "Pompidue Center",
                "Central Park",
                "Torre De Belém",
                "Leaning Tower of Pisa",
                "Table Mountain",
                "Twelve Apostles",
                "CN Tower",
                "Westminster Abbey",
                "Schonbrunn Palace",
                "Edinburgh Castle",
                "Oslo Opera House",
                "Lincoln Memorial",
                "Yosemite National Park",
                "Notre Dame Cathedral",
                "Mount Everest",
                "St. Mark's Basilica",
                "Pompeii",
                "Madrid Palace",
                "Florence Cathedral",
                "Las Vegas",
                "St. Paul's Cathedral",
                "Rialto Bridge",
                "Alcatraz",
                "White Cliffs of Dover",
                "Washington Monument",
                "Arena Di Verona",
                "The Gherkin",
                "Brandenburg Gate",
                "Pentagon",
                "Cloud Gate",
                "Terracotta Warriors",
                "Cologne Cathedral",
                "The Shard",
                "Nyhavn",
                "Ponte Vecchio",
                "Wailing Wall",
                "Loch Ness",
                "Sydney Harbor Bridge",
                "Sistine Chapel",
                "Mecca",
                "Bridge of Sighs",
                "Mount Etna",
                "Lascaux Caves",
                "Bryce Canyon National Park",
                "Great Buddha",
                "Spanish Steps",
                "Freedom Tower",
                "Gateway Arch",
                "Shanghai World Finacial Center",
                "Redwood National Park",
                "Moulin Rouge",
                "Mill Complex at Kinderdijk",
                "Kronborg Castle",
                "Santorini",
                "Helsinki Cathedral",
                "Berlin Cathedral",
                "Brighton Pier",
                "Tivoli Gardens",
                "Papel Palace",
                "Disneyland Paris",
                "Tsarskoye Selo",
                "Bath",
                "Juliet's Balcony",
                "Giant's Causeway",
                "Bran Castle",
                "Portofino",
                "Atomium",
                "Chapel Bridge",
                "Guggenheim Museum ",
                "Piazza Del Campo",
                "Hollywood Walk of Fame",
                "Death Valley",
                "British Museum",
                "Amalienborg Palace",
                "Blue Lagoon",
                "Oxford University",
                "Blue Mosque",
                "Millau Bridge",
                "Piccadilly Circus",
                "Carcassonne",
                "Osaka Castle",
                "Bodiam Castle",
                "Matsumoto Castle",
                "Drottningholm Palace",
                "Lover's Bridge",
                "Charles Bridge",
                "Pont D'avignon",
                "D-Day Beaches",
                "Oresund Bridge",
                "Roskilde Cathedral",
                "Big Sur",
                "Sequoia National Park",
                "Torres Del Paine National Park",
                "Rock-Hewn Churches",
                "Carnac",
                "Wawel Cathedral",
                "Ollantaytambo",
                "Grand Teton National Park",
                "Guggenheim",
                "Church in the Rock",
                "Masai Mara",
                "Lago Di Garda",
                "Pienza",
                "San Diego Zoo",
                "Arches National Park",
                "Milford Sound",
                "Serengeti National Park",
                "Angel Falls",
                "Tallinn Town Hall Square",
                "Bryggen",
                "Van Gogh Museum",
                "Cambridge University",
                "Okavango Delta",
                "Monte Fitz Roy, El Chalten, Argentina/Chile",
                "Hoover Dam",
                "Banff National Park",
                "Winslow, AZ - Meteor Barringer Crater",
                "Teotihuacan",
                "The Panama Canal",
                "Carlsbad Caverns National Park",
                "Mezquita of Cordoba",
                "Chartres Cathedral",
                "Chateau De Chambord",
                "Delphi",
                "Valley of the Kings",
                "Jerusalem Old City",
                "The Aswan High Dam",
                "Ngorongoro Crater",
                "Golden Temple - Darbar Sahib - Harmandir Sahib",
                "Lake Baikal",
                "Borobudur Temple, Java",
                "Zhangjiajie China",
                "Banaue Rice Terraces",
                "Komodo Island, Indonesia",
                "Victoria Falls",
                "Green Park",
                "Tate Modern",
                "Siena Cathedral",
                "Basilica in Assisi",
                "Great Smoky Mountains National Park",
                "Smithsonian National Museum of Natural History",
                "Zion National Park",
                "Mesa Verde National Park",
                "Þingvellir National Park",
                "Trentino Dolomites, Italy",
                "Trolltunga, Norway",
                "The Nazca Lines",
                "Salar De Uyuni (Bolivia)",
                "Dead Sea",
                "Tian Tan Buddha",
                "Glacier Bay National Park",
                "Hawaii Volcanoes National Park",
                "Marrakech Bazaar",
                "Ephesus",
                "Great Blue Hole, Belize",
                "Tigers Nest Monastery, Bhutan",
                "Gullfoss Waterfall, Iceland",
                "Halong Bay",
                "Antelope Canyon"};

        public ArrayList<MonumentModel> monumentModelModels = new ArrayList<>();
        //public ArrayList<MonumentModel> MonumentModels = new ArrayList<>();



        @Override
        protected ArrayList<MonumentModel> doInBackground(Void... params) {
            for (String s: allMonuments
                 ) {
                SearchResultModel srm = WikiSearchTermToFullUrlSvc.getSearchResultsSummary(s);
                SearchResultModel srm2 = WikiSearchTermToFullUrlSvc.getURLPartFromPageId(srm);
                if (srm2!=null && srm2.URLPart!=null && srm2.URLPart.trim().length()>0){
                    MonumentModel m = WikiSearchTermToFullUrlSvc.getMonumentSummary(srm2);
                    monumentModelModels.add(m);
                    //Log.d("MonumentModel",srm2.URLPart);
                }

            }
            //Monumentlist();
            return monumentModelModels;
        }

        @Override
        protected void onPostExecute(final ArrayList<MonumentModel> mms) {
            int duration = Toast.LENGTH_LONG;
            //Toast toast = Toast.makeText(context, "Done", duration);
            //toast.show();
            for (MonumentModel s: mms
                    ){
                String  outputCSVLine = "%#%"+s.name+"%#%"+s.description+"%#%"+s.lat+"%#%"+s.lng+"%#%"+s.imageURL+"%#%"+s.detailedDescription;
                Log.d("Monuments",outputCSVLine);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home);

        playbutton = (Button)findViewById(R.id.playbutton);
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeActivity();
            }
        });

        shopbutton = (Button)findViewById(R.id.shopbutton);
        shopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShopActivity();
            }
        });

        settingsbutton = (Button)findViewById(R.id.settingsbutton);
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingActivity();
            }
        });

        context = getApplicationContext();
    }

    public void openHomeActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void openShopActivity(){
        Intent intent1 = new Intent(this,ShopActivity.class);
        startActivity(intent1);
    }
    public void openSettingActivity(){
        //Intent intent1 = new Intent(this,SettingActivity.class);
        //startActivity(intent1);
        //new GetWikiURLsAsync().execute();
    }



}
