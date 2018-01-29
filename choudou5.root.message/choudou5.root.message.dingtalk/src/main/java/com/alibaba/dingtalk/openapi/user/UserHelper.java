package com.alibaba.dingtalk.openapi.user;

import cn.hutool.core.util.StrUtil;
import com.alibaba.dingtalk.openapi.OApiException;
import com.alibaba.dingtalk.openapi.utils.HttpHelper;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.model.corp.CorpUserBaseInfo;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.dingtalk.open.client.api.model.corp.CorpUserDetailList;
import com.dingtalk.open.client.api.model.corp.CorpUserList;
import com.dingtalk.open.client.api.service.corp.CorpUserService;
import com.dingtalk.open.client.common.ParamAttr;

import java.util.*;

/**
 * 通讯录成员相关的接口调用
 */
public class UserHelper {


    /**
     * 根据免登授权码查询免登用户userId
     *
     * @param accessToken
     * @param code
     * @return
     * @throws Exception
     */
    public static CorpUserBaseInfo getUserInfo(String accessToken, String code) throws Exception {
        CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
        return corpUserService.getUserinfo(accessToken, code);
    }

    /**
     * 创建企业成员
     * <p>
     * https://open-doc.dingtalk.com/docs/doc.htm?treeId=385&articleId=106816&docType=1#s1
     */
    public static String createUser(String accessToken, CorpUserDetail userDetail) throws Exception {
        CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
        JSONObject js = (JSONObject) JSONObject.parse(userDetail.getOrderInDepts());
        Map<Long, Long> orderInDepts = toHashMap(js);

        String userId = corpUserService.createCorpUser(accessToken, userDetail.getUserid(), userDetail.getName(), orderInDepts,
                userDetail.getDepartment(), userDetail.getPosition(), userDetail.getMobile(), userDetail.getTel(), userDetail.getWorkPlace(),
                userDetail.getRemark(), userDetail.getEmail(), userDetail.getJobnumber(),
                userDetail.getIsHide(), userDetail.getSenior(), userDetail.getExtattr());

        // 员工唯一标识ID
        return userId;
    }


    /**
     * 更新成员
     * <p>
     * https://open-doc.dingtalk.com/docs/doc.htm?treeId=385&articleId=106816&docType=1#s2
     */
    public static void updateUser(String accessToken, CorpUserDetail userDetail) throws Exception {
        CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
        JSONObject js = (JSONObject) JSONObject.parse(userDetail.getOrderInDepts());
        Map<Long, Long> orderInDepts = toHashMap(js);

        corpUserService.updateCorpUser(accessToken, userDetail.getUserid(), userDetail.getName(), orderInDepts,
                userDetail.getDepartment(), userDetail.getPosition(), userDetail.getMobile(), userDetail.getTel(), userDetail.getWorkPlace(),
                userDetail.getRemark(), userDetail.getEmail(), userDetail.getJobnumber(),
                userDetail.getIsHide(), userDetail.getSenior(), userDetail.getExtattr());
    }


    /**
     * 删除成员
     */
    public static void deleteUser(String accessToken, String userid) throws Exception {
        CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
        corpUserService.deleteCorpUser(accessToken, userid);
    }


    //获取成员
    public static CorpUserDetail getUser(String accessToken, String userid) throws Exception {

        CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
        return corpUserService.getCorpUser(accessToken, userid);
    }

    //批量删除成员
    public static void batchDeleteUser(String accessToken, List<String> useridlist)
            throws Exception {
        CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
        corpUserService.batchdeleteCorpUserListByUserids(accessToken, useridlist);
    }

    //批量删除成员
    public static void batchDeleteUser(String accessToken, String ... userids)
            throws Exception {
        List<String> useridlist = new ArrayList<>();
        for (String userid : userids) {
            useridlist.add(userid);
        }
        CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
        corpUserService.batchdeleteCorpUserListByUserids(accessToken, useridlist);
    }

    //获取部门成员
    public static CorpUserList getDepartmentUser(
            String accessToken,
            long departmentId,
            Long offset,
            Integer size,
            String order)
            throws Exception {

        String url = "/user/simplelist?access_token=" + accessToken+"&department_id="+departmentId+"&offset="+offset+"&size="+size;
        JSONObject response = HttpHelper.httpGet(url);
        return response.toJavaObject(CorpUserList.class);
    }


    //获取部门成员（详情）
    public static CorpUserDetailList getUserDetails(
            String accessToken,
            long departmentId,
            Long offset,
            Integer size,
            String order)
            throws Exception {

        String url = "/user/list?access_token=" + accessToken+"&department_id="+departmentId+"&offset="+offset+"&size="+size;
        JSONObject response = HttpHelper.httpGet(url);
        return response.toJavaObject(CorpUserDetailList.class);
    }


    /**
     * 管理后台免登时通过CODE换取微应用管理员的身份信息
     *
     * @param ssoToken
     * @param code
     * @return
     * @throws OApiException
     */
    public static JSONObject getAgentUserInfo(String ssoToken, String code) throws OApiException {
        String url = "/sso/getuserinfo?" + "access_token=" + ssoToken + "&code=" + code;
        JSONObject response = HttpHelper.httpGet(url);
        return response;
    }


    public static HashMap<Long, Long> toHashMap(JSONObject js) {
        if (js == null) {
            return null;
        }
        HashMap<Long, Long> data = new HashMap<Long, Long>();
        // 将json字符串转换成jsonObject
        Set<String> set = js.keySet();
        // 遍历jsonObject数据，添加到Map对象
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            Long keyLong = Long.valueOf(key);

            String value = js.getString(key);
            Long valueLong;
            if (StrUtil.isEmpty(value)) {
                valueLong = js.getLong(key);
            } else {
                valueLong = Long.valueOf(value);
            }
            data.put(keyLong, valueLong);
        }
        return data;
    }
}
