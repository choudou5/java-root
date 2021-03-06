package com.dingtalk.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.dingtalk.api.BaseDingTalkRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.dingtalk.api.response.CorpHealthStepinfoListResponse;

/**
 * TOP DingTalk-API: dingtalk.corp.health.stepinfo.list request
 * 
 * @author top auto create
 * @since 1.0, 2018.01.19
 */
public class CorpHealthStepinfoListRequest extends BaseDingTalkRequest<CorpHealthStepinfoListResponse> {
	
	

	/** 
	* 可以传入用户userid或者部门id
	 */
	private String objectId;

	/** 
	* 时间列表，注意时间格式是YYYYMMDD
	 */
	private String statDates;

	/** 
	* 0表示取用户步数，1表示取部门步数
	 */
	private Long type;

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectId() {
		return this.objectId;
	}

	public void setStatDates(String statDates) {
		this.statDates = statDates;
	}

	public String getStatDates() {
		return this.statDates;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getType() {
		return this.type;
	}

	public String getApiMethodName() {
		return "dingtalk.corp.health.stepinfo.list";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("object_id", this.objectId);
		txtParams.put("stat_dates", this.statDates);
		txtParams.put("type", this.type);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<CorpHealthStepinfoListResponse> getResponseClass() {
		return CorpHealthStepinfoListResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(objectId, "objectId");
		RequestCheckUtils.checkNotEmpty(statDates, "statDates");
		RequestCheckUtils.checkMaxListSize(statDates, 31, "statDates");
		RequestCheckUtils.checkNotEmpty(type, "type");
	}
	

}