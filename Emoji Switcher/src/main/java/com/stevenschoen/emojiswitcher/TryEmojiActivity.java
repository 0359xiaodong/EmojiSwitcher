package com.stevenschoen.emojiswitcher;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class TryEmojiActivity extends Activity {

    private ArrayAdapter<EmojiSet> adapter;

    private List<EmojiSet> emojiSets;
    private List<Typeface> emojiSetTypefaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.try_emoji);

        emojiSets = new ArrayList<>();
        emojiSets.addAll(getIntent().<EmojiSet>getParcelableArrayListExtra("emojiSets"));

        final EditText textEmojiInput = (EditText) findViewById(R.id.text_try_emoji_input);

        ListView list = (ListView) findViewById(R.id.list_try_emoji);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textEmojiInput.setTypeface(emojiSetTypefaces.get(position));
            }
        });
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, emojiSets);

        emojiSetTypefaces = new ArrayList<>(emojiSets.size());
        Log.d("asdf", "size " + emojiSets.size());
        for (EmojiSet set : emojiSets) {
            Log.d("asdf", "adding " + set.getFriendlyName());
            emojiSetTypefaces.add(Typeface.createFromFile(set.getPath()));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_try_emoji, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
