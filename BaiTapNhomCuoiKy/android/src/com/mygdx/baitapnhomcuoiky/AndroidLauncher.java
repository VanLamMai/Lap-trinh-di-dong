package com.mygdx.baitapnhomcuoiky;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.baitapnhomcuoiky.FreegemasReprogrammed;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FreegemasReprogrammed.setPlatformResolver(new AndroidResolver());

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL30 = false;

		initialize(new FreegemasReprogrammed(), cfg);
	}
}
