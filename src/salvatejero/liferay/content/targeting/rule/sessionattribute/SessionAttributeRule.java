package salvatejero.liferay.content.targeting.rule.sessionattribute;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import com.liferay.content.targeting.anonymous.users.model.AnonymousUser;
import com.liferay.content.targeting.api.model.BaseRule;
import com.liferay.content.targeting.api.model.Rule;
import com.liferay.content.targeting.model.RuleInstance;
import com.liferay.content.targeting.rule.categories.SessionAttributesRuleCategory;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author stejeros
 */
@Component(immediate = true, service = Rule.class)
public class SessionAttributeRule extends BaseRule {

	@Activate
	@Override
	public void activate() {
		super.activate();
	}

	@Deactivate
	@Override
	public void deActivate() {
		super.deActivate();
	}

	@Override
	public boolean evaluate(HttpServletRequest request,
			RuleInstance ruleInstance, AnonymousUser anonymousUser)
			throws Exception {

		HttpSession httpSession = (HttpSession) request.getSession();

		JSONObject jsonObj = JSONFactoryUtil.createJSONObject(ruleInstance
				.getTypeSettings());

		String key = jsonObj.getString("attributeKey");
		String value = jsonObj.getString("attributeValue");

		Object keyValue = httpSession.getAttribute(key);
		if(keyValue != null){
			return keyValue.toString().equals(value);
		}
		return false;
	}

	@Override
	public String getIcon() {
		return "icon-puzzle";
	}

	@Override
	public String getRuleCategoryKey() {
		return SessionAttributesRuleCategory.KEY;
	}

	@Override
	public String getSummary(RuleInstance ruleInstance, Locale locale) {
		return LanguageUtil.get(locale, ruleInstance.getTypeSettings());
	}

	@Override
	public String processRule(PortletRequest request, PortletResponse response,
			String id, Map<String, String> values) {

		String key = GetterUtil.getString(values.get("attributeKey"));
		String value = GetterUtil.getString(values.get("attributeValue"));

		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("attributeKey", key);
		jsonObj.put("attributeValue", value);

		return jsonObj.toString();

	}

	@Override
	protected void populateContext(RuleInstance ruleInstance,
			Map<String, Object> context, Map<String, String> values) {

		String key = "";
		String value = "";

		if (!values.isEmpty()) {
			key = GetterUtil.getString(values.get("attributeKey"));
			value = GetterUtil.getString(values.get("attributeValue"));
		} else if (ruleInstance != null) {
			String typeSettings = ruleInstance.getTypeSettings();

			try {
				JSONObject jsonObj = JSONFactoryUtil
						.createJSONObject(typeSettings);

				key = jsonObj.getString("attributeKey");
				value = jsonObj.getString("attributeValue");
			} catch (JSONException jse) {
			}
		}

		context.put("attributeKey", key);
		context.put("attributeValue", value);

	}

}