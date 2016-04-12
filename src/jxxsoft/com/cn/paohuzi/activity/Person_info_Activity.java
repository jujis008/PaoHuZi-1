package jxxsoft.com.cn.paohuzi.activity;

import jxxsoft.com.cn.paohuzi.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;

/**
 * ������Ϣ�޸Ľ���
 * @author Administrator
 *
 */
public class Person_info_Activity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_info);
		findViewById(R.id.person_info_exit).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.person_info_exit:
				saveAndClose();
				break;
			default:
				break;
		}
		
	}
	
	
	//���ؼ�
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		saveAndClose();
	}

	private void saveAndClose(){
		setResult(0x02);
		this.finish();
	}


}
