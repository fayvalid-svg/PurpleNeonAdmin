package com.example.purpleneon;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    private final int BG = Color.rgb(10, 6, 20), PANEL = Color.rgb(25, 14, 42), PURPLE = Color.rgb(190, 105, 255);
    private LinearLayout root;
    private TextView outfitPreview;

    @Override public void onCreate(Bundle state) { super.onCreate(state); showDashboard(); }

    private GradientDrawable shape(int color, int stroke) {
        GradientDrawable d = new GradientDrawable(); d.setColor(color); d.setCornerRadius(22); d.setStroke(2, stroke); return d;
    }
    private TextView text(String value, int size, int color) {
        TextView t = new TextView(this); t.setText(value); t.setTextSize(size); t.setTextColor(color); t.setPadding(16,12,16,12); return t;
    }
    private void addCard(String title, String subtitle) {
        LinearLayout card = new LinearLayout(this); card.setOrientation(LinearLayout.VERTICAL); card.setPadding(10,8,10,8); card.setBackground(shape(PANEL,PURPLE));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1,-2); lp.setMargins(0,8,0,8); root.addView(card,lp);
        card.addView(text(title,18,Color.WHITE)); card.addView(text(subtitle,13,Color.LTGRAY));
    }
    private void addSwitch(String label, boolean checked) {
        Switch s = new Switch(this); s.setText(label); s.setTextColor(Color.WHITE); s.setTextSize(15); s.setChecked(checked); s.setPadding(16,12,16,12); root.addView(s);
    }
    private Button button(String label) {
        Button b = new Button(this); b.setText(label); b.setTextColor(Color.WHITE); b.setAllCaps(false); b.setBackground(shape(Color.rgb(83,35,125),PURPLE));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1,-2); lp.setMargins(0,8,0,8); root.addView(b,lp); return b;
    }
    private void setupRoot(String title) {
        ScrollView scroll = new ScrollView(this); root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(20,24,20,24); root.setBackgroundColor(BG); scroll.addView(root); setContentView(scroll);
        TextView heading = text("✦ " + title + " ✦",24,PURPLE); heading.setTypeface(Typeface.DEFAULT_BOLD); heading.setGravity(Gravity.CENTER); root.addView(heading);
        TextView sub = text("REC ROOM-INSPIRED • STANDALONE APP",11,Color.LTGRAY); sub.setGravity(Gravity.CENTER); root.addView(sub);
    }
    private void showDashboard() {
        setupRoot("PURPLE NEON ADMIN");
        addCard("SYSTEM STATUS","Dashboard ready • Local interface only");
        addCard("CONNECTION HEALTH","Basic device connectivity information. This cannot prevent game moderation or kicks.");
        Button diagnostics = button("Run connection diagnostics");
        diagnostics.setOnClickListener(v -> new android.app.AlertDialog.Builder(this).setTitle("Connection health")
            .setMessage("Device network: Check Android network settings\nGame connection: Not connected to Rec Room\nModeration bypass: Not supported")
            .setPositiveButton("OK",null).show());
        addCard("ANTI-KICK","Display-only placeholder • Does not prevent kicks or bypass game moderation.");
        Switch antiKick = new Switch(this);
        antiKick.setText("Anti-Kick (visual toggle only)");
        antiKick.setTextColor(Color.WHITE);
        antiKick.setTextSize(15);
        antiKick.setPadding(16,12,16,12);
        antiKick.setOnCheckedChangeListener((buttonView, isChecked) ->
            new android.app.AlertDialog.Builder(this)
                .setTitle("Anti-Kick — display only")
                .setMessage("This switch only changes the menu display. It does not connect to Rec Room, prevent kicks, or bypass moderation.")
                .setPositiveButton("OK", null)
                .show());
        root.addView(antiKick);
        addCard("PLAYER / VISUAL SETTINGS","Personal interface preferences");
        addSwitch("Show local visual indicators",true); addSwitch("Enable custom crosshair preview",false); addSwitch("Enable menu glow",true);
        addCard("CLOTHING CUSTOMIZER","Preview outfit combinations; no game items are unlocked or changed.");
        Spinner clothing = new Spinner(this); String[] choices = {"Purple hoodie","Black jacket","Neon shirt","White hoodie","Casual outfit"};
        clothing.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,choices)); root.addView(clothing);
        outfitPreview = text("OUTFIT PREVIEW\nPurple hoodie",20,Color.WHITE); outfitPreview.setGravity(Gravity.CENTER); outfitPreview.setBackground(shape(PANEL,PURPLE)); root.addView(outfitPreview,new LinearLayout.LayoutParams(-1,180));
        clothing.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            public void onItemSelected(android.widget.AdapterView<?> p,View v,int pos,long id) { outfitPreview.setText("OUTFIT PREVIEW\n"+choices[pos]); }
            public void onNothingSelected(android.widget.AdapterView<?> p) {}
        });
        Button about = button("About this app"); about.setOnClickListener(v -> new android.app.AlertDialog.Builder(this).setTitle("Purple Neon Admin")
            .setMessage("Standalone visual dashboard prototype. It does not connect to Rec Room, modify accounts, unlock clothing, inject code, or bypass kicks/moderation.")
            .setPositiveButton("Close",null).show());
        TextView footer = text("VERSION 1.0 • PURPLE NEON",11,PURPLE); footer.setGravity(Gravity.CENTER); root.addView(footer);
    }
}
