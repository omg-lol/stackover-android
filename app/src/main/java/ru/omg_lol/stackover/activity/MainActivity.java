package ru.omg_lol.stackover.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.omg_lol.stackover.R;
import ru.omg_lol.stackover.api.command.questions.GetQuestionsCommand;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetQuestionsCommand("android").run();
    }
}
