package com.hylanda.analyzer.core;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import com.hylanda.segmentor.BasicSegmentor;
import com.hylanda.segmentor.common.SegOption;
import com.hylanda.segmentor.common.SegResult;
import com.hylanda.segmentor.common.Token;

public class HLSegmenterAdapter implements Iterator<Token> {

	// 分词处理器
	private BasicSegmentor segmentor = new BasicSegmentor();

	private SegOption option;

	private Iterator<Token> tokens;
	
	private String text = null;


	public HLSegmenterAdapter(SegOption option) {
		this.option = option;
		segmentor.useStaticDictionary();
	}

	@Override
	public boolean hasNext() {
		return tokens.hasNext();
	}

	@Override
	public Token next() {
		return tokens.next();
	}

	@Override
	public void remove() {
		tokens.remove();
	}

	public synchronized void reset(Reader input) {
		try {
			StringBuilder bdr = new StringBuilder();
			char[] buf = new char[1024];
			int size = 0;
			while ((size = input.read(buf, 0, buf.length)) != -1) {
				String tempstr = new String(buf, 0, size);
				bdr.append(tempstr);
			}
			text = bdr.toString().trim();
		} catch (IOException e) {
			e.printStackTrace();
		}

		SegResult result = segmentor.segment(text, option);
		tokens = result.iterator();
	}
}
