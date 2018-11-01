package com.hylanda.utils;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;

import com.hylanda.segmentor.BasicSegmentor;
import com.hylanda.segmentor.common.SegGrain;
import com.hylanda.segmentor.common.SegOption;

public class SegTools {
	
	public static void loaddict(Environment env){
		String coredict = env.pluginsFile().resolve("elasticsearch-hlseg/config/CoreDict.dat").toString();
		String usrdict = env.pluginsFile().resolve("elasticsearch-hlseg/config/userDict_utf8.txt").toString();
		BasicSegmentor.loadStaticDictionary(coredict, usrdict);
	}
	
	public static SegOption getOption(SegGrain mode, Settings settings){
		
		if(mode == null){
			mode = SegGrain.SMALL;
		}
		
		SegOption option = new SegOption();
		option.doPosTagging = true;  //做词性标注
		option.grainSize = mode;
		option.outputDelimiter = false;
		
		if(settings != null){
			option.mergeChineseName = settings.getAsBoolean("mergeChineseName", true);
			option.mergeOrgInNormalGrainMode = settings.getAsBoolean("mergeOrgInNormalGrainMode", false);
			option.mergeNumeralAndQuantity = settings.getAsBoolean("mergeNumeralAndQuantity", true);
			option.outputStopWord = settings.getAsBoolean("outputStopWord", false);
		}
		
		return option;
	}

	/*private static SegGrain getMode(String mode) {

		SegGrain segMode;

		switch (mode) {
		case "large":
			segMode = SegGrain.LARGE;
			break;
		case "normal":
			segMode = SegGrain.NORMAL;
			break;
		default:
			segMode = SegGrain.SMALL;
			break;
		}

		return segMode;
	}*/

}
