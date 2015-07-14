package com.cmcc.android.script;

public enum MessageType {
	
	TextMessage("文本消息", 1),
	ImageMessage("图片消息", 2),
	FileMessage("文件传输", 3),
	LocationMessage("位置信息", 4);
	
	private String name;
	private int index;

	private MessageType(String name, int index){
		this.name = name;
		this.index = index;
	}
	
	public String getName(int index){
		for(MessageType mt : MessageType.values()){
			return mt.getIndex() == index ? mt.name : null;
		}
		return null;
	}

	private int getIndex() {
		return index;
	}
	
	public static MessageType getMessageType(int index){
		for(MessageType mt : MessageType.values()){
			if(mt.getIndex() == index)
				return mt;
		}
		return null;
	}
}
