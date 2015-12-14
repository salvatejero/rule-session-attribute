<#assign aui = PortletJspTagLibs["/META-INF/aui.tld"] />
<#assign liferay_ui = PortletJspTagLibs["/META-INF/liferay-ui.tld"] />

<#setting number_format="computer">

	<div class="alert alert-warning">
		<strong><@liferay_ui["message"] key="salvatejero.liferay.content.targeting.rule.sessionattribute.SessionAttributeRule.alert" /></strong>
	</div>
	
	<@aui["input"] name="attributeKey" label="key" value=attributeKey />
		
	<@aui["input"] name="attributeValue" label="value" value=attributeValue />

