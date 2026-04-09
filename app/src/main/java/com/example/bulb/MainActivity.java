package com.example.bulb;

// Name: [Your Name] | Student ID: [Your ID] | Lab: 1

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LampApp";

    private Lamp lamp;
    private TextView tvLampStatus, tvIntensity, tvShining, tvBulbStatus;
    private LinearLayout testResultsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lamp = new Lamp();

        ScrollView scrollView = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(32, 48, 32, 48);
        root.setBackgroundColor(Color.parseColor("#1A1A2E"));

        // Title
        TextView title = new TextView(this);
        title.setText("Lamp & Bulb Lab");
        title.setTextSize(26f);
        title.setTextColor(Color.parseColor("#E0E0FF"));
        title.setPadding(0, 0, 0, 32);
        root.addView(title);

        // Status card
        root.addView(sectionLabel("Lamp State"));
        LinearLayout statusCard = card();
        tvLampStatus = statusRow("Lamp:", "OFF");
        tvIntensity  = statusRow("Intensity:", "0");
        tvShining    = statusRow("Shining:", "false");
        tvBulbStatus = statusRow("Bulb burned:", "false");
        statusCard.addView(tvLampStatus);
        statusCard.addView(tvIntensity);
        statusCard.addView(tvShining);
        statusCard.addView(tvBulbStatus);
        root.addView(statusCard);

        // Controls
        root.addView(sectionLabel("Controls"));

        LinearLayout row1 = buttonRow();
        row1.addView(actionButton("Turn On",  v -> { lamp.turnOn();  updateStatus(); }));
        row1.addView(actionButton("Turn Off", v -> { lamp.turnOff(); updateStatus(); }));
        root.addView(row1);

        LinearLayout row2 = buttonRow();
        row2.addView(actionButton("Brighten", v -> { lamp.brighten(); updateStatus(); }));
        row2.addView(actionButton("Dim",      v -> { lamp.dim();      updateStatus(); }));
        root.addView(row2);

        Button btnReplace = actionButton("Replace Bulb", v -> {
            boolean ok = lamp.replaceBulb();
            updateStatus();
            showMessage(ok ? "Bulb replaced!" : "Cannot replace while lamp is on");
        });
        LinearLayout.LayoutParams fullWidth = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        fullWidth.setMargins(4, 4, 4, 4);
        btnReplace.setLayoutParams(fullWidth);
        root.addView(btnReplace);

        // Test runner
        root.addView(sectionLabel("Automated Tests"));
        testResultsContainer = new LinearLayout(this);
        testResultsContainer.setOrientation(LinearLayout.VERTICAL);

        Button btnRun = actionButton("Run All Tests", v -> runTests());
        btnRun.setBackgroundColor(Color.parseColor("#4A4A8A"));
        btnRun.setLayoutParams(fullWidth);
        root.addView(btnRun);
        root.addView(testResultsContainer);

        scrollView.addView(root);
        setContentView(scrollView);
    }

    private void updateStatus() {
        setRow(tvLampStatus, "Lamp:",        lamp.isOn() ? "ON" : "OFF",           lamp.isOn());
        setRow(tvIntensity,  "Intensity:",   String.valueOf(lamp.getIntensity()),   lamp.getIntensity() > 0);
        setRow(tvShining,    "Shining:",     String.valueOf(lamp.isShining()),      lamp.isShining());
        setRow(tvBulbStatus, "Bulb burned:", String.valueOf(lamp.isBulbBurned()),   !lamp.isBulbBurned());
    }

    private void setRow(TextView tv, String label, String value, boolean good) {
        tv.setText(label + "  " + value);
        tv.setTextColor(good ? Color.parseColor("#66FF99") : Color.parseColor("#FF6666"));
    }

    private void runTests() {
        testResultsContainer.removeAllViews();
        List<LampTester.TestResult> results = new LampTester().runAll();
        int passed = 0;
        for (LampTester.TestResult r : results) {
            if (r.passed) passed++;
            addTestRow(r);
            Log.d(TAG, (r.passed ? "PASS" : "FAIL") + " | " + r.name + " — " + r.details);
        }
        showMessage("Result: " + passed + "/" + results.size() + " passed");
    }

    private void addTestRow(LampTester.TestResult r) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.VERTICAL);
        row.setPadding(20, 16, 20, 16);
        row.setBackgroundColor(Color.parseColor(r.passed ? "#1A2E1A" : "#2E1A1A"));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.setMargins(0, 6, 0, 0);
        row.setLayoutParams(p);

        TextView name = new TextView(this);
        name.setText((r.passed ? "PASS  " : "FAIL  ") + r.name);
        name.setTextColor(r.passed ? Color.parseColor("#99FFBB") : Color.parseColor("#FF8888"));
        name.setTextSize(14f);

        TextView detail = new TextView(this);
        detail.setText(r.details);
        detail.setTextColor(Color.parseColor("#AAAACC"));
        detail.setTextSize(12f);
        detail.setPadding(0, 4, 0, 0);

        row.addView(name);
        row.addView(detail);
        testResultsContainer.addView(row);
    }

    private void showMessage(String msg) {
        TextView tv = new TextView(this);
        tv.setText(msg);
        tv.setTextColor(Color.parseColor("#FFD700"));
        tv.setTextSize(13f);
        tv.setPadding(16, 12, 16, 4);
        testResultsContainer.addView(tv);
    }

    private LinearLayout card() {
        LinearLayout c = new LinearLayout(this);
        c.setOrientation(LinearLayout.VERTICAL);
        c.setBackgroundColor(Color.parseColor("#16213E"));
        c.setPadding(24, 20, 24, 20);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.setMargins(0, 8, 0, 16);
        c.setLayoutParams(p);
        return c;
    }

    private TextView statusRow(String label, String initial) {
        TextView tv = new TextView(this);
        tv.setText(label + "  " + initial);
        tv.setTextColor(Color.parseColor("#CCCCFF"));
        tv.setTextSize(15f);
        tv.setPadding(0, 6, 0, 6);
        return tv;
    }

    private TextView sectionLabel(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(16f);
        tv.setTextColor(Color.parseColor("#AAAAEE"));
        tv.setPadding(0, 24, 0, 6);
        return tv;
    }

    private Button actionButton(String label, View.OnClickListener listener) {
        Button b = new Button(this);
        b.setText(label);
        b.setTextColor(Color.WHITE);
        b.setBackgroundColor(Color.parseColor("#2E2E6E"));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        p.setMargins(4, 4, 4, 4);
        b.setLayoutParams(p);
        b.setOnClickListener(listener);
        return b;
    }

    private LinearLayout buttonRow() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return row;
    }
}