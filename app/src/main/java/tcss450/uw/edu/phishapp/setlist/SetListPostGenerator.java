package tcss450.uw.edu.phishapp.setlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to generate dummy content for testing the setList View.
 */
public class SetListPostGenerator {

    public static final SetListPost[] SET_LISTS;
    public static final int count = 20;

    static {
        SET_LISTS = new SetListPost[count];
        for (int i = 0; i < SET_LISTS.length; i++) {
            SET_LISTS[i] = new SetListPost
                    .Builder("Monday 10/24/2016", "Grand Prairie, TX, USA")
                    .addSetListData("<p><span class='set-label'>Set 1</span>: <a href='http://phish.net/song/no-men-in-no-mans-land' class='setlist-song' title='No Men In No Man's Land'>No Men In No Man's Land</a>, <a href='http://phish.net/song/breath-and-burning' class='setlist-song' title='Breath and Burning'>Breath and Burning</a>, <a href='http://phish.net/song/poor-heart' class='setlist-song' title='Poor Heart'>Poor Heart</a>, <a href='http://phish.net/song/wolfmans-brother' class='setlist-song' title='Wolfman's Brother'>Wolfman's Brother</a>, <a href='http://phish.net/song/water-in-the-sky' class='setlist-song' title='Water in the Sky'>Water in the Sky</a>, <a href='http://phish.net/song/my-soul' class='setlist-song' title='My Soul'>My Soul</a>, <a href='http://phish.net/song/nicu' class='setlist-song' title='NICU'>NICU</a>, <a href='http://phish.net/song/its-ice' class='setlist-song' title='It's Ice'>It's Ice</a> > <a href='http://phish.net/song/ocelot' class='setlist-song' title='Ocelot'>Ocelot</a>, <a href='http://phish.net/song/fuck-your-face' class='setlist-song' title='Fuck Your Face'>Fuck Your Face</a>, <a href='http://phish.net/song/ass-handed' class='setlist-song' title='Ass Handed'>Ass Handed</a>, <a href='http://phish.net/song/saw-it-again' class='setlist-song' title='Saw It Again'>Saw It Again</a>, <a href='http://phish.net/song/running-out-of-time' class='setlist-song' title='Running Out of Time'>Running Out of Time</a>, <a href='http://phish.net/song/david-bowie' class='setlist-song' title='David Bowie'>David Bowie</a></p><p><span class='set-label'>Encore 2</span>:<a href='http://phish.net/song/dog-faced-boy' class='setlist-song' title='Dog Faced Boy'>Dog Faced Boy</a>, <a href='http://phish.net/song/seven-below' class='setlist-song' title='Seven Below'>Seven Below</a>, <a href='http://phish.net/song/petrichor' class='setlist-song' title='Petrichor'>Petrichor</a>, <a href='http://phish.net/song/maze' class='setlist-song' title='Maze'>Maze</a> > <a href='http://phish.net/song/dirt' class='setlist-song' title='Dirt'>Dirt</a>, <a href='http://phish.net/song/i-always-wanted-it-this-way' class='setlist-song' title='I Always Wanted It This Way'>I Always Wanted It This Way</a><sup title=\"Trey on Marimba Lumina.\">[\"2]</sup> > <a href='http://phish.net/song/piper' class='setlist-song' title='Piper'>Piper</a>, <a href='http://phish.net/song/bug' class='setlist-song' title='Bug'>Bug</a></p><p><span class='set-label'>Encore</span>:<a href='http://phish.net/song/buffalo-bill' class='setlist-song' title='Buffalo Bill'>Buffalo Bill</a> > <a href='http://phish.net/song/rock-and-roll' class='setlist-song' title='Rock and Roll'>Rock and Roll</a><p class='setlist-footer'>[2] Trey on Marimba Lumina.<br></p>")
                    .addSetListNotes("I Always Wanted It This Way featured Trey on Marimba Lumina. Piper contained a Woman from Tokyo tease from Trey.<br>via <a href=\"http://phish.net\">phish.net</a>")
                    .addVenue("<a href=\"http://phish.net/venue/1327/Verizon_Theatre_at_Grand_Prairie\">Verizon Theatre at Grand Prairie</a>")
                    .build();
        }
    }

    private SetListPostGenerator() { }
}
