package com.fc.pay.common.pay.union;

import java.io.File;
import java.io.FilenameFilter;

public class CerFilter implements FilenameFilter {
	public boolean isCer(String name) {
		if (name.toLowerCase().endsWith(".cer")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean accept(File dir, String name) {
		return isCer(name);
	}
}
