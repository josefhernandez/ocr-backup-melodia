package com.isthari.ocr.melodia.audio;

public class RingBuffer {
	
	private byte[] internalData = new byte[480000];
	private int readPointer=0;
	private int writePointer=0;
	
	public void add(byte[] data, int offset, int length){
		for (int i=offset; i<length; i++){
			this.internalData[writePointer] = data[i];
			writePointer++;
			if (writePointer==internalData.length){
				writePointer=0;
			}			
		}
	}
	
//	public void add(byte []data){
//		this.add(data, 0, data.length);
//	}
	
	public byte[] recover(int length){
		int size=0;
		int tempWritePointer=writePointer;
		
		if (readPointer==tempWritePointer){
			size=0;
		} else if (readPointer<tempWritePointer){
			size=tempWritePointer-readPointer;
		}else {
			size = (internalData.length-readPointer)+tempWritePointer;
		}
		
		if (size<length){
			return null;
		}
		
		byte [] data = new byte[length];
		for (int i=0; i<length; i++){
			data[i]=this.internalData[readPointer];
			readPointer++;
			if (readPointer==internalData.length){
				readPointer=0;
			}
		}
		return data;
	}
}
