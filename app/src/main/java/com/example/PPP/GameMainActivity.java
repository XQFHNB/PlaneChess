package com.example.PPP;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.yifeihappy.planechess.Deserializable;
import com.example.yifeihappy.planechess.R;
import com.example.yifeihappy.planechess.SerliBroacastData;

public class GameMainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.end);

		Intent intent = getIntent();
		Bundle bundleBegin = intent.getExtras();
		SerliBroacastData serliBroacastData = (SerliBroacastData) bundleBegin.getSerializable("begin");

		TextView txtTest = (TextView)findViewById(R.id.txtTest);
		txtTest.setText("飞机起飞点数：" + serliBroacastData.getTag() + "\n" + "人数：" +
				serliBroacastData.getPlayersNum() + "\n" + "Players Name:" + serliBroacastData.getPlayerName() + "\n"
				+ "Players Color:" + serliBroacastData.getPlaneColor() + "\n" + "Players ip" + serliBroacastData.getPlayerIP());

		String tagStartsNum = serliBroacastData.getTag();


	}

}