package com.alibaba.dingtalk.openapi;

import com.alibaba.dingtalk.openapi.message.chat.ChatGroupHelper;
import com.dingtalk.open.client.api.model.corp.ChatInfo;
import com.dingtalk.open.client.api.model.corp.MessageBody;
import com.dingtalk.open.client.api.model.corp.MessageType;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Name：聊天群 api
 * @Author：xuhaowen
 * @Date：2018-01-29
 * @Site：http://solrhome.com
 * @License：MIT
 * @Copyright：xuhaowende@sina.cn (@Copyright 2018-2020)
 */
public class ChatApiTest extends BaseApiTest {

    @Test
    public void mainTest() throws Exception {

        //创建群
//        String chatId = createChat();
        String chatId = "chatdf8431c9d92618bdb976aef3230cc61c";

        //发送文本消息
        MessageBody.TextBody textBody = new MessageBody.TextBody();
        textBody.setContent("66666666666");
//        sendMsg(chatId, textBody);

        //发送链接消息
        MessageBody.LinkBody linkBody = new MessageBody.LinkBody();
        linkBody.setTitle("标题");
        linkBody.setText("链接到我的solrhome");
        linkBody.setMessageUrl("http://www.solrhome.com");
        linkBody.setPicUrl("https://gw.alicdn.com/tps/TB1FN16LFXXXXXJXpXXXXXXXXXX-256-130.png");
        sendMsg(chatId, linkBody);

        //更多参考 实现方式参考 MessageBody
        //doc: https://open-doc.dingtalk.com/docs/doc.htm?spm=a219a.7629140.0.0.bpMc6I&treeId=385&articleId=104977&docType=1#s8
    }

    private String createChat() throws Exception {
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setName("测试群2");
        String userId = "1617236404-248135131";
        chatInfo.setOwner(userId);
        chatInfo.setUseridlist(Arrays.asList(new String[]{userId}));
        String chatId = ChatGroupHelper.createChat(accessToken, chatInfo);
        log("群ID:" + chatId);
        return chatId;
    }

    private void sendMsg(String chatId, MessageBody message) throws Exception {
        ChatGroupHelper.sendMsg(accessToken, chatId, message);
    }

}
