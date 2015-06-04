package com.slanissue.drawcircle;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	private MyCircleProgress myp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        myp = (MyCircleProgress) findViewById(R.id.MyCircleProgress);
        new ProgressAnimation().execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main, menu);
    	return true;
    }
    
    class ProgressAnimation extends AsyncTask<Void, Integer, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			for (int i = 0; i < 360; i++) {
				try {
					//这种方法可以在DoInBackground调用更新UI线程，子线程的计算仍然在进行
					//每次调用这个方法将会触发onProgressUpdate在UI线程执行，如果任务被取消
					//onProgressUpdate将会作出提醒
					publishProgress(i);
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
			myp.n = values[0];
			myp.invalidate();
			super.onProgressUpdate(values);
		}
    	
    }
}
