package com.cmcc.android.script;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;
import org.robolectric.annotation.Config;

import com.cmcc.ats.android.sdk.BaseTestRunner;
import com.cmcc.ats.android.sdk.util.LogUtils;
import com.littlec.sdk.business.MessageConstants.Conversation;
import com.littlec.sdk.entity.CMMessage;
import com.littlec.sdk.entity.messagebody.FileMessageBody;
import com.littlec.sdk.entity.messagebody.ImageMessageBody;
import com.littlec.sdk.entity.messagebody.LocationMessageBody;
import com.littlec.sdk.entity.messagebody.MessageBody;
import com.littlec.sdk.entity.messagebody.TextMessageBody;
import com.littlec.sdk.manager.CMAccountManager;
import com.littlec.sdk.manager.CMIMHelper;
import com.littlec.sdk.utils.CMChatListener.CMCallBack;
import com.littlec.sdk.utils.CMChatListener.OnCMListener;

@Config(emulateSdk=18, manifest=Config.NONE)
public class SendMessageTest extends BaseTestRunner {

	String loginFailReason, sendErrReason;
	
	String caseId, appkey, userName, passWord, targetUser, messageContent;
	MessageType messageType;
	boolean isTrue;
	
	public SendMessageTest(String caseId, String appkey, String username, String password, String targetUser, String messageType, String messageContent, String isTrue){
		super(SendMessageTest.class);
		this.caseId = caseId;
		this.appkey = appkey;
		this.userName = username;
		this.passWord = password;
		this.targetUser = targetUser;
		this.messageType = MessageType.getMessageType(Integer.valueOf(messageType));
		this.messageContent = messageContent;
		this.isTrue = "true".equalsIgnoreCase(isTrue) ? true : false;
	}
	
	 /**
	  * 測試消息發送
	  * @throws InterruptedException
	  */
	@Test
	public void testSendMessage(){	
		CMIMHelper.getCmAccountManager().init(context, appkey);
		final CMMessage message = new CMMessage(Conversation.TYPE_SINGLE, targetUser, getMsgBody());
		
		try{
			CMIMHelper.getCmAccountManager().doLogin(userName, passWord, new OnCMListener() {
				public void onSuccess() {
					LogUtils.debug(getClass(), "login --onSuccess");
					CMIMHelper.getCmMessageManager().sendMessage(message, callBack);
				}
				public void onFailed(String paramString) {
					loginFailReason = paramString;
					LogUtils.debug(getClass(), "onFailed:"+paramString);
				}
			});

			keepAlive(20);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(isDone()){
				Assert.assertTrue(caseId + ": send message is failed! Reason:" + loginFailReason + ", sendErrReason:" + sendErrReason, isSuccess & !isFail);
			} else {
				Assert.assertTrue(caseId + ": send message is not failed!", isFail & !isSuccess);
			}
			cmam.doLogOut();
		}
	}
	
	/**
	 * 获取消息体
	 * @return
	 */
	public MessageBody getMsgBody(){
		MessageBody msgBody = null;
		switch(messageType){
			case TextMessage:
				msgBody = new TextMessageBody(messageContent);
				break;
			case ImageMessage:
				msgBody = new ImageMessageBody(new File(getFile("pic_path", messageContent)));
				break;
			case FileMessage:
				msgBody = new FileMessageBody(new File(getFile("pic_path", messageContent)));
				break;
			case LocationMessage:
				String[] msg = messageContent.split("\\|");
				msgBody = new LocationMessageBody(Double.valueOf(msg[0]), Double.valueOf(msg[1]), msg[2], new File("pic_path", getFile(msg[3])));
				break;
			default:
				LogUtils.debug(getClass(), "请输入正确的消息类型：1-文本消息，2-图片信息，3-文件传输，4-位置信息");
				break;
		}
		return msgBody;
	}
	
	/**
	 * 消息发送回调实例
	 */
	CMCallBack callBack =new CMCallBack(){
		@Override
		public void onError(CMMessage arg0, String arg1) {
			sendErrReason = arg1;
			LogUtils.debug(getClass(), "CMCallBack send error:"+"  error reason:" + arg1);
			isFail = true;
			setDone(true);
		}
		@Override
		public void onProgress(CMMessage arg0, int arg1) {
			LogUtils.debug(getClass(), "CMCallBack send progress :" + arg1);
		}
		@Override
		public void onSuccess(CMMessage arg0) {
			LogUtils.debug(getClass(), "CMCallBack send success :" + arg0.getMessageBody().getContent());
			isSuccess = true;
			setDone(true);
		}
		
	} ;
	
	public boolean isSuccess = false;
	public boolean isFail = false;
	/**
	 * 登录回调实例
	 */
	public OnCMListener onCMListener = new OnCMListener() {
		public void onSuccess() {
			LogUtils.debug(getClass(), "onSuccess");
			setDone(true);
			isSuccess = true;
		}
		public void onFailed(String paramString) {
			LogUtils.debug(getClass(), "onFailed:"+paramString);
 			setDone(true);
			isFail = true;
		}
	};
	
	public CMAccountManager cmam = CMIMHelper.getCmAccountManager();
	
}
