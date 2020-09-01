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
			option.outputStopWord = settings.getAsBoolean("outputStopWord", false);
			
			if(mode == SegGrain.LARGE || mode == SegGrain.NORMAL){

				option.mergeOrgInNormalGrainMode = settings.getAsBoolean("mergeOrgInNormalGrainMode", false);
				option.mergeNumeralAndQuantity = settings.getAsBoolean("mergeNumeralAndQuantity", true);

			}
		}
		
		return option;
	}
}
